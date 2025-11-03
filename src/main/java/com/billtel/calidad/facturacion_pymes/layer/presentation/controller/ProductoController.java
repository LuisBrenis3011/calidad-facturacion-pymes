package com.billtel.calidad.facturacion_pymes.layer.presentation.controller;
import com.billtel.calidad.facturacion_pymes.layer.business.facade.IProductoFacade;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.ProductoRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.ProductoDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producto")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*")// NOSONAR
public class ProductoController {

    private final IProductoFacade productoFacade;

    @GetMapping
    public ResponseEntity<List<ProductoDto>> list() {
        return ResponseEntity.ok(productoFacade.findAll());
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<ProductoDto>> listByEmpresa(@PathVariable Long empresaId) {
        return ResponseEntity.ok(productoFacade.findByEmpresaId(empresaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDto> getById(@PathVariable Long id) {
        return toResponseEntity(productoFacade.findById(id));
    }

    @GetMapping("/{id}/empresa/{empresaId}")
    public ResponseEntity<ProductoDto> getByIdAndEmpresa(@PathVariable Long id, @PathVariable Long empresaId) {
        return toResponseEntity(productoFacade.findByIdAndEmpresaId(id, empresaId));
    }

    @PostMapping("/empresa/{empresaId}")
    public ResponseEntity<ProductoDto> create(
            @PathVariable Long empresaId,
            @RequestBody ProductoRequest request
    ) {
        request.setEmpresaId(empresaId);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoFacade.create(request));
    }

    @PutMapping("/{id}/empresa/{empresaId}")
    public ResponseEntity<ProductoDto> update(
            @PathVariable Long id,
            @PathVariable Long empresaId,
            @RequestBody ProductoRequest request
    ) {
        request.setEmpresaId(empresaId);
        return toResponseEntity(productoFacade.update(id, request));
    }

    @DeleteMapping("/{id}/empresa/{empresaId}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @PathVariable Long empresaId) {
        return productoFacade.deleteByIdAndEmpresaId(id, empresaId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    private ResponseEntity<ProductoDto> toResponseEntity(Optional<ProductoDto> optional) {
        return optional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

