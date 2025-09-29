package com.billtel.calidad.facturacion_pymes.Comprobantes.repositories;

import com.billtel.calidad.facturacion_pymes.Comprobantes.entities.Comprobante;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComprobanteRepository extends CrudRepository<Comprobante, Long> {
}
