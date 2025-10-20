package com.billtel.calidad.facturacion_pymes.layer.business.mapper.empresaMapper;

import com.billtel.calidad.facturacion_pymes.layer.business.mapper.productoMapper.ProductoSimpleDtoMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.mapper.usuarioMapper.UsuarioSimpleDtoMapper;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.EmpresaDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UsuarioSimpleDtoMapper.class, ProductoSimpleDtoMapper.class})
public interface EmpresaDtoMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "usuario", target = "usuario")
    @Mapping(source = "productos", target = "productos")
    EmpresaDto toDto(Empresa domain);
}
