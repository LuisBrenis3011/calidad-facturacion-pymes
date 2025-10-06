package com.billtel.calidad.facturacion_pymes.layer.persistence;

import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.Comprobante;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComprobanteRepository extends CrudRepository<Comprobante, Long> {
}
