package com.billtel.calidad.facturacion_pymes.layer.business.facade.impl;

import com.billtel.calidad.facturacion_pymes.layer.business.service.IComprobanteService;
import com.billtel.calidad.facturacion_pymes.layer.business.service.IProductoService;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobanteRequest.BoletaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobanteRequest.ComprobanteRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobanteRequest.DetalleComprobanteRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobanteRequest.FacturaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.comprobanteResponse.BoletaDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.comprobanteResponse.FacturaDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Producto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComprobanteFacadeTest {

    @Mock
    private IComprobanteService comprobanteService;

    @Mock
    private IProductoService productoService;

    @InjectMocks
    private ComprobanteFacade comprobanteFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private DetalleComprobante mockDetalle(Comprobante comprobante) {
        DetalleComprobante detalle = new DetalleComprobante();
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(BigDecimal.valueOf(50));
        detalle.setComprobante(comprobante);
        return detalle;
    }

    @Test
    void testFindAll() {
        var comprobante = new Factura();
        comprobante.setId(1L);
        comprobante.setTipoPago(TipoPago.AL_CONTADO);
        when(comprobanteService.findAll()).thenReturn(List.of(comprobante));

        var result = comprobanteFacade.findAll();
        assertEquals(1, result.size());
        var facturaDto = (FacturaDto) result.get(0);
        assertEquals(TipoPago.AL_CONTADO, facturaDto.getTipoPago());
    }

    @Test
    void testFindByEmpresaId() {
        var comprobante = new Boleta();
        comprobante.setId(2L);
        comprobante.setTipoDocumentoCliente(TipoDocumentoCliente.DNI);
        when(comprobanteService.findByEmpresaId(5L)).thenReturn(List.of(comprobante));

        var result = comprobanteFacade.findByEmpresaId(5L);

        assertEquals(1, result.size());

        // Casteamos correctamente a BoletaDto
        var boletaDto = (BoletaDto) result.get(0);
        assertEquals(TipoDocumentoCliente.DNI, boletaDto.getTipoDocumentoCliente());
    }

    @Test
    void testFindById() {
        var comprobante = new Factura();
        comprobante.setId(3L);
        comprobante.setTipoPago(TipoPago.A_CREDITO);
        when(comprobanteService.findById(3L)).thenReturn(Optional.of(comprobante));

        var result = comprobanteFacade.findById(3L);

        assertTrue(result.isPresent());

        // Casteamos correctamente a FacturaDto
        var facturaDto = (FacturaDto) result.get();
        assertEquals(TipoPago.A_CREDITO, facturaDto.getTipoPago());
    }

    @Test
    void testFindByIdAndEmpresaId() {
        var comprobante = new Boleta();
        comprobante.setId(4L);
        comprobante.setTipoDocumentoCliente(TipoDocumentoCliente.RUC);
        when(comprobanteService.findByIdAndEmpresaId(4L, 7L)).thenReturn(Optional.of(comprobante));

        var result = comprobanteFacade.findByIdAndEmpresaId(4L, 7L);

        assertTrue(result.isPresent());

        // Casteamos correctamente a BoletaDto
        var boletaDto = (BoletaDto) result.get();
        assertEquals(TipoDocumentoCliente.RUC, boletaDto.getTipoDocumentoCliente());
    }

    @Test
    void testCreateFactura() {
        // Mock del request
        var request = new FacturaRequest();
        request.setEmpresaId(10L);
        request.setTipoPago(TipoPago.AL_CONTADO);
        request.setNroDocCliente("12345678");
        request.setNombreCliente("Cliente 1");
        request.setDireccionCliente("Lima");
        request.setSerie("F001");
        request.setFechaEmision(LocalDateTime.now());

        // Mock detalle
        var detalleReq = new DetalleComprobanteRequest();
        detalleReq.setProductoId(1L);
        detalleReq.setCantidad(2);
        request.setDetalles(List.of(detalleReq));

        // Mock producto
        var producto = new Producto();
        producto.setValorUnitario(BigDecimal.valueOf(100));
        producto.setIgv(BigDecimal.valueOf(18));
        when(productoService.findById(1L)).thenReturn(Optional.of(producto));

        // Mock correlativo
        when(comprobanteService.getNextCorrelativo(10L, "F001")).thenReturn(1);

        // Mock guardado
        var facturaGuardada = new Factura();
        facturaGuardada.setId(99L);
        facturaGuardada.setTipoPago(TipoPago.AL_CONTADO);
        facturaGuardada.setSubtotal(BigDecimal.valueOf(200));
        facturaGuardada.setIgvTotal(BigDecimal.valueOf(36));
        facturaGuardada.setTotal(BigDecimal.valueOf(236));
        when(comprobanteService.save(any())).thenReturn(facturaGuardada);

        var result = comprobanteFacade.create(request);
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(236), result.getTotal());
    }

    @Test
    void testCreateBoleta() {
        var request = new BoletaRequest();
        request.setEmpresaId(20L);
        request.setTipoDocumentoCliente(TipoDocumentoCliente.DNI);
        request.setNroDocCliente("98765432");
        request.setNombreCliente("Juan Perez");
        request.setDireccionCliente("Cusco");
        request.setSerie("B001");

        var detalleReq = new DetalleComprobanteRequest();
        detalleReq.setProductoId(2L);
        detalleReq.setCantidad(1);
        request.setDetalles(List.of(detalleReq));

        var producto = new Producto();
        producto.setValorUnitario(BigDecimal.valueOf(50));
        producto.setIgv(BigDecimal.valueOf(18));
        when(productoService.findById(2L)).thenReturn(Optional.of(producto));
        when(comprobanteService.getNextCorrelativo(20L, "B001")).thenReturn(5);
        when(comprobanteService.save(any())).thenReturn(new Boleta());

        var result = comprobanteFacade.create(request);
        assertNotNull(result);
    }

    @Test
    void testCreateTipoNoValido() {
        var request = mock(ComprobanteRequest.class);
        assertThrows(IllegalArgumentException.class, () -> comprobanteFacade.create(request));
    }

    @Test
    void testDeleteById() {
        doNothing().when(comprobanteService).deleteById(1L);
        comprobanteFacade.deleteById(1L);
        verify(comprobanteService, times(1)).deleteById(1L);
    }

}