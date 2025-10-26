package com.billtel.calidad.facturacion_pymes.layer.presentation.controller;

import com.billtel.calidad.facturacion_pymes.layer.business.facade.IProductoFacade;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.ProductoDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.ProductoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IProductoFacade productoFacade;

    private ProductoDto productoDto;

    @BeforeEach
    void setUp() {
        productoDto = new ProductoDto();
        productoDto.setId(1L);
        productoDto.setNombre("Laptop");
        productoDto.setValorUnitario(BigDecimal.valueOf(1500.0));
    }

    @Test
    void testListByEmpresa() throws Exception {
        Mockito.when(productoFacade.findByEmpresaId(1L)).thenReturn(List.of(productoDto));

        mockMvc.perform(get("/producto/empresa/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Laptop"));
    }

    @Test
    void testListAll() throws Exception {
        Mockito.when(productoFacade.findAll()).thenReturn(List.of(productoDto));

        mockMvc.perform(get("/producto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void testDetailsByEmpresa_found() throws Exception {
        Mockito.when(productoFacade.findByIdAndEmpresaId(1L, 2L)).thenReturn(Optional.of(productoDto));

        mockMvc.perform(get("/producto/1/empresa/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Laptop"));
    }

    @Test
    void testDetailsByEmpresa_notFound() throws Exception {
        Mockito.when(productoFacade.findByIdAndEmpresaId(1L, 2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/producto/1/empresa/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDetails_found() throws Exception {
        Mockito.when(productoFacade.findById(1L)).thenReturn(Optional.of(productoDto));

        mockMvc.perform(get("/producto/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Laptop"));
    }

    @Test
    void testDetails_notFound() throws Exception {
        Mockito.when(productoFacade.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/producto/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreate() throws Exception {
        ProductoRequest request = new ProductoRequest();
        request.setNombre("Mouse");
        request.setValorUnitario(BigDecimal.valueOf(25.0));
        request.setEmpresaId(5L);

        Mockito.when(productoFacade.create(any(ProductoRequest.class))).thenReturn(productoDto);

        mockMvc.perform(post("/producto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Mouse\",\"precio\":25.0,\"empresaId\":5}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testUpdateByEmpresa_found() throws Exception {
        Mockito.when(productoFacade.findByIdAndEmpresaId(1L, 2L)).thenReturn(Optional.of(productoDto));
        Mockito.when(productoFacade.create(any(ProductoRequest.class))).thenReturn(productoDto);

        mockMvc.perform(put("/producto/1/empresa/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Laptop actualizada\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testUpdateByEmpresa_notFound() throws Exception {
        Mockito.when(productoFacade.findByIdAndEmpresaId(1L, 2L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/producto/1/empresa/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"No existe\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteByEmpresa_found() throws Exception {
        Mockito.when(productoFacade.findByIdAndEmpresaId(1L, 2L)).thenReturn(Optional.of(productoDto));

        mockMvc.perform(delete("/producto/1/empresa/2"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteByEmpresa_notFound() throws Exception {
        Mockito.when(productoFacade.findByIdAndEmpresaId(1L, 2L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/producto/1/empresa/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelete_found() throws Exception {
        Mockito.when(productoFacade.findById(1L)).thenReturn(Optional.of(productoDto));

        mockMvc.perform(delete("/producto/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDelete_notFound() throws Exception {
        Mockito.when(productoFacade.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/producto/1"))
                .andExpect(status().isNotFound());
    }
}
