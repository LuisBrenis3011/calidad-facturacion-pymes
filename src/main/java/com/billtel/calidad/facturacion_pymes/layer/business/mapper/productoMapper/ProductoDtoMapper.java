package com.billtel.calidad.facturacion_pymes.layer.business.mapper.productoMapper;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.ProductoDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductoDtoMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "descripcion", target = "descripcion")
    @Mapping(source = "valorUnitario", target = "valorUnitario")
    @Mapping(source = "unidadMedida", target = "unidadMedida")
    ProductoDto toDto(Producto domain);
}
