package com.billtel.calidad.facturacion_pymes.Usuarios.repositories;

import com.billtel.calidad.facturacion_pymes.Usuarios.entities.Rol;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RolRepository extends CrudRepository<Rol,Long> {
    Optional<Rol> findByName(String name);
}
