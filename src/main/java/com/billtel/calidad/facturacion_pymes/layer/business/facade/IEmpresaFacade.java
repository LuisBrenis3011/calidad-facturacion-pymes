package com.billtel.calidad.facturacion_pymes.layer.business.facade;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.EmpresaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.EmpresaDto;

import java.util.List;
import java.util.Optional;

public interface IEmpresaFacade {
    List<EmpresaDto> findAll();
    Optional<EmpresaDto> findById(Long id);
    EmpresaDto create(EmpresaRequest request);
    void deleteById(Long id);
}
