package com.billtel.calidad.facturacion_pymes.layer.business.mapper.producto_mapper;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.ProductoRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProductoRequestMapper {

    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "descripcion", target = "descripcion")
    @Mapping(source = "valorUnitario", target = "valorUnitario")
    @Mapping(source = "unidadMedida", target = "unidadMedida")
    @Mapping(source = "empresaId", target = "empresa.id")
    Producto toDomain(ProductoRequest request);
}
