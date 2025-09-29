package com.billtel.calidad.facturacion_pymes.Usuarios.repositories;

import com.billtel.calidad.facturacion_pymes.Usuarios.entities.Rol;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends CrudRepository<Rol,Long> {
    Optional<Rol> findByName(String name);
}
