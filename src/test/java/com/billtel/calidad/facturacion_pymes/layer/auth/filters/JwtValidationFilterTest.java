package com.billtel.calidad.facturacion_pymes.layer.auth.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

import static com.billtel.calidad.facturacion_pymes.layer.auth.TokenJwtConfig.SECRET_KEY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtValidationFilterTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private JwtValidationFilter filter;
    private StringWriter responseWriter;

    @BeforeEach
    void setUp() {
        filter = new JwtValidationFilter(authenticationManager);
        responseWriter = new StringWriter();
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Debe continuar el filtro cuando no hay header de autorización")
    void sinHeaderAutorizacion() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("Debe continuar el filtro cuando el header no empieza con 'Bearer '")
    void headerSinPrefixoBearer() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Basic abc123");

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("Debe autenticar correctamente con token JWT válido")
    void tokenJwtValido() throws ServletException, IOException {
        String token = generarTokenValido("testuser", List.of("ROLE_USER"));
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(auth);
        assertEquals("testuser", auth.getName());
        assertTrue(auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @Test
    @DisplayName("Debe autenticar con múltiples roles")
    void tokenConMultiplesRoles() throws ServletException, IOException {
        String token = generarTokenValido("admin", List.of("ROLE_ADMIN", "ROLE_USER"));
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(auth);
        assertEquals(2, auth.getAuthorities().size());
        assertTrue(auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        assertTrue(auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @Test
    @DisplayName("Debe rechazar token JWT inválido con error 401")
    void tokenJwtInvalido() throws ServletException, IOException {
        String tokenInvalido = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.invalido.invalido";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + tokenInvalido);
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, never()).doFilter(request, response);
        verify(response).setStatus(401);
        verify(response).setContentType("application/json");

        String responseBody = responseWriter.toString();
        assertTrue(responseBody.contains("error"));
        assertTrue(responseBody.contains("El token JWT no es válido!"));

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("Debe rechazar token JWT expirado")
    void tokenJwtExpirado() throws ServletException, IOException {
        String tokenExpirado = generarTokenExpirado("testuser", List.of("ROLE_USER"));
        when(request.getHeader("Authorization")).thenReturn("Bearer " + tokenExpirado);
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, never()).doFilter(request, response);
        verify(response).setStatus(401);
        verify(response).setContentType("application/json");

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("Debe manejar token malformado sin estructura válida")
    void tokenMalformado() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer token.malformado");
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, never()).doFilter(request, response);
        verify(response).setStatus(401);
        verify(response).setContentType("application/json");

        String responseBody = responseWriter.toString();
        Map<String, String> errorResponse = new ObjectMapper().readValue(responseBody, Map.class);
        assertEquals("El token JWT no es válido!", errorResponse.get("message"));
    }

    @Test
    @DisplayName("Debe rechazar token con firma incorrecta")
    void tokenConFirmaIncorrecta() throws ServletException, IOException {
        String tokenConFirmaIncorrecta = Jwts.builder()
                .subject("testuser")
                .claim("authorities", "[{\"authority\":\"ROLE_USER\"}]")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(Keys.hmacShaKeyFor("otra_clave_secreta_diferente_con_longitud_minima_requerida".getBytes()))
                .compact();

        when(request.getHeader("Authorization")).thenReturn("Bearer " + tokenConFirmaIncorrecta);
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, never()).doFilter(request, response);
        verify(response).setStatus(401);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    private String generarTokenValido(String username, List<String> roles) {
        // Serializar las autoridades como JSON string
        List<String> authoritiesJson = roles.stream()
                .map(role -> String.format("{\"authority\":\"%s\"}", role))
                .toList();
        String authoritiesString = "[" + String.join(",", authoritiesJson) + "]";

        return Jwts.builder()
                .subject(username)
                .claim("username", username)
                .claim("authorities", authoritiesString)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SECRET_KEY)
                .compact();
    }

    private String generarTokenExpirado(String username, List<String> roles) {
        List<String> authoritiesJson = roles.stream()
                .map(role -> String.format("{\"authority\":\"%s\"}", role))
                .toList();
        String authoritiesString = "[" + String.join(",", authoritiesJson) + "]";

        return Jwts.builder()
                .subject(username)
                .claim("username", username)
                .claim("authorities", authoritiesString)
                .issuedAt(new Date(System.currentTimeMillis() - 7200000))
                .expiration(new Date(System.currentTimeMillis() - 3600000))
                .signWith(SECRET_KEY)
                .compact();
    }
}