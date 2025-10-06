package com.billtel.calidad.facturacion_pymes.layer.business.mapper.empresaMapper;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.EmpresaDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmpresaDtoMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "usuario", target = "usuario")
    @Mapping(source = "ruc", target = "ruc")
    @Mapping(source = "razonSocial", target = "razonSocial")
    @Mapping(source = "direccion", target = "direccion")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "telefono", target = "telefono")
    @Mapping(source = "logo", target = "logo")
    @Mapping(source = "productos", target = "productos")
    EmpresaDto toDto(Empresa domain);
}
