package com.billtel.calidad.facturacion_pymes.layer.presentation.controller;

import com.billtel.calidad.facturacion_pymes.layer.business.facade.IUsuarioFacade;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.UsuarioCreateRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.UsuarioRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.UsuarioDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IUsuarioFacade usuarioFacade;

    @Autowired
    private ObjectMapper objectMapper;

    private UsuarioDto usuarioDto;

    @BeforeEach
    void setup() {
        usuarioDto = new UsuarioDto();
        usuarioDto.setId(1L);
        usuarioDto.setUsername("brenis");
        usuarioDto.setEmail("brenis@example.com");
        usuarioDto.setAdmin(false);
    }

    @Test
    @DisplayName("GET /usuario debe devolver lista de usuarios")
    void getAllUsers() throws Exception {
        when(usuarioFacade.findAll()).thenReturn(List.of(usuarioDto));

        mockMvc.perform(get("/usuario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username").value("brenis"));
    }

    @Test
    @DisplayName("GET /usuario/{id} debe devolver usuario cuando existe")
    void getUserByIdFound() throws Exception {
        when(usuarioFacade.findById(1L)).thenReturn(Optional.of(usuarioDto));

        mockMvc.perform(get("/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("brenis@example.com"));
    }

    @Test
    @DisplayName("GET /usuario/{id} debe devolver 404 cuando no existe")
    void getUserByIdNotFound() throws Exception {
        when(usuarioFacade.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/usuario/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /usuario/username/{username} debe devolver usuario cuando existe")
    void getUserByUsernameFound() throws Exception {
        when(usuarioFacade.findByUsername("brenis")).thenReturn(Optional.of(usuarioDto));

        mockMvc.perform(get("/usuario/username/brenis"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("brenis"));
    }

    @Test
    @DisplayName("GET /usuario/username/{username} debe devolver 404 cuando no existe")
    void getUserByUsernameNotFound() throws Exception {
        when(usuarioFacade.findByUsername("noexiste")).thenReturn(Optional.empty());

        mockMvc.perform(get("/usuario/username/noexiste"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /usuario debe crear usuario")
    void createUser() throws Exception {
        UsuarioCreateRequest req = new UsuarioCreateRequest();
        req.setUsername("nuevo");
        req.setEmail("nuevo@example.com");
        req.setPassword("abc123");

        when(usuarioFacade.create(any())).thenReturn(usuarioDto);

        mockMvc.perform(post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("brenis"));
    }

    @Test
    @DisplayName("PUT /usuario/{id} debe actualizar usuario cuando existe")
    void updateUserFound() throws Exception {
        UsuarioRequest req = new UsuarioRequest();
        req.setUsername("actualizado");
        req.setEmail("a@example.com");
        req.setAdmin(true);

        UsuarioDto actualizado = new UsuarioDto();
        actualizado.setId(1L);
        actualizado.setUsername("actualizado");
        actualizado.setEmail("a@example.com");
        actualizado.setAdmin(true);

        when(usuarioFacade.update(any(), eq(1L))).thenReturn(Optional.of(actualizado));

        mockMvc.perform(put("/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("actualizado"))
                .andExpect(jsonPath("$.admin").value(true));
    }

    @Test
    @DisplayName("PUT /usuario/{id} debe devolver 404 cuando no existe")
    void updateUserNotFound() throws Exception {
        UsuarioRequest req = new UsuarioRequest();
        req.setUsername("noexiste");
        req.setEmail("no@example.com");

        when(usuarioFacade.update(any(), eq(99L))).thenReturn(Optional.empty());

        mockMvc.perform(put("/usuario/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /usuario/{id} debe eliminar usuario cuando existe")
    void deleteUserFound() throws Exception {
        when(usuarioFacade.findById(1L)).thenReturn(Optional.of(usuarioDto));
        doNothing().when(usuarioFacade).delete(1L);

        mockMvc.perform(delete("/usuario/1"))
                .andExpect(status().isNoContent());

        verify(usuarioFacade, times(1)).delete(1L);
    }

    @Test
    @DisplayName("DELETE /usuario/{id} debe devolver 404 cuando no existe")
    void deleteUserNotFound() throws Exception {
        when(usuarioFacade.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/usuario/99"))
                .andExpect(status().isNotFound());

        verify(usuarioFacade, never()).delete(anyLong());
    }
}