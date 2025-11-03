package com.billtel.calidad.facturacion_pymes.layer.business.facade.impl;

import com.billtel.calidad.facturacion_pymes.layer.business.mapper.comprobante_mapper.ComprobanteDtoMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.mapper.comprobante_mapper.ComprobanteRequestMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.service.IComprobanteService;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobante_request.ComprobanteRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobante_request.DetalleComprobanteRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.comprobante_response.ComprobanteDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.Comprobante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComprobanteFacadeTest {

    @Mock
    private IComprobanteService comprobanteService;

    @Mock
    private ComprobanteRequestMapper comprobanteRequestMapper;

    @Mock
    private ComprobanteDtoMapper comprobanteDtoMapper;

    @InjectMocks
    private ComprobanteFacade comprobanteFacade;

    private Comprobante comprobante;
    private ComprobanteDto comprobanteDto;
    private ComprobanteRequest comprobanteRequest;

    @BeforeEach
    void setUp() {
        comprobante = new Comprobante();
        comprobante.setId(1L);
        comprobante.setSerie("F001");
        comprobante.setCorrelativo(1);
        comprobante.setFechaEmision(LocalDateTime.now());
        comprobante.setSubtotal(new BigDecimal("100.00"));
        comprobante.setIgvTotal(new BigDecimal("18.00"));
        comprobante.setTotal(new BigDecimal("118.00"));

        comprobanteDto = new ComprobanteDto();
        comprobanteDto.setId(1L);
        comprobanteDto.setSerie("F001");
        comprobanteDto.setTotal(new BigDecimal("118.00"));

        DetalleComprobanteRequest detalleReq = new DetalleComprobanteRequest();
        detalleReq.setProductoId(1L);
        detalleReq.setCantidad(2);

        comprobanteRequest = new ComprobanteRequest();
        comprobanteRequest.setEmpresaId(10L);
        comprobanteRequest.setSerie("F001");
        comprobanteRequest.setDetalles(List.of(detalleReq));
    }

    @Test
    void findAll_debeRetornarListaDeComprobantes() {
        when(comprobanteService.findAll()).thenReturn(List.of(comprobante));
        when(comprobanteDtoMapper.toDto(comprobante)).thenReturn(comprobanteDto);

        var resultado = comprobanteFacade.findAll();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getSerie()).isEqualTo("F001");
        verify(comprobanteService).findAll();
        verify(comprobanteDtoMapper).toDto(comprobante);
    }

    @Test
    void findByEmpresaId_debeRetornarComprobantesDeEmpresa() {
        when(comprobanteService.findByEmpresaId(10L)).thenReturn(List.of(comprobante));
        when(comprobanteDtoMapper.toDto(comprobante)).thenReturn(comprobanteDto);

        var resultado = comprobanteFacade.findByEmpresaId(10L);

        assertThat(resultado).hasSize(1);
        verify(comprobanteService).findByEmpresaId(10L);
    }

    @Test
    void findById_debeRetornarComprobanteCuandoExiste() {
        when(comprobanteService.findById(1L)).thenReturn(Optional.of(comprobante));
        when(comprobanteDtoMapper.toDto(comprobante)).thenReturn(comprobanteDto);

        Optional<ComprobanteDto> resultado = comprobanteFacade.findById(1L);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getSerie()).isEqualTo("F001");
        verify(comprobanteService).findById(1L);
    }

    @Test
    void findByIdAndEmpresaId_debeRetornarComprobanteEspecifico() {
        when(comprobanteService.findByIdAndEmpresaId(1L, 10L)).thenReturn(Optional.of(comprobante));
        when(comprobanteDtoMapper.toDto(comprobante)).thenReturn(comprobanteDto);

        var resultado = comprobanteFacade.findByIdAndEmpresaId(1L, 10L);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(1L);
        verify(comprobanteService).findByIdAndEmpresaId(1L, 10L);
    }

    @Test
    void create_debeCrearComprobanteCorrectamente() {
        when(comprobanteRequestMapper.toDomain(comprobanteRequest)).thenReturn(comprobante);
        when(comprobanteService.createComprobante(any(), eq(10L), anyList())).thenReturn(comprobante);
        when(comprobanteDtoMapper.toDto(comprobante)).thenReturn(comprobanteDto);

        var resultado = comprobanteFacade.create(comprobanteRequest);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getSerie()).isEqualTo("F001");
        verify(comprobanteRequestMapper).toDomain(comprobanteRequest);
        verify(comprobanteService).createComprobante(any(), eq(10L), anyList());
        verify(comprobanteDtoMapper).toDto(comprobante);
    }

    @Test
    void deleteById_debeEliminarComprobante() {
        comprobanteFacade.deleteById(1L);
        verify(comprobanteService).deleteById(1L);
    }
}
