package com.billtel.calidad.facturacion_pymes.layer.presentation.controller;

import com.billtel.calidad.facturacion_pymes.layer.business.facade.IProductoFacade;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.ProductoRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.ProductoDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IProductoFacade productoFacade;

    @Test
    @DisplayName("GET /producto debe listar todos los productos")
    void listAllProductos() throws Exception {
        when(productoFacade.findAll())
                .thenReturn(List.of(
                        new ProductoDto(1L, "Lapicero", "Azul punta fina", new BigDecimal("2.50"), "UND"),
                        new ProductoDto(2L, "Cuaderno", "Cuadriculado 100 hojas", new BigDecimal("5.00"), "UND")
                ));

        mockMvc.perform(get("/producto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Lapicero"))
                .andExpect(jsonPath("$[1].nombre").value("Cuaderno"));
    }

    @Test
    @DisplayName("GET /producto/empresa/{empresaId} debe listar productos de la empresa")
    void listProductosByEmpresa() throws Exception {
        when(productoFacade.findByEmpresaId(1L))
                .thenReturn(List.of(
                        new ProductoDto(3L, "Borrador", "Suave blanco", new BigDecimal("1.00"), "UND")
                ));

        mockMvc.perform(get("/producto/empresa/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Borrador"));
    }

    @Test
    @DisplayName("GET /producto/{id} debe devolver producto cuando existe")
    void getProductoByIdFound() throws Exception {
        when(productoFacade.findById(1L))
                .thenReturn(Optional.of(new ProductoDto(1L, "Regla", "30cm transparente", new BigDecimal("3.50"), "UND")));

        mockMvc.perform(get("/producto/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Regla"))
                .andExpect(jsonPath("$.valorUnitario").value(3.50));
    }

    @Test
    @DisplayName("GET /producto/{id} debe devolver 404 cuando no existe")
    void getProductoByIdNotFound() throws Exception {
        when(productoFacade.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/producto/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /producto/{id}/empresa/{empresaId} debe devolver producto de empresa cuando existe")
    void getProductoByIdAndEmpresaFound() throws Exception {
        when(productoFacade.findByIdAndEmpresaId(1L, 2L))
                .thenReturn(Optional.of(new ProductoDto(1L, "Pegamento", "Extra fuerte", new BigDecimal("2.00"), "UND")));

        mockMvc.perform(get("/producto/1/empresa/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Pegamento"));
    }

    @Test
    @DisplayName("GET /producto/{id}/empresa/{empresaId} debe devolver 404 cuando no existe")
    void getProductoByIdAndEmpresaNotFound() throws Exception {
        when(productoFacade.findByIdAndEmpresaId(99L, 2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/producto/99/empresa/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /producto/empresa/{empresaId} debe crear producto")
    void createProducto() throws Exception {
        ProductoDto nuevo = new ProductoDto(10L, "Tijeras", "Corte fino", new BigDecimal("4.50"), "UND");
        when(productoFacade.create(any(ProductoRequest.class))).thenReturn(nuevo);

        mockMvc.perform(post("/producto/empresa/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Tijeras\", \"descripcion\": \"Corte fino\", \"valorUnitario\": 4.5, \"unidadMedida\": \"UND\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Tijeras"))
                .andExpect(jsonPath("$.descripcion").value("Corte fino"))
                .andExpect(jsonPath("$.valorUnitario").value(4.5));
    }

    @Test
    @DisplayName("PUT /producto/{id}/empresa/{empresaId} debe actualizar producto cuando existe")
    void updateProductoFound() throws Exception {
        ProductoDto actualizado = new ProductoDto(1L, "Tijeras Pro", "Corte premium", new BigDecimal("5.50"), "UND");
        when(productoFacade.update(eq(1L), any(ProductoRequest.class)))
                .thenReturn(Optional.of(actualizado));

        mockMvc.perform(put("/producto/1/empresa/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Tijeras Pro\", \"descripcion\": \"Corte premium\", \"valorUnitario\": 5.5, \"unidadMedida\": \"UND\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Tijeras Pro"))
                .andExpect(jsonPath("$.valorUnitario").value(5.5));
    }

    @Test
    @DisplayName("PUT /producto/{id}/empresa/{empresaId} debe devolver 404 cuando no existe")
    void updateProductoNotFound() throws Exception {
        when(productoFacade.update(eq(99L), any(ProductoRequest.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/producto/99/empresa/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"NoExiste\", \"descripcion\": \"Nada\", \"valorUnitario\": 10.0, \"unidadMedida\": \"UND\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /producto/{id}/empresa/{empresaId} debe eliminar producto cuando existe")
    void deleteProductoFound() throws Exception {
        when(productoFacade.deleteByIdAndEmpresaId(1L, 2L)).thenReturn(true);

        mockMvc.perform(delete("/producto/1/empresa/2"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /producto/{id}/empresa/{empresaId} debe devolver 404 cuando no existe")
    void deleteProductoNotFound() throws Exception {
        when(productoFacade.deleteByIdAndEmpresaId(99L, 1L)).thenReturn(false);

        mockMvc.perform(delete("/producto/99/empresa/1"))
                .andExpect(status().isNotFound());
    }
}