package com.billtel.calidad.facturacion_pymes.Usuarios.services;

import com.billtel.calidad.facturacion_pymes.Usuarios.entities.Usuario;

import java.util.Optional;

public interface IUserService {

    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByUsername(String username);
    Iterable<Usuario> findAll();
    Usuario save(Usuario user);
    Optional<Usuario> update(Usuario user, Long id);
    void delete(Long id);

}
