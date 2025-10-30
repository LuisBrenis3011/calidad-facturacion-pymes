package com.billtel.calidad.facturacion_pymes.layer.business.mapper.empresaMapper;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.EmpresaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.users.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

