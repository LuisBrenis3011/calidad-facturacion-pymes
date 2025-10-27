package com.billtel.calidad.facturacion_pymes.layer.business.service;

import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.Comprobante;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.DetalleComprobante;

import java.util.List;
import java.util.Optional;

public interface IComprobanteService {
    List<Comprobante> findAll();
    List<Comprobante> findByEmpresaId(Long empresaId);
    Optional<Comprobante> findById(Long id);
    Optional<Comprobante> findByIdAndEmpresaId(Long id, Long empresaId);
    Comprobante save(Comprobante comprobante);
    void deleteById(Long id);
    Integer getNextCorrelativo(Long empresaId, String serie);

    Comprobante createComprobante(Comprobante comprobanteBase, Long empresaId, List<DetalleComprobante> detalles);
}
