package com.billtel.calidad.facturacion_pymes.layer.presentation.controller;

import com.billtel.calidad.facturacion_pymes.layer.business.facade.IComprobanteFacade;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobante_request.BoletaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobante_request.FacturaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.comprobante_response.BoletaDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.comprobante_response.ComprobanteDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.comprobante_response.FacturaDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ComprobanteController.class)
@AutoConfigureMockMvc(addFilters = false)
class ComprobanteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IComprobanteFacade comprobanteFacade;

    @Test
    @DisplayName("GET /comprobante/empresa/{empresaId} debe listar comprobantes de la empresa")
    void listComprobantesByEmpresa() throws Exception {
        when(comprobanteFacade.findByEmpresaId(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/comprobante/empresa/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /comprobante debe listar todos los comprobantes")
    void listAllComprobantes() throws Exception {
        when(comprobanteFacade.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/comprobante"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /comprobante/{id} debe devolver comprobante cuando existe")
    void detailsComprobanteFound() throws Exception {
        ComprobanteDto dto = new FacturaDto();
        when(comprobanteFacade.findById(1L)).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/comprobante/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /comprobante/{id} debe devolver 404 cuando no existe")
    void detailsComprobanteNotFound() throws Exception {
        when(comprobanteFacade.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/comprobante/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /comprobante/{id}/empresa/{empresaId} debe devolver comprobante de empresa cuando existe")
    void detailsByEmpresaFound() throws Exception {
        ComprobanteDto dto = new FacturaDto();
        when(comprobanteFacade.findByIdAndEmpresaId(1L, 1L)).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/comprobante/1/empresa/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /comprobante/{id}/empresa/{empresaId} debe devolver 404 cuando no existe")
    void detailsByEmpresaNotFound() throws Exception {
        when(comprobanteFacade.findByIdAndEmpresaId(1L, 1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/comprobante/1/empresa/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /comprobante/factura debe crear factura exitosamente")
    void createFacturaSuccess() throws Exception {
        ComprobanteDto dto = new FacturaDto();
        when(comprobanteFacade.create(any(FacturaRequest.class))).thenReturn(dto);

        mockMvc.perform(post("/comprobante/factura")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("POST /comprobante/factura debe devolver 400 cuando hay error")
    void createFacturaError() throws Exception {
        when(comprobanteFacade.create(any(FacturaRequest.class)))
                .thenThrow(new RuntimeException("Error de validación"));

        mockMvc.perform(post("/comprobante/factura")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Error de validación"));
    }

    @Test
    @DisplayName("POST /comprobante/boleta debe crear boleta exitosamente")
    void createBoletaSuccess() throws Exception {
        ComprobanteDto dto = new BoletaDto();
        when(comprobanteFacade.create(any(BoletaRequest.class))).thenReturn(dto);

        mockMvc.perform(post("/comprobante/boleta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("POST /comprobante/boleta debe devolver 400 cuando hay error")
    void createBoletaError() throws Exception {
        when(comprobanteFacade.create(any(BoletaRequest.class)))
                .thenThrow(new RuntimeException("Error en los datos"));

        mockMvc.perform(post("/comprobante/boleta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Error en los datos"));
    }

    @Test
    @DisplayName("DELETE /comprobante/{id} debe eliminar comprobante cuando existe")
    void deleteComprobanteFound() throws Exception {
        when(comprobanteFacade.findById(1L)).thenReturn(Optional.of(new FacturaDto()));
        doNothing().when(comprobanteFacade).deleteById(1L);

        mockMvc.perform(delete("/comprobante/1"))
                .andExpect(status().isNoContent());

        verify(comprobanteFacade, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("DELETE /comprobante/{id} debe devolver 404 cuando no existe")
    void deleteComprobanteNotFound() throws Exception {
        when(comprobanteFacade.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/comprobante/1"))
                .andExpect(status().isNotFound());

        verify(comprobanteFacade, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("DELETE /comprobante/{id}/empresa/{empresaId} debe eliminar cuando existe")
    void deleteByEmpresaFound() throws Exception {
        when(comprobanteFacade.findByIdAndEmpresaId(1L, 1L))
                .thenReturn(Optional.of(new BoletaDto()));
        doNothing().when(comprobanteFacade).deleteById(1L);

        mockMvc.perform(delete("/comprobante/1/empresa/1"))
                .andExpect(status().isNoContent());

        verify(comprobanteFacade, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("DELETE /comprobante/{id}/empresa/{empresaId} debe devolver 404 cuando no existe")
    void deleteByEmpresaNotFound() throws Exception {
        when(comprobanteFacade.findByIdAndEmpresaId(1L, 1L))
                .thenReturn(Optional.empty());

        mockMvc.perform(delete("/comprobante/1/empresa/1"))
                .andExpect(status().isNotFound());

        verify(comprobanteFacade, never()).deleteById(anyLong());
    }
}