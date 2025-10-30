package com.billtel.calidad.facturacion_pymes.layer.business.facade;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.EmpresaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.EmpresaDto;

import java.util.List;
import java.util.Optional;

public interface IEmpresaFacade {
    List<EmpresaDto> findAll();
    List<EmpresaDto> findByUsuarioId(Long usuarioId);
    Optional<EmpresaDto> findById(Long id);
    Optional<EmpresaDto> findByIdAndUsuarioId(Long id, Long usuarioId);
    EmpresaDto create(EmpresaRequest request);
    void deleteById(Long id);
    void deleteByIdAndUsuarioId(Long id, Long usuarioId);
    List<EmpresaDto> findByUsername(String username);
    Optional<EmpresaDto> update(EmpresaRequest request, Long id);
}
