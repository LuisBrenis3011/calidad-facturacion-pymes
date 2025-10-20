package com.billtel.calidad.facturacion_pymes.layer.persistence;

import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Producto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends CrudRepository<Producto, Long> {
    List<Producto> findByEmpresaId(Long empresaId);
    Optional<Producto> findByIdAndEmpresaId(Long id, Long empresaId);
}
