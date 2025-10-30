package com.billtel.calidad.facturacion_pymes.layer.business.mapper.usuarioMapper;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.UsuarioDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.users.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



@Mapper(componentModel = "spring")
public interface UsuarioDtoMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "admin", target = "admin")
    UsuarioDto toDto(Usuario domain);
}

