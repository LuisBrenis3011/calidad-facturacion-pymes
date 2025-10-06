package com.billtel.calidad.facturacion_pymes.layer.business.service;

import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.Comprobante;

import java.util.List;
import java.util.Optional;

public interface IComprobanteService {
    List<Comprobante> findAll();
    Optional<Comprobante> findById(Long id);
    Comprobante save(Comprobante comprobante);
    void deleteById(Long id);
}
