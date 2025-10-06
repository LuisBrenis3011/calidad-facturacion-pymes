package com.billtel.calidad.facturacion_pymes.layer.business.service;

import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;

import java.util.List;
import java.util.Optional;

public interface IEmpresaService {
    List<Empresa> findAll();
    Optional<Empresa> findById(Long id);
    Empresa save(Empresa empresa);
    void deleteById(Long id);
}
