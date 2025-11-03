package com.billtel.calidad.facturacion_pymes.layer.business.service.impl;

import com.billtel.calidad.facturacion_pymes.layer.domain.entity.users.Rol;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.users.Usuario;
import com.billtel.calidad.facturacion_pymes.layer.persistence.users.RolRepository;
import com.billtel.calidad.facturacion_pymes.layer.persistence.users.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private Usuario usuario;
    private Rol rolUser;
    private Rol rolAdmin;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("brenis");
        usuario.setPassword("12345");
        usuario.setEmail("brenis@example.com");

        rolUser = new Rol();
        rolUser.setName("ROLE_USER");

        rolAdmin = new Rol();
        rolAdmin.setName("ROLE_ADMIN");
    }

    @Test
    void findById_debeRetornarUsuarioCuandoExiste() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.findById(1L);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getUsername()).isEqualTo("brenis");
        verify(usuarioRepository).findById(1L);
    }

    @Test
    void findByUsername_debeRetornarUsuarioPorNombre() {
        when(usuarioRepository.findByUsername("brenis")).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.findByUsername("brenis");

        assertThat(resultado).isPresent();
        verify(usuarioRepository).findByUsername("brenis");
    }

    @Test
    void findAll_debeRetornarTodosLosUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        Iterable<Usuario> resultado = usuarioService.findAll();

        assertThat(resultado).hasSize(1);
        verify(usuarioRepository).findAll();
    }

    @Test
    void save_debeGuardarUsuarioConPasswordCodificadaYRoles() {
        usuario.setAdmin(true);

        when(passwordEncoder.encode("12345")).thenReturn("encoded123");
        when(rolRepository.findByName("ROLE_USER")).thenReturn(Optional.of(rolUser));
        when(rolRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(rolAdmin));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario guardado = usuarioService.save(usuario);

        assertThat(guardado.getPassword()).isEqualTo("encoded123");
        assertThat(guardado.getRoles()).extracting(Rol::getName)
                .containsExactlyInAnyOrder("ROLE_USER", "ROLE_ADMIN");
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void update_debeActualizarUsuarioSiExiste() {
        Usuario actualizado = new Usuario();
        actualizado.setUsername("nuevoUser");
        actualizado.setEmail("nuevo@example.com");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(rolRepository.findByName("ROLE_USER")).thenReturn(Optional.of(rolUser));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Usuario> resultado = usuarioService.update(actualizado, 1L);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getUsername()).isEqualTo("nuevoUser");
        assertThat(resultado.get().getEmail()).isEqualTo("nuevo@example.com");
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void delete_debeEliminarUsuarioPorId() {
        usuarioService.delete(1L);
        verify(usuarioRepository).deleteById(1L);
    }

    @Test
    void getRoles_debeAsignarSoloRoleUserCuandoNoEsAdmin() {
        when(rolRepository.findByName("ROLE_USER")).thenReturn(Optional.of(rolUser));

        List<Rol> roles = usuarioService.getRoles(usuario);

        assertThat(roles).hasSize(1);
        assertThat(roles.get(0).getName()).isEqualTo("ROLE_USER");
        verify(rolRepository).findByName("ROLE_USER");
    }

    @Test
    void getRoles_debeAsignarRolesUserYAdminCuandoEsAdmin() {
        usuario.setAdmin(true);
        when(rolRepository.findByName("ROLE_USER")).thenReturn(Optional.of(rolUser));
        when(rolRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(rolAdmin));

        List<Rol> roles = usuarioService.getRoles(usuario);

        assertThat(roles).hasSize(2);
        assertThat(roles).extracting(Rol::getName)
                .containsExactlyInAnyOrder("ROLE_USER", "ROLE_ADMIN");
        verify(rolRepository).findByName("ROLE_ADMIN");
    }
}

