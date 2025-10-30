package com.billtel.calidad.facturacion_pymes.layer.presentation.controller;

import com.billtel.calidad.facturacion_pymes.layer.business.facade.IUsuarioFacade;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.UsuarioCreateRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.UsuarioRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.UsuarioDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IUsuarioFacade iUserFacade;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllUsers() throws Exception {
        UsuarioDto dto = new UsuarioDto();
        dto.setId(1L);
        List<UsuarioDto> users = Collections.singletonList(dto);

        when(iUserFacade.findAll()).thenReturn(users);

        mockMvc.perform(get("/usuario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(iUserFacade, times(1)).findAll();
    }

    @Test
    void testGetUserById_Found() throws Exception {
        UsuarioDto dto = new UsuarioDto();
        dto.setId(1L);

        when(iUserFacade.findById(1L)).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testGetUserById_NotFound() throws Exception {
        when(iUserFacade.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/usuario/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetUserByUsername_Found() throws Exception {
        UsuarioDto dto = new UsuarioDto();
        dto.setUsername("john");

        when(iUserFacade.findByUsername("john")).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/usuario/username/john"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john"));
    }

    @Test
    void testGetUserByUsername_NotFound() throws Exception {
        when(iUserFacade.findByUsername("john")).thenReturn(Optional.empty());

        mockMvc.perform(get("/usuario/username/john"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateUser() throws Exception {
        UsuarioRequest request = new UsuarioRequest();
        request.setUsername("john");

        UsuarioDto dto = new UsuarioDto();
        dto.setId(1L);
        dto.setUsername("john");

        when(iUserFacade.create(any(UsuarioCreateRequest.class))).thenReturn(dto);

        mockMvc.perform(post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("john"));
    }

    @Test
    void testUpdateUser_Found() throws Exception {
        UsuarioRequest request = new UsuarioRequest();
        request.setUsername("john_updated");

        UsuarioDto updated = new UsuarioDto();
        updated.setId(1L);
        updated.setUsername("john_updated");

        when(iUserFacade.update(any(UsuarioRequest.class), eq(1L))).thenReturn(Optional.of(updated));

        mockMvc.perform(put("/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("john_updated"));
    }

    @Test
    void testUpdateUser_NotFound() throws Exception {
        UsuarioRequest request = new UsuarioRequest();
        request.setUsername("john_updated");

        when(iUserFacade.update(any(UsuarioRequest.class), eq(1L))).thenReturn(Optional.empty());

        mockMvc.perform(put("/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(iUserFacade).delete(1L);

        mockMvc.perform(delete("/usuario/1"))
                .andExpect(status().isNoContent());

        verify(iUserFacade, times(1)).delete(1L);
    }
}
