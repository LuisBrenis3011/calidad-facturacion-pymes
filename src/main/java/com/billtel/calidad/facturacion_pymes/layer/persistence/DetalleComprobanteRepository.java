package com.billtel.calidad.facturacion_pymes.layer.persistence;

import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.DetalleComprobante;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleComprobanteRepository extends CrudRepository<DetalleComprobante, Long> {
    List<DetalleComprobante> findByComprobanteId(Long comprobanteId);
}
