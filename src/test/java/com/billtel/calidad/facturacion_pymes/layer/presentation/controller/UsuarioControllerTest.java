package com.billtel.calidad.facturacion_pymes.layer.presentation.controller;

import com.billtel.calidad.facturacion_pymes.layer.business.facade.IUsuarioFacade;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.UsuarioCreateRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.UsuarioRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.UsuarioDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

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
    @DisplayName("GET /usuario/{id} debe devolver usuario")
    void getUserById() throws Exception {
        when(usuarioFacade.findById(1L)).thenReturn(Optional.of(usuarioDto));

        mockMvc.perform(get("/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("brenis@example.com"));
    }

    @Test
    @DisplayName("GET /usuario/username/{username} debe devolver usuario por username")
    void getUserByUsername() throws Exception {
        when(usuarioFacade.findByUsername("brenis")).thenReturn(Optional.of(usuarioDto));

        mockMvc.perform(get("/usuario/username/brenis"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("brenis"));
    }

    @Test
    @DisplayName("POST /usuario debe crear usuario sin validaciones activas")
    void createUser() throws Exception {
        UsuarioCreateRequest req = new UsuarioCreateRequest();
        req.setUsername("nuevo");
        req.setEmail("nuevo@example.com");
        req.setPassword("abc123");

        when(usuarioFacade.create(ArgumentMatchers.any())).thenReturn(usuarioDto);

        mockMvc.perform(post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("brenis"));
    }

    @Test
    @DisplayName("PUT /usuario/{id} debe actualizar usuario")
    void updateUser() throws Exception {
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
    @DisplayName("DELETE /usuario/{id} debe eliminar usuario")
    void deleteUser() throws Exception {
        when(usuarioFacade.findById(1L)).thenReturn(Optional.of(usuarioDto));
        doNothing().when(usuarioFacade).delete(1L);

        mockMvc.perform(delete("/usuario/1"))
                .andExpect(status().isNoContent());
    }
}
