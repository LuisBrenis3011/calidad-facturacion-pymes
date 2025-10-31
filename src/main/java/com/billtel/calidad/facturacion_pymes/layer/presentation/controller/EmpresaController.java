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
@CrossOrigin(originPatterns = "*")
public class EmpresaController {

    private final IEmpresaFacade iEmpresaFacade;

    @GetMapping
    public ResponseEntity<List<EmpresaDto>> list(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

//        // Si es admin, retorna todas
//        if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
//            return ResponseEntity.ok(iEmpresaFacade.findAll());
//        }

        return ResponseEntity.ok(iEmpresaFacade.findByUsername(userDetails.getUsername()));
    }

    @PostMapping
    public ResponseEntity<EmpresaDto> create(@RequestBody EmpresaRequest request,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        request.setUsername(userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(iEmpresaFacade.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDto> update(@PathVariable Long id,
                                             @RequestBody EmpresaRequest request,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        Optional<EmpresaDto> updated = iEmpresaFacade.update(request, id);
        if (updated.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(updated.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        iEmpresaFacade.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

