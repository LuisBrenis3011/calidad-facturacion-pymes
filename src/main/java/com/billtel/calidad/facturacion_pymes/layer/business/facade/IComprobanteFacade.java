package com.billtel.calidad.facturacion_pymes.layer.business.facade;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobanteRequest.ComprobanteRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.comprobanteResponse.ComprobanteDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.Comprobante;

import java.util.List;
import java.util.Optional;

public interface IComprobanteFacade {
    List<ComprobanteDto> findAll();
    Optional<ComprobanteDto> findById(Long id);
    ComprobanteDto create(ComprobanteRequest request);
    void deleteById(Long id);
}
