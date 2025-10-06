package com.billtel.calidad.facturacion_pymes.layer.business.mapper.comprobanteMapper;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.comprobanteResponse.ComprobanteDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.Comprobante;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ComprobanteDtoMapper {
    ComprobanteDto toDto(Comprobante domain);
}
