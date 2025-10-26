package com.billtel.calidad.facturacion_pymes.layer.presentation.controller;

import com.billtel.calidad.facturacion_pymes.layer.business.facade.IComprobanteFacade;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobanteRequest.BoletaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobanteRequest.ComprobanteRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobanteRequest.FacturaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.comprobanteResponse.BoletaDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.comprobanteResponse.ComprobanteDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.comprobanteResponse.FacturaDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ComprobanteController.class)
class ComprobanteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IComprobanteFacade comprobanteFacade;

    @Test
    void testListByEmpresa() throws Exception {
        when(comprobanteFacade.findByEmpresaId(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/comprobante/empresa/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testListAll() throws Exception {
        when(comprobanteFacade.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/comprobante"))
                .andExpect(status().isOk());
    }

    @Test
    void testDetailsByEmpresaFound() throws Exception {
        ComprobanteDto dto = new FacturaDto();
        when(comprobanteFacade.findByIdAndEmpresaId(1L, 1L)).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/comprobante/1/empresa/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDetailsByEmpresaNotFound() throws Exception {
        when(comprobanteFacade.findByIdAndEmpresaId(1L, 1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/comprobante/1/empresa/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateFactura() throws Exception {
        ComprobanteDto dto = new FacturaDto();
        when(comprobanteFacade.create(any(FacturaRequest.class))).thenReturn(dto);

        mockMvc.perform(post("/comprobante/factura")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateBoleta() throws Exception {
        ComprobanteDto dto = new BoletaDto();
        when(comprobanteFacade.create(any(BoletaRequest.class))).thenReturn(dto);

        mockMvc.perform(post("/comprobante/boleta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isCreated());
    }

//    @Test
//    void testCreateGeneric() throws Exception {
//        ComprobanteDto dto = new ComprobanteDto();
//        when(comprobanteFacade.create(any(ComprobanteRequest.class))).thenReturn(dto);
//
//        mockMvc.perform(post("/comprobante")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{}"))
//                .andExpect(status().isCreated());
//    }

    @Test
    void testDeleteFound() throws Exception {
        when(comprobanteFacade.findById(1L)).thenReturn(Optional.of(new FacturaDto()));

        mockMvc.perform(delete("/comprobante/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteNotFound() throws Exception {
        when(comprobanteFacade.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/comprobante/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteByEmpresaFound() throws Exception {
        when(comprobanteFacade.findByIdAndEmpresaId(eq(1L), eq(1L)))
                .thenReturn(Optional.of(new BoletaDto()));

        mockMvc.perform(delete("/comprobante/1/empresa/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteByEmpresaNotFound() throws Exception {
        when(comprobanteFacade.findByIdAndEmpresaId(eq(1L), eq(1L)))
                .thenReturn(Optional.empty());

        mockMvc.perform(delete("/comprobante/1/empresa/1"))
                .andExpect(status().isNotFound());
    }
}