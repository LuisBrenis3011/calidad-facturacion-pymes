package com.billtel.calidad.facturacion_pymes.layer.business.service;

import com.billtel.calidad.facturacion_pymes.layer.domain.entity.users.Usuario;

import java.util.Optional;

public interface IUsuarioService {
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByUsername(String username);
    Iterable<Usuario> findAll();
    Usuario save(Usuario user);
    Optional<Usuario> update(Usuario user, Long id);
    void delete(Long id);

}
