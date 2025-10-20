package com.billtel.calidad.facturacion_pymes.layer.business.mapper.usuarioMapper;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.simplificados.UsuarioSimpleDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.users.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioSimpleDtoMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    UsuarioSimpleDto toDto(Usuario domain);
}
