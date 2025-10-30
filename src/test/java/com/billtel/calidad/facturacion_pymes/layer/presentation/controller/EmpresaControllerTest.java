package com.billtel.calidad.facturacion_pymes.layer.presentation.controller;
import com.billtel.calidad.facturacion_pymes.layer.business.facade.IEmpresaFacade;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.EmpresaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.EmpresaDto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmpresaController.class)
class EmpresaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IEmpresaFacade iEmpresaFacade;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListByUsuario() throws Exception {
        EmpresaDto dto = new EmpresaDto();
        dto.setId(1L);
        List<EmpresaDto> list = Collections.singletonList(dto);

        when(iEmpresaFacade.findByUsuarioId(1L)).thenReturn(list);

        mockMvc.perform(get("/empresa/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));

        verify(iEmpresaFacade, times(1)).findByUsuarioId(1L);
    }

    @Test
    void testCreate() throws Exception {
        EmpresaRequest request = new EmpresaRequest();
        request.setRazonSocial("Mi Empresa");

        EmpresaDto dto = new EmpresaDto();
        dto.setId(3L);
        dto.setRazonSocial("Mi Empresa");

        when(iEmpresaFacade.create(any(EmpresaRequest.class))).thenReturn(dto);

        mockMvc.perform(post("/empresa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.razonSocial").value("Mi Empresa"));
    }

    @Test
    void testDetailsByUsuario_NotFound() throws Exception {
        when(iEmpresaFacade.findByIdAndUsuarioId(2L, 1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/empresa/2/usuario/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteByUsuario_Found() throws Exception {
        EmpresaDto dto = new EmpresaDto();
        when(iEmpresaFacade.findByIdAndUsuarioId(1L, 1L)).thenReturn(Optional.of(dto));

        mockMvc.perform(delete("/empresa/1/usuario/1"))
                .andExpect(status().isNoContent());

        verify(iEmpresaFacade, times(1)).deleteByIdAndUsuarioId(1L, 1L);
    }
}
