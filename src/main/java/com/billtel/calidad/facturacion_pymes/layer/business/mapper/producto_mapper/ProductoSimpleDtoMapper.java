package com.billtel.calidad.facturacion_pymes.layer.business.mapper.producto_mapper;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.simplificados.ProductoSimpleDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductoSimpleDtoMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "valorUnitario", target = "valorUnitario")
    @Mapping(source = "unidadMedida", target = "unidadMedida")
    ProductoSimpleDto toDto(Producto domain);
}