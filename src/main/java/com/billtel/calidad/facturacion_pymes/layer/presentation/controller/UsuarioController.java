package com.billtel.calidad.facturacion_pymes.layer.presentation.controller;


import com.billtel.calidad.facturacion_pymes.layer.business.facade.IUsuarioFacade;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.UsuarioRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.UsuarioDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.users.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
public class UsuarioController {

    private IUsuarioFacade iUserFacade;

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(this.iUserFacade.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> getUserById(@PathVariable Long id){
        return this.iUserFacade.findById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UsuarioDto> getUserByUsername(@PathVariable String username){
        return this.iUserFacade.findByUsername(username).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> createUser(@RequestBody UsuarioRequest user){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.iUserFacade.create(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UsuarioRequest user, @PathVariable Long id){
        return this.iUserFacade.update(user, id)
                .map(userUpdate -> ResponseEntity.status(HttpStatus.CREATED).body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        this.iUserFacade.delete(id);
        return ResponseEntity.noContent().build();
    }
}
