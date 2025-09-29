package com.billtel.calidad.facturacion_pymes.DetalleComprobante.services;

import com.billtel.calidad.facturacion_pymes.DetalleComprobante.entities.DetalleComprobante;

import java.util.List;
import java.util.Optional;

public interface DetalleComprobanteService {
    List<DetalleComprobante> findAll();

    Optional<DetalleComprobante> findById(Long id);

    DetalleComprobante save(DetalleComprobante detalle);

    void deleteById(Long id);
}
