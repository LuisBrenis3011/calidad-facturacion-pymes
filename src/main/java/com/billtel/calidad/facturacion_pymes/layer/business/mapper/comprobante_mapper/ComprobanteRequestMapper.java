package com.billtel.calidad.facturacion_pymes.layer.business.mapper.comprobante_mapper;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobante_request.ComprobanteRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.Comprobante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComprobanteRequestMapper {
    @Mapping(source = "empresaId", target = "empresa.id")
    @Mapping(target = "nroDocCliente", source = "nroDocCliente")
    @Mapping(target = "direccionCliente", source = "direccionCliente")
    @Mapping(target = "serie", source = "serie")
    @Mapping(target = "fechaEmision", source = "fechaEmision")
    Comprobante toDomain(ComprobanteRequest request);
}
