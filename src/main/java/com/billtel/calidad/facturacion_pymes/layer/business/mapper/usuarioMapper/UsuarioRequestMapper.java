package com.billtel.calidad.facturacion_pymes.layer.business.mapper.usuarioMapper;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.UsuarioRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.users.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UsuarioRequestMapper {

    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "enabled", target = "enabled")
    @Mapping(source = "roles", target = "roles")
    Usuario toDomain(UsuarioRequest request);
}
