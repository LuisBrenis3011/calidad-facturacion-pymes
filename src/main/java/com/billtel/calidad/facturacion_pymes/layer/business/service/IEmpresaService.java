package com.billtel.calidad.facturacion_pymes.layer.business.service;

import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;

import java.util.List;
import java.util.Optional;

public interface IEmpresaService {
    List<Empresa> findAll();
    List<Empresa> findByUsuarioId(Long usuarioId);
    Optional<Empresa> findById(Long id);
    Optional<Empresa> findByIdAndUsuarioId(Long id, Long usuarioId);
    Empresa save(Empresa empresa);
    void deleteById(Long id);
    void deleteByIdAndUsuarioId(Long id, Long usuarioId);
}
