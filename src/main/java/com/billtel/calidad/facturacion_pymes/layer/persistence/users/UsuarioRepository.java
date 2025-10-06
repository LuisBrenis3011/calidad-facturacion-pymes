package com.billtel.calidad.facturacion_pymes.layer.persistence.users;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.users.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}