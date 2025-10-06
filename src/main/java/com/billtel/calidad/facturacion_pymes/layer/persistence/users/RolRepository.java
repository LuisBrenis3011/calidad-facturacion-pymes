package com.billtel.calidad.facturacion_pymes.layer.persistence.users;

import com.billtel.calidad.facturacion_pymes.layer.domain.entity.users.Rol;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends CrudRepository<Rol,Long> {
    Optional<Rol> findByName(String name);
}
