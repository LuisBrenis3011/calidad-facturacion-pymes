package com.billtel.calidad.facturacion_pymes.layer.business.mapper.usuarioMapper;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.UsuarioDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.users.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioDtoMapper {
    UsuarioDto toDto(Usuario domain);
}
