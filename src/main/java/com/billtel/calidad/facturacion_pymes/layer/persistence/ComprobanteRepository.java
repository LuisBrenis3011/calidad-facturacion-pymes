package com.billtel.calidad.facturacion_pymes.layer.persistence;

import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.Comprobante;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComprobanteRepository extends CrudRepository<Comprobante, Long> {
    List<Comprobante> findByEmpresaId(Long empresaId);
    Optional<Comprobante> findByIdAndEmpresaId(Long id, Long empresaId);
    Optional<Comprobante> findTopByEmpresaIdAndSerieOrderByCorrelativoDesc(Long empresaId, String serie);
}
