package com.billtel.calidad.facturacion_pymes.Productos.dto;

import com.billtel.calidad.facturacion_pymes.Empresas.entities.Empresa;
import com.billtel.calidad.facturacion_pymes.Productos.entities.Producto;

public class ProductoMapper {

    public static ProductoDTO toDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setEmpresaId(producto.getEmpresa() != null ? producto.getEmpresa().getId() : null);
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setValorUnitario(producto.getValorUnitario());
        dto.setIgv(producto.getIgv());
        dto.setUnidadMedida(producto.getUnidadMedida());
        return dto;
    }

    public static Producto toEntity(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setId(dto.getId());

        if (dto.getEmpresaId() != null) {
            Empresa empresa = new Empresa();
            empresa.setId(dto.getEmpresaId());
            producto.setEmpresa(empresa);
        }

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setValorUnitario(dto.getValorUnitario());
        producto.setIgv(dto.getIgv());
        producto.setUnidadMedida(dto.getUnidadMedida());
        return producto;
    }
}

