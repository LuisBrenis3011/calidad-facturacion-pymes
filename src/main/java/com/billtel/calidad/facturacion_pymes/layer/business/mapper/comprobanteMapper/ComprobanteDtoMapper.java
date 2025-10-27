package com.billtel.calidad.facturacion_pymes.layer.business.mapper.comprobanteMapper;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.comprobanteResponse.ComprobanteDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.Comprobante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComprobanteDtoMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "empresa", source = "empresa")
    @Mapping(target = "nroDocCliente", source = "nroDocCliente")
    @Mapping(target = "direccionCliente", source = "direccionCliente")
    @Mapping(target = "serie", source = "serie")
    @Mapping(target = "correlativo", source = "correlativo")
    @Mapping(target = "fechaEmision", source = "fechaEmision")
    @Mapping(target = "subtotal", source = "subtotal")
    @Mapping(target = "igvTotal", source = "igvTotal")
    @Mapping(target = "total", source = "total")
    ComprobanteDto toDto(Comprobante domain);
}
