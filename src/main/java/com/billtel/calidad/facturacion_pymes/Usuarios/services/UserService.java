package com.billtel.calidad.facturacion_pymes.Usuarios.services;

import com.billtel.calidad.facturacion_pymes.Usuarios.entities.Rol;
import com.billtel.calidad.facturacion_pymes.Usuarios.entities.Usuario;
import com.billtel.calidad.facturacion_pymes.Usuarios.repositories.UserRepository;
import com.billtel.calidad.facturacion_pymes.Usuarios.repositories.RolRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private UserRepository userRepository;

    private RolRepository rolRepository;

    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
        user.setEnabled(true);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public Optional<Usuario> update(Usuario user, Long id){
        Optional<Usuario> userOptional = this.findById(id);
        if(userOptional.isPresent()){
            Usuario userDB = userOptional.get();
            userDB.setUsername(user.getUsername());
            userDB.setRoles(getRoles(user));
            if(user.isEnabled() == null){
                userDB.setEnabled(true);
            }else{
                userDB.setEnabled(user.isEnabled());
            }
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
