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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/usuario")
@CrossOrigin(originPatterns = "*")// NOSONAR
public class UsuarioController {

    private final IUsuarioFacade iUserFacade;

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> getAllUsers() {
        return ResponseEntity.ok(iUserFacade.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> getUserById(@PathVariable Long id) {
        return toResponseEntity(iUserFacade.findById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UsuarioDto> getUserByUsername(@PathVariable String username) {
        return toResponseEntity(iUserFacade.findByUsername(username));
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody UsuarioCreateRequest user, BindingResult result) {
        return result.hasErrors()
                ? buildValidationErrors(result)
                : ResponseEntity.status(HttpStatus.CREATED).body(iUserFacade.create(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UsuarioRequest user,
                                             BindingResult result,
                                             @PathVariable Long id) {
        return result.hasErrors()
                ? buildValidationErrors(result)
                : toResponseEntityWithStatus(iUserFacade.update(user, id), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        return iUserFacade.findById(id)
                .map(dto -> {
                    iUserFacade.delete(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private ResponseEntity<UsuarioDto> toResponseEntity(Optional<UsuarioDto> optional) {
        return optional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private ResponseEntity<Object> toResponseEntityWithStatus(Optional<UsuarioDto> optional, HttpStatus status) {
        return optional
                .<ResponseEntity<Object>>map(dto -> ResponseEntity.status(status).body(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    private ResponseEntity<Object> buildValidationErrors(BindingResult result) {
        Map<String, String> errors = result.getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        err -> "El campo " + err.getField() + " " + err.getDefaultMessage()
                ));
        return ResponseEntity.badRequest().body(errors);
    }
}