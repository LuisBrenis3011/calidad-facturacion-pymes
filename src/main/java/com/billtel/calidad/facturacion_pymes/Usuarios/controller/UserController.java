package com.billtel.calidad.facturacion_pymes.Usuarios.controller;

import com.billtel.calidad.facturacion_pymes.Usuarios.entities.Usuario;
import com.billtel.calidad.facturacion_pymes.Usuarios.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UserController {

    private IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(this.userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable Long id){
        return this.userService.findById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Usuario> getUserByUsername(@PathVariable String username){
        return this.userService.findByUsername(username).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuario> createUser(@RequestBody Usuario user){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody Usuario user, @PathVariable Long id){
        return this.userService.update(user, id)
                .map(userUpdate -> ResponseEntity.status(HttpStatus.CREATED).body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
