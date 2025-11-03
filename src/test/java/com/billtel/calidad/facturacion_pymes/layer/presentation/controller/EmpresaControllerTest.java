package com.billtel.calidad.facturacion_pymes.layer.presentation.controller;

import com.billtel.calidad.facturacion_pymes.layer.business.facade.IEmpresaFacade;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.EmpresaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.EmpresaDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmpresaController.class)
@AutoConfigureMockMvc(addFilters = false)
class EmpresaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IEmpresaFacade empresaFacade;

    @Autowired
    private ObjectMapper objectMapper;

    private EmpresaDto empresaDto;

    @BeforeEach
    void setUp() {
        empresaDto = new EmpresaDto();
        empresaDto.setId(1L);
        empresaDto.setRazonSocial("TechCorp");
        empresaDto.setDireccion("Av. Central 123");
        empresaDto.setRuc("12345678901");
    }

    @Test
    @WithMockUser(username = "brenis", roles = "USER")
    @DisplayName("GET /empresa debe retornar lista de empresas del usuario autenticado")
    void listEmpresasConUsuarioAutenticado() throws Exception {
        when(empresaFacade.findByUsername("brenis"))
                .thenReturn(List.of(empresaDto));

        mockMvc.perform(get("/empresa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].razonSocial").value("TechCorp"))
                .andExpect(jsonPath("$[0].ruc").value("12345678901"));
    }

    @Test
    @DisplayName("GET /empresa debe retornar 401 cuando no hay usuario autenticado")
    void listEmpresasSinUsuarioAutenticado() throws Exception {
        mockMvc.perform(get("/empresa"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "brenis", roles = "USER")
    @DisplayName("POST /empresa debe crear empresa para usuario autenticado")
    void createEmpresaConUsuarioAutenticado() throws Exception {
        EmpresaRequest req = new EmpresaRequest();
        req.setRazonSocial("NuevaCorp");
        req.setRuc("11122233344");
        req.setDireccion("Calle Nueva 456");

        when(empresaFacade.create(any())).thenReturn(empresaDto);

        mockMvc.perform(post("/empresa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.razonSocial").value("TechCorp"));
    }

    @Test
    @DisplayName("POST /empresa debe retornar 401 cuando no hay usuario autenticado")
    void createEmpresaSinUsuarioAutenticado() throws Exception {
        EmpresaRequest req = new EmpresaRequest();
        req.setRazonSocial("NuevaCorp");

        mockMvc.perform(post("/empresa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "brenis", roles = "USER")
    @DisplayName("PUT /empresa/{id} debe actualizar empresa cuando existe")
    void updateEmpresaExistente() throws Exception {
        EmpresaRequest req = new EmpresaRequest();
        req.setRazonSocial("TechCorp Updated");
        req.setDireccion("Av. Modificada 789");
        req.setRuc("99988877766");

        EmpresaDto updated = new EmpresaDto();
        updated.setId(1L);
        updated.setRazonSocial("TechCorp Updated");
        updated.setDireccion("Av. Modificada 789");
        updated.setRuc("99988877766");

        when(empresaFacade.update(any(), eq(1L))).thenReturn(Optional.of(updated));

        mockMvc.perform(put("/empresa/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.razonSocial").value("TechCorp Updated"));
    }

    @Test
    @WithMockUser(username = "brenis", roles = "USER")
    @DisplayName("PUT /empresa/{id} debe retornar 404 cuando no existe")
    void updateEmpresaNoExistente() throws Exception {
        EmpresaRequest req = new EmpresaRequest();
        req.setRazonSocial("NoExiste");

        when(empresaFacade.update(any(), eq(99L))).thenReturn(Optional.empty());

        mockMvc.perform(put("/empresa/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /empresa/{id} debe retornar 401 cuando no hay usuario autenticado")
    void updateEmpresaSinUsuarioAutenticado() throws Exception {
        EmpresaRequest req = new EmpresaRequest();
        req.setRazonSocial("Fake");

        mockMvc.perform(put("/empresa/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "brenis", roles = "USER")
    @DisplayName("DELETE /empresa/{id} debe eliminar empresa cuando hay usuario autenticado")
    void deleteEmpresaConUsuarioAutenticado() throws Exception {
        doNothing().when(empresaFacade).deleteById(1L);

        mockMvc.perform(delete("/empresa/1"))
                .andExpect(status().isNoContent());

        verify(empresaFacade, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("DELETE /empresa/{id} debe retornar 401 cuando no hay usuario autenticado")
    void deleteEmpresaSinUsuarioAutenticado() throws Exception {
        mockMvc.perform(delete("/empresa/1"))
                .andExpect(status().isUnauthorized());

        verify(empresaFacade, never()).deleteById(anyLong());
    }
}