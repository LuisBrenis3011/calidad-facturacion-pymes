package com.billtel.calidad.facturacion_pymes.Usuarios.repositories;

import com.billtel.calidad.facturacion_pymes.Usuarios.entities.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}
