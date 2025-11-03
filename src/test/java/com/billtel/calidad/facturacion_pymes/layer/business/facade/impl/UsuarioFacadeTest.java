package com.billtel.calidad.facturacion_pymes.layer.business.facade.impl;

import com.billtel.calidad.facturacion_pymes.layer.business.mapper.usuario_mapper.UsuarioCreateRequestMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.mapper.usuario_mapper.UsuarioDtoMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.mapper.usuario_mapper.UsuarioRequestMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.service.IUsuarioService;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.UsuarioCreateRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.UsuarioRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.UsuarioDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.users.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioFacadeTest {

    private IUsuarioService usuarioService;
    private UsuarioDtoMapper usuarioDtoMapper;
    private UsuarioRequestMapper usuarioRequestMapper;
    private UsuarioCreateRequestMapper usuarioCreateRequestMapper;
    private UsuarioFacade usuarioFacade;

    @BeforeEach
    void setUp() {
        usuarioService = mock(IUsuarioService.class);
        usuarioDtoMapper = mock(UsuarioDtoMapper.class);
        usuarioRequestMapper = mock(UsuarioRequestMapper.class);
        usuarioCreateRequestMapper = mock(UsuarioCreateRequestMapper.class);
        usuarioFacade = new UsuarioFacade(usuarioService, usuarioDtoMapper, usuarioRequestMapper, usuarioCreateRequestMapper);
    }

    @Test
    void testFindById() {
        var user = new Usuario();
        var userDto = new UsuarioDto();

        when(usuarioService.findById(1L)).thenReturn(Optional.of(user));
        when(usuarioDtoMapper.toDto(user)).thenReturn(userDto);

        var result = usuarioFacade.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(userDto, result.get());
        verify(usuarioService).findById(1L);
        verify(usuarioDtoMapper).toDto(user);
    }

    @Test
    void testFindByUsername() {
        var user = new Usuario();
        var userDto = new UsuarioDto();

        when(usuarioService.findByUsername("brenis")).thenReturn(Optional.of(user));
        when(usuarioDtoMapper.toDto(user)).thenReturn(userDto);

        var result = usuarioFacade.findByUsername("brenis");

        assertTrue(result.isPresent());
        assertEquals(userDto, result.get());
        verify(usuarioService).findByUsername("brenis");
        verify(usuarioDtoMapper).toDto(user);
    }

    @Test
    void testFindAll() {
        var user = new Usuario();
        var userDto = new UsuarioDto();

        when(usuarioService.findAll()).thenReturn(List.of(user));
        when(usuarioDtoMapper.toDto(user)).thenReturn(userDto);

        var result = usuarioFacade.findAll();

        assertEquals(1, result.size());
        assertEquals(userDto, result.get(0));
        verify(usuarioService).findAll();
        verify(usuarioDtoMapper).toDto(user);
    }

    @Test
    void testCreate() {
        var request = new UsuarioCreateRequest();
        var user = new Usuario();
        var createdUser = new Usuario();
        var userDto = new UsuarioDto();

        when(usuarioCreateRequestMapper.toDomain(request)).thenReturn(user);
        when(usuarioService.save(user)).thenReturn(createdUser);
        when(usuarioDtoMapper.toDto(createdUser)).thenReturn(userDto);

        var result = usuarioFacade.create(request);

        assertNotNull(result);
        assertEquals(userDto, result);
        verify(usuarioCreateRequestMapper).toDomain(request);
        verify(usuarioService).save(user);
        verify(usuarioDtoMapper).toDto(createdUser);
    }

    @Test
    void testUpdate() {
        var request = new UsuarioRequest();
        var user = new Usuario();
        var updatedUser = new Usuario();
        var userDto = new UsuarioDto();

        when(usuarioRequestMapper.toDomain(request)).thenReturn(user);
        when(usuarioService.update(user, 1L)).thenReturn(Optional.of(updatedUser));
        when(usuarioDtoMapper.toDto(updatedUser)).thenReturn(userDto);

        var result = usuarioFacade.update(request, 1L);

        assertTrue(result.isPresent());
        assertEquals(userDto, result.get());
        verify(usuarioRequestMapper).toDomain(request);
        verify(usuarioService).update(user, 1L);
        verify(usuarioDtoMapper).toDto(updatedUser);
    }

    @Test
    void testDelete() {
        usuarioFacade.delete(1L);

        verify(usuarioService).delete(1L);
        verifyNoMoreInteractions(usuarioService);
    }

}
