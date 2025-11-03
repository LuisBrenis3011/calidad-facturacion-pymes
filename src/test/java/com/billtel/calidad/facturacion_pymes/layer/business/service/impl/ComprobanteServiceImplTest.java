package com.billtel.calidad.facturacion_pymes.layer.business.service.impl;

import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Producto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.Comprobante;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.DetalleComprobante;
import com.billtel.calidad.facturacion_pymes.layer.persistence.ComprobanteRepository;
import com.billtel.calidad.facturacion_pymes.layer.persistence.EmpresaRepository;
import com.billtel.calidad.facturacion_pymes.layer.persistence.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComprobanteServiceImplTest {

    @Mock
    private ComprobanteRepository comprobanteRepository;
    @Mock
    private EmpresaRepository empresaRepository;
    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ComprobanteServiceImpl comprobanteService;

    private Empresa empresa;
    private Producto producto;
    private Comprobante comprobanteBase;
    private DetalleComprobante detalle;

    @BeforeEach
    void setUp() {
        empresa = new Empresa();
        empresa.setId(1L);
        empresa.setRazonSocial("Empresa Test");
        empresa.setRuc("12345678901");
        empresa.setDireccion("Av. Siempre Viva 123");

        producto = new Producto();
        producto.setId(10L);
        producto.setNombre("Mouse Gamer");
        producto.setValorUnitario(BigDecimal.valueOf(100));
        producto.setIgv(BigDecimal.valueOf(18));
        producto.setUnidadMedida("UN");

        comprobanteBase = new Comprobante();
        comprobanteBase.setSerie("F001");
        comprobanteBase.setNroDocCliente("12345678");
        comprobanteBase.setNombreCliente("Juan Pérez");
        comprobanteBase.setDireccionCliente("Lima");
        comprobanteBase.setSubtotal(BigDecimal.ZERO);
        comprobanteBase.setIgvTotal(BigDecimal.ZERO);
        comprobanteBase.setTotal(BigDecimal.ZERO);

        detalle = new DetalleComprobante();
        detalle.setProducto(producto);
        detalle.setCantidad(2);
    }

    @Test
    void testGetNextCorrelativo_CuandoExisteComprobantePrevio() {
        Comprobante ultimo = new Comprobante();
        ultimo.setCorrelativo(5);

        when(comprobanteRepository.findTopByEmpresaIdAndSerieOrderByCorrelativoDesc(1L, "F001"))
                .thenReturn(Optional.of(ultimo));

        Integer siguiente = comprobanteService.getNextCorrelativo(1L, "F001");

        assertThat(siguiente).isEqualTo(6);
        verify(comprobanteRepository).findTopByEmpresaIdAndSerieOrderByCorrelativoDesc(1L, "F001");
    }

    @Test
    void testGetNextCorrelativo_SiNoHayComprobantePrevio() {
        when(comprobanteRepository.findTopByEmpresaIdAndSerieOrderByCorrelativoDesc(1L, "F001"))
                .thenReturn(Optional.empty());

        Integer siguiente = comprobanteService.getNextCorrelativo(1L, "F001");

        assertThat(siguiente).isEqualTo(1);
    }

    @Test
    void testCreateComprobante_Correcto() {
        when(empresaRepository.findById(1L)).thenReturn(Optional.of(empresa));
        when(productoRepository.findById(10L)).thenReturn(Optional.of(producto));
        when(comprobanteRepository.findTopByEmpresaIdAndSerieOrderByCorrelativoDesc(1L, "F001"))
                .thenReturn(Optional.empty());
        when(comprobanteRepository.save(any(Comprobante.class))).thenAnswer(i -> i.getArgument(0));

        Comprobante resultado = comprobanteService.createComprobante(comprobanteBase, 1L, List.of(detalle));

        assertThat(resultado.getEmpresa()).isEqualTo(empresa);
        assertThat(resultado.getCorrelativo()).isEqualTo(1);
        assertThat(resultado.getDetalles()).hasSize(1);
        assertThat(resultado.getSubtotal()).isEqualByComparingTo("200.00");
        assertThat(resultado.getIgvTotal()).isEqualByComparingTo("36.00");
        assertThat(resultado.getTotal()).isEqualByComparingTo("236.00");
        assertThat(resultado.getEstadoSunat()).isEqualTo(Comprobante.EstadoSunat.PENDIENTE);
        assertThat(resultado.getFechaEmision()).isBeforeOrEqualTo(LocalDateTime.now());

        verify(comprobanteRepository).save(any(Comprobante.class));
    }

    @Test
    void testCreateComprobante_EmpresaNoExiste() {
        when(empresaRepository.findById(1L)).thenReturn(Optional.empty());

        // Acción encapsulada en un Executable
        Executable action = () -> comprobanteService.createComprobante(comprobanteBase, 1L, List.of(detalle));

        // Verificación de la excepción
        RuntimeException ex = assertThrows(RuntimeException.class, action);

        assertThat(ex.getMessage()).isEqualTo("Empresa no encontrada");
        verify(empresaRepository).findById(1L);
        verifyNoInteractions(productoRepository);
    }

    @Test
    void testCreateComprobante_ProductoNoExiste() {
        when(empresaRepository.findById(1L)).thenReturn(Optional.of(empresa));
        when(productoRepository.findById(10L)).thenReturn(Optional.empty());

        // Acción encapsulada en un Executable
        Executable action = () -> comprobanteService.createComprobante(comprobanteBase, 1L, List.of(detalle));

        // Verificación de la excepción
        RuntimeException ex = assertThrows(RuntimeException.class, action);

        assertThat(ex.getMessage()).isEqualTo("Producto no encontrado");
    }

}

