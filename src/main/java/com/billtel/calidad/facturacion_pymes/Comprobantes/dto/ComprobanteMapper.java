package com.billtel.calidad.facturacion_pymes.Comprobantes.dto;

import com.billtel.calidad.facturacion_pymes.Comprobantes.entities.Comprobante;
import com.billtel.calidad.facturacion_pymes.Empresas.entities.Empresa;
import com.billtel.calidad.facturacion_pymes.DetalleComprobante.dto.DetalleComprobanteMapper;

import java.util.stream.Collectors;

public class ComprobanteMapper {

    public static ComprobanteDTO toDTO(Comprobante comprobante) {
        ComprobanteDTO dto = new ComprobanteDTO();
        dto.setId(comprobante.getId());
        dto.setEmpresaId(comprobante.getEmpresa() != null ? comprobante.getEmpresa().getId() : null);
        dto.setNroDocCliente(comprobante.getNroDocCliente());
        dto.setDireccionCliente(comprobante.getDireccionCliente());
        dto.setSerie(comprobante.getSerie());
        dto.setCorrelativo(comprobante.getCorrelativo());
        dto.setFechaEmision(comprobante.getFechaEmision());
        dto.setSubtotal(comprobante.getSubtotal());
        dto.setIgvTotal(comprobante.getIgvTotal());
        dto.setTotal(comprobante.getTotal());
        dto.setEstadoSunat(comprobante.getEstadoSunat().name());

        if (comprobante.getDetalles() != null) {
            dto.setDetalles(comprobante.getDetalles().stream()
                    .map(DetalleComprobanteMapper::toDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public static Comprobante toEntity(ComprobanteDTO dto) {
        Comprobante comprobante = new Comprobante();
        comprobante.setId(dto.getId());

        if (dto.getEmpresaId() != null) {
            Empresa empresa = new Empresa();
            empresa.setId(dto.getEmpresaId());
            comprobante.setEmpresa(empresa);
        }

        comprobante.setNroDocCliente(dto.getNroDocCliente());
        comprobante.setDireccionCliente(dto.getDireccionCliente());
        comprobante.setSerie(dto.getSerie());
        comprobante.setCorrelativo(dto.getCorrelativo());
        comprobante.setFechaEmision(dto.getFechaEmision());
        comprobante.setSubtotal(dto.getSubtotal());
        comprobante.setIgvTotal(dto.getIgvTotal());
        comprobante.setTotal(dto.getTotal());

        if (dto.getEstadoSunat() != null) {
            comprobante.setEstadoSunat(Comprobante.EstadoSunat.valueOf(dto.getEstadoSunat()));
        }

        if (dto.getDetalles() != null) {
            comprobante.setDetalles(dto.getDetalles().stream()
                    .map(DetalleComprobanteMapper::toEntity)
                    .collect(Collectors.toList()));
        }

        return comprobante;
    }
}