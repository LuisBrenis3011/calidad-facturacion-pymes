package com.billtel.calidad.facturacion_pymes.Comprobantes.services;

import com.billtel.calidad.facturacion_pymes.Comprobantes.entities.Comprobante;

import java.util.List;
import java.util.Optional;

public interface ComprobanteService {
    List<Comprobante> findAll();

    Optional<Comprobante> findById(Long id);

    Comprobante save(Comprobante comprobante);

    void deleteById(Long id);
}
