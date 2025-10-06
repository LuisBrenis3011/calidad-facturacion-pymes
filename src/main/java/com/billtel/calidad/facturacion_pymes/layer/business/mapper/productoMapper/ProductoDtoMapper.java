package com.billtel.calidad.facturacion_pymes.layer.business.mapper.productoMapper;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.ProductoDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductoDtoMapper {
    ProductoDto toDto(Empresa domain);
}
