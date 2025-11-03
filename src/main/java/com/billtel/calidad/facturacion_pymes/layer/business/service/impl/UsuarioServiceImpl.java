package com.billtel.calidad.facturacion_pymes.layer.business.service.impl;


import com.billtel.calidad.facturacion_pymes.layer.business.service.IUsuarioService;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.users.Rol;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.users.Usuario;
import com.billtel.calidad.facturacion_pymes.layer.persistence.users.RolRepository;
import com.billtel.calidad.facturacion_pymes.layer.persistence.users.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {
    private final UsuarioRepository userRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Usuario> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Usuario save(Usuario user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(getRoles(user));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public Optional<Usuario> update(Usuario user, Long id){
        Optional<Usuario> userOptional =  userRepository.findById(id);
        if(userOptional.isPresent()){
            Usuario userDB = userOptional.orElseThrow();
            userDB.setUsername(user.getUsername());
            userDB.setRoles(getRoles(user));
            userDB.setEmail(user.getEmail());
            return Optional.of(userRepository.save(userDB));
        }
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public List<Rol> getRoles(Usuario user) {
        List<Rol> roles = new ArrayList<>();
        Optional<Rol> rol = rolRepository.findByName("ROLE_USER");
        rol.ifPresent(roles::add);
        if (user.isAdmin()) {
            Optional<Rol> rolAdmin = rolRepository.findByName("ROLE_ADMIN");
            rolAdmin.ifPresent(roles::add);
        }
        return roles;
    }
}
