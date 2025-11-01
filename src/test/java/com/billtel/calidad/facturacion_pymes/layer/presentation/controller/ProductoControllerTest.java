package com.billtel.calidad.facturacion_pymes.layer.presentation.controller;

import com.billtel.calidad.facturacion_pymes.layer.business.facade.IProductoFacade;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.ProductoRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.ProductoDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
    void testListAllProductos() throws Exception {
        Mockito.when(productoFacade.findAll())
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
    void testListProductosByEmpresa() throws Exception {
        Mockito.when(productoFacade.findByEmpresaId(1L))
                .thenReturn(List.of(
                        new ProductoDto(3L, "Borrador", "Suave blanco", new BigDecimal("1.00"), "UND")
                ));

        mockMvc.perform(get("/producto/empresa/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Borrador"));
    }

    @Test
    void testGetProductoByIdFound() throws Exception {
        Mockito.when(productoFacade.findById(1L))
                .thenReturn(Optional.of(new ProductoDto(1L, "Regla", "30cm transparente", new BigDecimal("3.50"), "UND")));

        mockMvc.perform(get("/producto/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Regla"))
                .andExpect(jsonPath("$.valorUnitario").value(3.50));
    }

    @Test
    void testGetProductoByIdNotFound() throws Exception {
        Mockito.when(productoFacade.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/producto/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateProducto() throws Exception {
        ProductoDto nuevo = new ProductoDto(10L, "Tijeras", "Corte fino", new BigDecimal("4.50"), "UND");
        Mockito.when(productoFacade.create(Mockito.any(ProductoRequest.class))).thenReturn(nuevo);

        mockMvc.perform(post("/producto/empresa/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Tijeras\", \"descripcion\": \"Corte fino\", \"valorUnitario\": 4.5, \"unidadMedida\": \"UND\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Tijeras"))
                .andExpect(jsonPath("$.descripcion").value("Corte fino"))
                .andExpect(jsonPath("$.valorUnitario").value(4.5));
    }

    @Test
    void testUpdateProductoFound() throws Exception {
        ProductoDto actualizado = new ProductoDto(1L, "Tijeras Pro", "Corte premium", new BigDecimal("5.50"), "UND");
        Mockito.when(productoFacade.update(Mockito.eq(1L), Mockito.any(ProductoRequest.class)))
                .thenReturn(Optional.of(actualizado));

        mockMvc.perform(put("/producto/1/empresa/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Tijeras Pro\", \"descripcion\": \"Corte premium\", \"valorUnitario\": 5.5, \"unidadMedida\": \"UND\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Tijeras Pro"))
                .andExpect(jsonPath("$.valorUnitario").value(5.5));
    }

    @Test
    void testUpdateProductoNotFound() throws Exception {
        Mockito.when(productoFacade.update(Mockito.eq(99L), Mockito.any(ProductoRequest.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/producto/99/empresa/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"NoExiste\", \"descripcion\": \"Nada\", \"valorUnitario\": 10.0, \"unidadMedida\": \"UND\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteProductoFound() throws Exception {
        Mockito.when(productoFacade.deleteByIdAndEmpresaId(1L, 2L)).thenReturn(true);

        mockMvc.perform(delete("/producto/1/empresa/2"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteProductoNotFound() throws Exception {
        Mockito.when(productoFacade.deleteByIdAndEmpresaId(99L, 1L)).thenReturn(false);

        mockMvc.perform(delete("/producto/99/empresa/1"))
                .andExpect(status().isNotFound());
    }
}
