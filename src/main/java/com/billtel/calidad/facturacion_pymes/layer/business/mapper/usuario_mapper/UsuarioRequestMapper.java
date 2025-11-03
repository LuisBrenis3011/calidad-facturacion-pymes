package com.billtel.calidad.facturacion_pymes.layer.business.mapper.usuario_mapper;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.UsuarioRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.users.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UsuarioRequestMapper {

    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "admin", target = "admin")
    Usuario toDomain(UsuarioRequest request);
}
