package com.billtel.calidad.facturacion_pymes.DetalleComprobante.repositories;

import com.billtel.calidad.facturacion_pymes.DetalleComprobante.entities.DetalleComprobante;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleComprobanteRepository extends CrudRepository<DetalleComprobante, Long> {
}