package com.billtel.calidad.facturacion_pymes.DetalleComprobante.dto;

import com.billtel.calidad.facturacion_pymes.Comprobantes.entities.Comprobante;
import com.billtel.calidad.facturacion_pymes.DetalleComprobante.entities.DetalleComprobante;
import com.billtel.calidad.facturacion_pymes.Productos.entities.Producto;

public class DetalleComprobanteMapper {

    public static DetalleComprobanteDTO toDTO(DetalleComprobante entity) {
        DetalleComprobanteDTO dto = new DetalleComprobanteDTO();
        dto.setId(entity.getId());
        dto.setComprobanteId(entity.getComprobante() != null ? entity.getComprobante().getId() : null);
        dto.setProductoId(entity.getProducto() != null ? entity.getProducto().getId() : null);
        dto.setCantidad(entity.getCantidad());
        dto.setPrecioUnitario(entity.getPrecioUnitario());
        dto.setSubtotal(entity.getSubtotal());
        return dto;
    }

    public static DetalleComprobante toEntity(DetalleComprobanteDTO dto) {
        DetalleComprobante entity = new DetalleComprobante();
        entity.setId(dto.getId());

        if (dto.getComprobanteId() != null) {
            Comprobante c = new Comprobante();
            c.setId(dto.getComprobanteId());
            entity.setComprobante(c);
        }

        if (dto.getProductoId() != null) {
            Producto p = new Producto();
            p.setId(dto.getProductoId());
            entity.setProducto(p);
        }

        entity.setCantidad(dto.getCantidad());
        entity.setPrecioUnitario(dto.getPrecioUnitario());
        entity.setSubtotal(dto.getSubtotal());
        return entity;
    }
}