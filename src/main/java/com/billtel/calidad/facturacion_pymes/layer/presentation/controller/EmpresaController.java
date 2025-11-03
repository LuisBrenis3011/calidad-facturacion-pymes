package com.billtel.calidad.facturacion_pymes.layer.presentation.controller;

import com.billtel.calidad.facturacion_pymes.layer.business.facade.IEmpresaFacade;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.EmpresaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.EmpresaDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/empresa")
@CrossOrigin(originPatterns = "*")// NOSONAR
public class EmpresaController {

    private final IEmpresaFacade iEmpresaFacade;

    @GetMapping
    public ResponseEntity<List<EmpresaDto>> list(@AuthenticationPrincipal UserDetails userDetails) {
        return Optional.ofNullable(userDetails)
                .map(user -> ResponseEntity.ok(iEmpresaFacade.findByUsername(user.getUsername())))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping
    public ResponseEntity<EmpresaDto> create(@RequestBody EmpresaRequest request,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        return Optional.ofNullable(userDetails)
                .map(user -> {
                    request.setUsername(user.getUsername());
                    return ResponseEntity.status(HttpStatus.CREATED).body(iEmpresaFacade.create(request));
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDto> update(@PathVariable Long id,
                                             @RequestBody EmpresaRequest request,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        return Optional.ofNullable(userDetails)
                .map(user -> toResponseEntityWithStatus(iEmpresaFacade.update(request, id)))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        return Optional.ofNullable(userDetails)
                .map(user -> {
                    iEmpresaFacade.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    private ResponseEntity<EmpresaDto> toResponseEntityWithStatus(Optional<EmpresaDto> optional) {
        return optional
                .map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto))
                .orElse(ResponseEntity.notFound().build());
    }
}

