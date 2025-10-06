package com.billtel.calidad.facturacion_pymes.layer.business.mapper.empresaMapper;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.EmpresaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmpresaRequestMapper {
    Empresa ToDomain(EmpresaRequest request);
}
