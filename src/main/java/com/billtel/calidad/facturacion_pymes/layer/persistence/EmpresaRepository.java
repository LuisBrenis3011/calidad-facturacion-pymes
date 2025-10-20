package com.billtel.calidad.facturacion_pymes.layer.persistence;

import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EmpresaRepository extends CrudRepository<Empresa, Long> {
    List<Empresa> findByUsuarioId(Long usuarioId);
    Optional<Empresa> findByIdAndUsuarioId(Long id, Long usuarioId);
}
