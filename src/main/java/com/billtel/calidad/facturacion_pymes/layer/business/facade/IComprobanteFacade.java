package com.billtel.calidad.facturacion_pymes.layer.business.facade;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobanteRequest.ComprobanteRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.comprobanteResponse.ComprobanteDto;
import java.util.List;
import java.util.Optional;

public interface IComprobanteFacade {
    List<ComprobanteDto> findAll();
    List<ComprobanteDto> findByEmpresaId(Long empresaId);
    Optional<ComprobanteDto> findById(Long id);
    Optional<ComprobanteDto> findByIdAndEmpresaId(Long id, Long empresaId);
    ComprobanteDto create(ComprobanteRequest request);
    void deleteById(Long id);
}
