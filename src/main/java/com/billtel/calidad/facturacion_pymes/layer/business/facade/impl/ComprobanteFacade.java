package com.billtel.calidad.facturacion_pymes.layer.business.facade.impl;

import com.billtel.calidad.facturacion_pymes.layer.business.facade.IComprobanteFacade;
import com.billtel.calidad.facturacion_pymes.layer.business.service.IComprobanteService;
import com.billtel.calidad.facturacion_pymes.layer.business.service.IProductoService;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobanteRequest.BoletaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobanteRequest.ComprobanteRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobanteRequest.FacturaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.comprobanteResponse.BoletaDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.comprobanteResponse.ComprobanteDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.comprobanteResponse.FacturaDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.Boleta;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.Comprobante;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.DetalleComprobante;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.Factura;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ComprobanteFacade implements IComprobanteFacade {

    private final IComprobanteService comprobanteService;
    private final IProductoService productoService;

    @Override
    public List<ComprobanteDto> findAll() {
        var comprobantes = comprobanteService.findAll();
        return comprobantes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ComprobanteDto> findByEmpresaId(Long empresaId) {
        var comprobantes = comprobanteService.findByEmpresaId(empresaId);
        return comprobantes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ComprobanteDto> findById(Long id) {
        return comprobanteService.findById(id).map(this::convertToDto);
    }

    @Override
    public Optional<ComprobanteDto> findByIdAndEmpresaId(Long id, Long empresaId) {
        return comprobanteService.findByIdAndEmpresaId(id, empresaId).map(this::convertToDto);
    }

    @Override
    public ComprobanteDto create(ComprobanteRequest request) {
        Comprobante comprobante;

        if (request instanceof FacturaRequest facturaRequest) {
            Factura factura = new Factura();
            factura.setTipoPago(facturaRequest.getTipoPago());
            comprobante = factura;
        } else if (request instanceof BoletaRequest boletaRequest) {
            Boleta boleta = new Boleta();
            boleta.setTipoDocumentoCliente(boletaRequest.getTipoDocumentoCliente());
            comprobante = boleta;
        } else {
            throw new IllegalArgumentException("Tipo de comprobante no válido");
        }

        // Datos básicos
        Empresa empresa = new Empresa();
        empresa.setId(request.getEmpresaId());
        comprobante.setEmpresa(empresa);
        comprobante.setNroDocCliente(request.getNroDocCliente());
        comprobante.setNombreCliente(request.getNombreCliente());
        comprobante.setDireccionCliente(request.getDireccionCliente());
        comprobante.setSerie(request.getSerie());
        comprobante.setFechaEmision(request.getFechaEmision() != null ? request.getFechaEmision() : LocalDateTime.now());
        comprobante.setEstadoSunat(Comprobante.EstadoSunat.PENDIENTE);

        // Generar correlativo automático
        Integer correlativo = comprobanteService.getNextCorrelativo(request.getEmpresaId(), request.getSerie());
        comprobante.setCorrelativo(correlativo);

        // Procesar detalles y calcular totales
        List<DetalleComprobante> detalles = new ArrayList<>();
        BigDecimal subtotalTotal = BigDecimal.ZERO;
        BigDecimal igvTotalTotal = BigDecimal.ZERO;
        BigDecimal totalTotal = BigDecimal.ZERO;

        for (var detalleRequest : request.getDetalles()) {
            var producto = productoService.findById(detalleRequest.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            DetalleComprobante detalle = new DetalleComprobante();
            detalle.setComprobante(comprobante);
            detalle.setProducto(producto);
            detalle.setCantidad(detalleRequest.getCantidad());
            detalle.setPrecioUnitario(producto.getValorUnitario());

            // Calcular valores
            BigDecimal subtotal = producto.getValorUnitario().multiply(BigDecimal.valueOf(detalleRequest.getCantidad()));
            BigDecimal igv = subtotal.multiply(producto.getIgv()).divide(BigDecimal.valueOf(100));
            BigDecimal total = subtotal.add(igv);

            detalle.setSubtotal(subtotal);
            detalle.setIgv(igv);
            detalle.setTotal(total);

            subtotalTotal = subtotalTotal.add(subtotal);
            igvTotalTotal = igvTotalTotal.add(igv);
            totalTotal = totalTotal.add(total);

            detalles.add(detalle);
        }

        comprobante.setSubtotal(subtotalTotal);
        comprobante.setIgvTotal(igvTotalTotal);
        comprobante.setTotal(totalTotal);
        comprobante.setDetalles(detalles);

        var comprobanteCreado = comprobanteService.save(comprobante);
        return convertToDto(comprobanteCreado);
    }

    @Override
    public void deleteById(Long id) {
        comprobanteService.deleteById(id);
    }

    private ComprobanteDto convertToDto(Comprobante comprobante) {
        ComprobanteDto dto;

        if (comprobante instanceof Factura factura) {
            FacturaDto facturaDto = new FacturaDto();
            facturaDto.setTipoPago(factura.getTipoPago());
            dto = facturaDto;
        } else if (comprobante instanceof Boleta boleta) {
            BoletaDto boletaDto = new BoletaDto();
            boletaDto.setTipoDocumentoCliente(boleta.getTipoDocumentoCliente());
            dto = boletaDto;
        } else {
            throw new IllegalArgumentException("Tipo de comprobante desconocido");
        }

        // Mapear campos comunes (aquí deberías usar mappers)
        dto.setId(comprobante.getId());
        dto.setNroDocCliente(comprobante.getNroDocCliente());
        dto.setNombreCliente(comprobante.getNombreCliente());
        dto.setDireccionCliente(comprobante.getDireccionCliente());
        dto.setSerie(comprobante.getSerie());
        dto.setCorrelativo(comprobante.getCorrelativo());
        dto.setFechaEmision(comprobante.getFechaEmision());
        dto.setSubtotal(comprobante.getSubtotal());
        dto.setIgvTotal(comprobante.getIgvTotal());
        dto.setTotal(comprobante.getTotal());
        dto.setEstadoSunat(comprobante.getEstadoSunat());

        return dto;
    }
}