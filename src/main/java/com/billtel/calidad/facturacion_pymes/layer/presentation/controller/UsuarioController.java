package com.billtel.calidad.facturacion_pymes.layer.presentation.controller;


import com.billtel.calidad.facturacion_pymes.layer.business.facade.IUsuarioFacade;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.UsuarioCreateRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.UsuarioRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.UsuarioDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/usuario")
@CrossOrigin(originPatterns = "*")
public class UsuarioController {

    private final IUsuarioFacade iUserFacade;

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> getAllUsers() {
        return ResponseEntity.ok(iUserFacade.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> getUserById(@PathVariable Long id) {
        Optional<UsuarioDto> usuarioOpt = iUserFacade.findById(id);
        if (usuarioOpt.isPresent()) {
            return ResponseEntity.ok(usuarioOpt.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UsuarioDto> getUserByUsername(@PathVariable String username) {
        Optional<UsuarioDto> usuarioOpt = iUserFacade.findByUsername(username);
        if (usuarioOpt.isPresent()) {
            return ResponseEntity.ok(usuarioOpt.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UsuarioCreateRequest user, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(iUserFacade.create(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UsuarioRequest user,
                                        BindingResult result,
                                        @PathVariable Long id) {
        if (result.hasErrors()) {
            return validation(result);
        }

        Optional<UsuarioDto> updated = iUserFacade.update(user, id);
        if (updated.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(updated.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<UsuarioDto> usuarioOpt = iUserFacade.findById(id);
        if (usuarioOpt.isPresent()) {
            iUserFacade.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err ->
                errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }
}