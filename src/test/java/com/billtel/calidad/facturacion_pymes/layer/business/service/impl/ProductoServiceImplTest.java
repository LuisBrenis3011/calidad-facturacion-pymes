package com.billtel.calidad.facturacion_pymes.layer.business.service.impl;

import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Producto;
import com.billtel.calidad.facturacion_pymes.layer.persistence.EmpresaRepository;
import com.billtel.calidad.facturacion_pymes.layer.persistence.ProductoRepository;
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
class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private EmpresaRepository empresaRepository;

    @InjectMocks
    private ProductoServiceImpl productoService;

    private Producto producto;
    private Empresa empresa;

    @BeforeEach
    void setUp() {
        empresa = new Empresa();
        empresa.setId(1L);

        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Laptop Gamer");
        producto.setValorUnitario(new BigDecimal("1000.00"));
        producto.setEmpresa(empresa);
    }

    @Test
    void findAll_debeRetornarListaDeProductos() {
        when(productoRepository.findAll()).thenReturn(List.of(producto));

        List<Producto> resultado = productoService.findAll();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Laptop Gamer");
        verify(productoRepository).findAll();
    }

    @Test
    void findByEmpresaId_debeRetornarProductosDeUnaEmpresa() {
        when(productoRepository.findByEmpresaId(1L)).thenReturn(List.of(producto));

        List<Producto> resultado = productoService.findByEmpresaId(1L);

        assertThat(resultado).isNotEmpty();
        verify(productoRepository).findByEmpresaId(1L);
    }

    @Test
    void findById_debeRetornarProductoPorId() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Optional<Producto> resultado = productoService.findById(1L);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Laptop Gamer");
        verify(productoRepository).findById(1L);
    }

    @Test
    void save_debeCalcularIGVAntesDeGuardar() {
        when(productoRepository.save(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Producto guardado = productoService.save(producto);

        assertThat(guardado.getIgv()).isEqualTo(new BigDecimal("180.0000"));
        verify(productoRepository).save(producto);
    }

    @Test
    void update_debeActualizarProductoCuandoExiste() {
        Producto actualizado = new Producto();
        actualizado.setNombre("Laptop Pro");
        actualizado.setDescripcion("Nueva versión mejorada");
        actualizado.setValorUnitario(new BigDecimal("2000.00"));
        actualizado.setEmpresa(empresa);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Producto> resultado = productoService.update(1L, actualizado);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Laptop Pro");
        verify(productoRepository).save(any(Producto.class));
    }

    @Test
    void deleteById_debeEliminarProductoPorId() {
        productoService.deleteById(1L);
        verify(productoRepository).deleteById(1L);
    }

    @Test
    void deleteByIdAndEmpresaId_debeEliminarSiExiste() {
        when(productoRepository.findByIdAndEmpresaId(1L, 1L)).thenReturn(Optional.of(producto));

        productoService.deleteByIdAndEmpresaId(1L, 1L);

        verify(productoRepository).deleteById(1L);
    }
}

