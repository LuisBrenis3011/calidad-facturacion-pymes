package com.billtel.calidad.facturacion_pymes.layer.business.mapper.productoMapper;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.ProductoRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Producto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductoRequestMapper {
    Producto toDomain(ProductoRequest request);
}
