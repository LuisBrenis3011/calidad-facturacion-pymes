package com.billtel.calidad.facturacion_pymes.layer.business.mapper.empresaMapper;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.simplificados.EmpresaSimpleDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmpresaSimpleDtoMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "ruc", target = "ruc")
    @Mapping(source = "razonSocial", target = "razonSocial")
    EmpresaSimpleDto toDto(Empresa domain);
}
