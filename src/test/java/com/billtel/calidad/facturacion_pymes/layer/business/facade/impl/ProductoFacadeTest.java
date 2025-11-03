package com.billtel.calidad.facturacion_pymes.layer.business.facade.impl;

import com.billtel.calidad.facturacion_pymes.layer.business.mapper.producto_mapper.ProductoDtoMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.mapper.producto_mapper.ProductoRequestMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.service.IProductoService;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.ProductoRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.ProductoDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoFacadeTest {

    @Mock
    private IProductoService productoService;
    @Mock
    private ProductoRequestMapper productoRequestMapper;
    @Mock
    private ProductoDtoMapper productoDtoMapper;

    @InjectMocks
    private ProductoFacade productoFacade;

    private Producto producto;
    private ProductoDto productoDto;
    private ProductoRequest productoRequest;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Laptop Lenovo");
        producto.setValorUnitario(new BigDecimal("2500.00"));

        productoDto = new ProductoDto();
        productoDto.setId(1L);
        productoDto.setNombre("Laptop Lenovo");
        productoDto.setValorUnitario(new BigDecimal("2500.00"));

        productoRequest = new ProductoRequest();
        productoRequest.setNombre("Laptop Lenovo");
        productoRequest.setValorUnitario(new BigDecimal("2500.00"));
    }

    @Test
    void testFindAll_ShouldReturnMappedList() {
        when(productoService.findAll()).thenReturn(List.of(producto));
        when(productoDtoMapper.toDto(producto)).thenReturn(productoDto);

        List<ProductoDto> result = productoFacade.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNombre()).isEqualTo("Laptop Lenovo");
        verify(productoService).findAll();
    }

    @Test
    void testFindByEmpresaId_ShouldReturnMappedList() {
        when(productoService.findByEmpresaId(10L)).thenReturn(List.of(producto));
        when(productoDtoMapper.toDto(producto)).thenReturn(productoDto);

        List<ProductoDto> result = productoFacade.findByEmpresaId(10L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getValorUnitario()).isEqualTo(new BigDecimal("2500.00"));
    }

    @Test
    void testFindById_ShouldReturnMappedOptional() {
        when(productoService.findById(1L)).thenReturn(Optional.of(producto));
        when(productoDtoMapper.toDto(producto)).thenReturn(productoDto);

        Optional<ProductoDto> result = productoFacade.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
    }

    @Test
    void testFindByIdAndEmpresaId_ShouldReturnMappedOptional() {
        when(productoService.findByIdAndEmpresaId(1L, 10L)).thenReturn(Optional.of(producto));
        when(productoDtoMapper.toDto(producto)).thenReturn(productoDto);

        Optional<ProductoDto> result = productoFacade.findByIdAndEmpresaId(1L, 10L);

        assertThat(result).isPresent();
        assertThat(result.get().getNombre()).isEqualTo("Laptop Lenovo");
    }

    @Test
    void testCreate_ShouldMapRequestAndReturnDto() {
        when(productoRequestMapper.toDomain(productoRequest)).thenReturn(producto);
        when(productoService.save(producto)).thenReturn(producto);
        when(productoDtoMapper.toDto(producto)).thenReturn(productoDto);

        ProductoDto result = productoFacade.create(productoRequest);

        assertThat(result.getNombre()).isEqualTo("Laptop Lenovo");
        verify(productoService).save(producto);
    }

    @Test
    void testUpdate_ShouldMapRequestAndReturnMappedOptional() {
        when(productoRequestMapper.toDomain(productoRequest)).thenReturn(producto);
        when(productoService.update(1L, producto)).thenReturn(Optional.of(producto));
        when(productoDtoMapper.toDto(producto)).thenReturn(productoDto);

        Optional<ProductoDto> result = productoFacade.update(1L, productoRequest);

        assertThat(result).isPresent();
        assertThat(result.get().getValorUnitario()).isEqualTo(new BigDecimal("2500.00"));
    }

    @Test
    void testDeleteById_ShouldCallService() {
        productoFacade.deleteById(1L);
        verify(productoService).deleteById(1L);
    }

    @Test
    void testDeleteByIdAndEmpresaId_ShouldReturnTrueWhenExists() {
        when(productoService.findByIdAndEmpresaId(1L, 10L)).thenReturn(Optional.of(producto));

        boolean result = productoFacade.deleteByIdAndEmpresaId(1L, 10L);

        assertThat(result).isTrue();
        verify(productoService).deleteById(1L);
    }

    @Test
    void testDeleteByIdAndEmpresaId_ShouldReturnFalseWhenNotExists() {
        when(productoService.findByIdAndEmpresaId(1L, 10L)).thenReturn(Optional.empty());

        boolean result = productoFacade.deleteByIdAndEmpresaId(1L, 10L);

        assertThat(result).isFalse();
        verify(productoService, never()).deleteById(any());
    }
}
