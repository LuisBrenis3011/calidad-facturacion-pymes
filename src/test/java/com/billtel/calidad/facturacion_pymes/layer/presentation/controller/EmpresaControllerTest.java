package com.billtel.calidad.facturacion_pymes.layer.presentation.controller;

import com.billtel.calidad.facturacion_pymes.layer.business.facade.IEmpresaFacade;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.EmpresaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.EmpresaDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

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
    @DisplayName("GET /empresa debe retornar lista de empresas del usuario")
    void listEmpresasPorUsuario() throws Exception {
        when(empresaFacade.findByUsername("brenis"))
                .thenReturn(List.of(empresaDto));

        mockMvc.perform(get("/empresa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].razonSocial").value("TechCorp"))
                .andExpect(jsonPath("$[0].ruc").value("12345678901"));
    }

    @Test
    @DisplayName("GET /empresa debe retornar 401 si no hay usuario autenticado")
    void listEmpresasSinUsuario() throws Exception {
        mockMvc.perform(get("/empresa"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "brenis", roles = "USER")
    @DisplayName("POST /empresa debe crear una empresa para el usuario autenticado")
    void createEmpresa() throws Exception {
        EmpresaRequest req = new EmpresaRequest();
        req.setRazonSocial("NuevaCorp");
        req.setRuc("11122233344");
        req.setDireccion("Calle Nueva 456");

        when(empresaFacade.create(ArgumentMatchers.any())).thenReturn(empresaDto);

        mockMvc.perform(post("/empresa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.razonSocial").value("TechCorp"));
    }

    @Test
    @WithMockUser(username = "brenis", roles = "USER")
    @DisplayName("PUT /empresa/{id} debe actualizar una empresa existente")
    void updateEmpresa() throws Exception {
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
    @DisplayName("PUT /empresa/{id} debe retornar 401 si no hay usuario autenticado")
    void updateEmpresaSinUsuario() throws Exception {
        EmpresaRequest req = new EmpresaRequest();
        req.setRazonSocial("Fake");

        mockMvc.perform(put("/empresa/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "brenis", roles = "USER")
    @DisplayName("DELETE /empresa/{id} debe eliminar empresa si hay usuario autenticado")
    void deleteEmpresa() throws Exception {
        doNothing().when(empresaFacade).deleteById(1L);

        mockMvc.perform(delete("/empresa/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /empresa/{id} debe retornar 401 si no hay usuario autenticado")
    void deleteEmpresaSinUsuario() throws Exception {
        mockMvc.perform(delete("/empresa/1"))
                .andExpect(status().isUnauthorized());
    }
}
