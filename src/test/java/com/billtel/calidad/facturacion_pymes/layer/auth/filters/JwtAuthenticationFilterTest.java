package com.billtel.calidad.facturacion_pymes.layer.auth.filters;

import com.billtel.calidad.facturacion_pymes.layer.auth.TokenJwtConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private JwtAuthenticationFilter filter;
    private StringWriter responseWriter;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        filter = new JwtAuthenticationFilter(authenticationManager);
        responseWriter = new StringWriter();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Debe autenticar exitosamente con credenciales válidas de usuario regular")
    void autenticacionExitosaUsuarioRegular() throws Exception {
        String jsonRequest = "{\"username\":\"testuser\",\"password\":\"password123\"}";
        mockRequestInputStream(jsonRequest);
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        User userDetails = new User("testuser", "password123",
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(auth);

        filter.attemptAuthentication(request, response);
        filter.successfulAuthentication(request, response, filterChain, auth);

        verify(response).setStatus(200);
        verify(response).setContentType("application/json");
        verify(response).addHeader(eq(TokenJwtConfig.HEADER_AUTHORIZATION), startsWith(TokenJwtConfig.PREFIX_TOKEN));

        String responseBody = responseWriter.toString();
        Map<String, Object> body = objectMapper.readValue(responseBody, Map.class);

        assertTrue(body.containsKey("token"));
        assertTrue(body.containsKey("username"));
        assertEquals("testuser", body.get("username"));
        assertTrue(body.get("message").toString().contains("testuser"));
        assertTrue(body.get("message").toString().contains("has iniciado sesión con éxito"));
    }

    @Test
    @DisplayName("Debe autenticar exitosamente con credenciales de administrador")
    void autenticacionExitosaUsuarioAdmin() throws Exception {
        String jsonRequest = "{\"username\":\"admin\",\"password\":\"admin123\"}";
        mockRequestInputStream(jsonRequest);
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        User adminDetails = new User("admin", "admin123",
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                        new SimpleGrantedAuthority("ROLE_USER")));
        Authentication auth = new UsernamePasswordAuthenticationToken(
                adminDetails, null, adminDetails.getAuthorities());

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(auth);

        filter.attemptAuthentication(request, response);
        filter.successfulAuthentication(request, response, filterChain, auth);

        verify(response).setStatus(200);
        verify(response).addHeader(eq(TokenJwtConfig.HEADER_AUTHORIZATION), anyString());

        String responseBody = responseWriter.toString();
        Map<String, Object> body = objectMapper.readValue(responseBody, Map.class);

        assertEquals("admin", body.get("username"));
        assertNotNull(body.get("token"));
    }

    @Test
    @DisplayName("Debe rechazar autenticación con credenciales inválidas")
    void autenticacionFallidaCredencialesInvalidas() throws Exception {
        String jsonRequest = "{\"username\":\"baduser\",\"password\":\"wrongpass\"}";
        mockRequestInputStream(jsonRequest);
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Credenciales inválidas"));

        assertThrows(BadCredentialsException.class, () -> {
            filter.attemptAuthentication(request, response);
        });

        BadCredentialsException exception = new BadCredentialsException("Credenciales inválidas");
        filter.unsuccessfulAuthentication(request, response, exception);

        verify(response).setStatus(401);
        verify(response).setContentType("application/json");

        String responseBody = responseWriter.toString();
        Map<String, Object> body = objectMapper.readValue(responseBody, Map.class);

        assertEquals("Error en la autenticacion username o password incorrecto!", body.get("message"));
        assertEquals("Credenciales inválidas", body.get("error"));
    }

    @Test
    @DisplayName("Debe manejar JSON malformado en el request")
    void autenticacionConJsonMalformado() throws Exception {
        String jsonInvalido = "{\"username\":\"test\",\"password\":";
        mockRequestInputStream(jsonInvalido);

        Authentication mockAuth = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuth);

        Authentication result = filter.attemptAuthentication(request, response);

        assertNotNull(result);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    @DisplayName("Debe manejar request sin body")
    void autenticacionSinBody() throws Exception {
        mockRequestInputStream("");

        Authentication mockAuth = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuth);

        Authentication result = filter.attemptAuthentication(request, response);

        assertNotNull(result);
    }

    @Test
    @DisplayName("Debe manejar IOException en el request")
    void autenticacionConIOException() throws Exception {
        ServletInputStream mockInputStream = mock(ServletInputStream.class);
        when(request.getInputStream()).thenReturn(mockInputStream);
        // Usar lenient porque Jackson puede llamar a cualquiera de estos métodos
        lenient().when(mockInputStream.read()).thenThrow(new IOException("Error de lectura"));
        lenient().when(mockInputStream.read(any(byte[].class))).thenThrow(new IOException("Error de lectura"));
        lenient().when(mockInputStream.read(any(byte[].class), anyInt(), anyInt())).thenThrow(new IOException("Error de lectura"));

        Authentication mockAuth = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuth);

        Authentication result = filter.attemptAuthentication(request, response);

        assertNotNull(result);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    @DisplayName("Debe incluir header de autorización con token en respuesta exitosa")
    void verificarHeaderAutorizacionEnRespuesta() throws Exception {
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        User userDetails = new User("user", "pass",
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        filter.successfulAuthentication(request, response, filterChain, auth);

        verify(response).addHeader(
                eq(TokenJwtConfig.HEADER_AUTHORIZATION),
                argThat(token -> token != null && token.startsWith(TokenJwtConfig.PREFIX_TOKEN))
        );
    }

    @Test
    @DisplayName("Debe incluir claim isAdmin=true para usuarios con ROLE_ADMIN")
    void verificarClaimIsAdminParaAdministradores() throws Exception {
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        User adminDetails = new User("admin", "admin123",
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        Authentication auth = new UsernamePasswordAuthenticationToken(
                adminDetails, null, adminDetails.getAuthorities());

        filter.successfulAuthentication(request, response, filterChain, auth);

        String responseBody = responseWriter.toString();
        Map<String, Object> body = objectMapper.readValue(responseBody, Map.class);

        assertNotNull(body.get("token"));
        assertTrue(body.get("token").toString().length() > 50);
    }

    @Test
    @DisplayName("Debe incluir claim isAdmin=false para usuarios sin ROLE_ADMIN")
    void verificarClaimIsAdminParaUsuariosRegulares() throws Exception {
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        User userDetails = new User("user", "pass",
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        filter.successfulAuthentication(request, response, filterChain, auth);

        String responseBody = responseWriter.toString();
        Map<String, Object> body = objectMapper.readValue(responseBody, Map.class);

        assertNotNull(body.get("token"));
        assertEquals("user", body.get("username"));
    }

    @Test
    @DisplayName("Debe retornar mensaje personalizado con username en autenticación exitosa")
    void verificarMensajePersonalizadoEnExito() throws Exception {
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        User userDetails = new User("juan", "pass",
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        filter.successfulAuthentication(request, response, filterChain, auth);

        String responseBody = responseWriter.toString();
        Map<String, Object> body = objectMapper.readValue(responseBody, Map.class);

        String mensaje = body.get("message").toString();
        assertTrue(mensaje.contains("juan"));
        assertTrue(mensaje.contains("has iniciado sesión con éxito"));
    }

    private void mockRequestInputStream(String json) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(json.getBytes());
        ServletInputStream servletInputStream = new ServletInputStream() {
            @Override
            public int read() {
                return inputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(jakarta.servlet.ReadListener readListener) { //NOSONAR
                // Método requerido por la interfaz pero no necesario en tests
            }
        };
        when(request.getInputStream()).thenReturn(servletInputStream);
    }
}