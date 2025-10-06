package com.billtel.calidad.facturacion_pymes.layer.business.mapper.comprobanteMapper;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobanteRequest.ComprobanteRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.Comprobante;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ComprobanteRequestMapper {
    Comprobante ToDomain(ComprobanteRequest request);
}
