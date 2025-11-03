package com.billtel.calidad.facturacion_pymes.layer.business.mapper.empresa_mapper;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.EmpresaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmpresaRequestMapper {

    @Mapping(source = "ruc", target = "ruc")
    @Mapping(source = "razonSocial", target = "razonSocial")
    @Mapping(source = "direccion", target = "direccion")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    Empresa toDomain(EmpresaRequest request);

}

