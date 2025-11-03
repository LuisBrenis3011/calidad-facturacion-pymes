package com.billtel.calidad.facturacion_pymes.layer.business.service.impl;


import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.users.Usuario;
import com.billtel.calidad.facturacion_pymes.layer.persistence.EmpresaRepository;
import com.billtel.calidad.facturacion_pymes.layer.persistence.users.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpresaServiceImplTest {

    @Mock
    private EmpresaRepository empresaRepository;
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private EmpresaServiceImpl empresaService;

    private Empresa empresa;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("brenis");

        empresa = new Empresa();
        empresa.setId(10L);
        empresa.setRuc("12345678901");
        empresa.setRazonSocial("Empresa Test");
        empresa.setDireccion("Av. Los Cedros 123");
        empresa.setEmail("empresa@test.com");
    }

    @Test
    void testFindAll() {
        when(empresaRepository.findAll()).thenReturn(List.of(empresa));

        var result = empresaService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getRazonSocial()).isEqualTo("Empresa Test");
        verify(empresaRepository).findAll();
    }

    @Test
    void testFindByUsername() {
        when(empresaRepository.findByUsuarioUsername("brenis")).thenReturn(List.of(empresa));

        var result = empresaService.findByUsername("brenis");

        assertThat(result).hasSize(1);
        verify(empresaRepository).findByUsuarioUsername("brenis");
    }

    @Test
    void testFindByUsuarioId() {
        when(empresaRepository.findByUsuarioId(1L)).thenReturn(List.of(empresa));

        var result = empresaService.findByUsuarioId(1L);

        assertThat(result).hasSize(1);
        verify(empresaRepository).findByUsuarioId(1L);
    }

    @Test
    void testFindById() {
        when(empresaRepository.findById(10L)).thenReturn(Optional.of(empresa));

        var result = empresaService.findById(10L);

        assertThat(result).isPresent();
        assertThat(result.get().getRuc()).isEqualTo("12345678901");
        verify(empresaRepository).findById(10L);
    }

    @Test
    void testFindByIdAndUsuarioId() {
        when(empresaRepository.findByIdAndUsuarioId(10L, 1L)).thenReturn(Optional.of(empresa));

        var result = empresaService.findByIdAndUsuarioId(10L, 1L);

        assertThat(result).isPresent();
        verify(empresaRepository).findByIdAndUsuarioId(10L, 1L);
    }

    @Test
    void testSave_UsuarioEncontrado() {
        when(usuarioRepository.findByUsername("brenis")).thenReturn(Optional.of(usuario));
        when(empresaRepository.save(any(Empresa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Empresa result = empresaService.save("brenis", empresa);

        assertThat(result.getUsuario()).isEqualTo(usuario);
        assertThat(result.getRazonSocial()).isEqualTo("Empresa Test");
        verify(usuarioRepository).findByUsername("brenis");
        verify(empresaRepository).save(empresa);
    }

    @Test
    void testSave_UsuarioNoEncontrado() {
        when(usuarioRepository.findByUsername("brenis")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> empresaService.save("brenis", empresa));

        assertThat(ex.getMessage()).isEqualTo("Usuario no encontrado: brenis");
        verify(usuarioRepository).findByUsername("brenis");
        verifyNoInteractions(empresaRepository);
    }

    @Test
    void testUpdate_EmpresaExistente() {
        Empresa empresaNueva = new Empresa();
        empresaNueva.setRuc("98765432109");
        empresaNueva.setRazonSocial("Empresa Actualizada");
        empresaNueva.setDireccion("Av. Nueva 999");
        empresaNueva.setEmail("nueva@test.com");

        when(empresaRepository.findById(10L)).thenReturn(Optional.of(empresa));
        when(empresaRepository.save(any(Empresa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = empresaService.update(empresaNueva, 10L);

        assertThat(result).isPresent();
        assertThat(result.get().getRazonSocial()).isEqualTo("Empresa Actualizada");
        assertThat(result.get().getRuc()).isEqualTo("98765432109");
        verify(empresaRepository).save(empresa);
    }

    @Test
    void testUpdate_EmpresaNoExiste() {
        when(empresaRepository.findById(10L)).thenReturn(Optional.empty());

        var result = empresaService.update(empresa, 10L);

        assertThat(result).isEmpty();
        verify(empresaRepository).findById(10L);
        verify(empresaRepository, never()).save(any());
    }

    @Test
    void testDeleteById() {
        empresaService.deleteById(10L);

        verify(empresaRepository).deleteById(10L);
    }

    @Test
    void testDeleteByIdAndUsuarioId_Existe() {
        when(empresaRepository.findByIdAndUsuarioId(10L, 1L)).thenReturn(Optional.of(empresa));

        empresaService.deleteByIdAndUsuarioId(10L, 1L);

        verify(empresaRepository).deleteById(10L);
    }

    @Test
    void testDeleteByIdAndUsuarioId_NoExiste() {
        when(empresaRepository.findByIdAndUsuarioId(10L, 1L)).thenReturn(Optional.empty());

        empresaService.deleteByIdAndUsuarioId(10L, 1L);

        verify(empresaRepository, never()).deleteById(any());
    }
}

