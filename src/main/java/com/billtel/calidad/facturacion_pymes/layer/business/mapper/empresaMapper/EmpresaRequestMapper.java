package com.billtel.calidad.facturacion_pymes.layer.business.mapper.empresaMapper;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.EmpresaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.users.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmpresaRequestMapper {

    @Mapping(source = "usuarioId", target = "usuario.id")
    Empresa toDomain(EmpresaRequest request);

    default Usuario map(Long usuarioId) {
        if (usuarioId == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return usuario;
    }
}
