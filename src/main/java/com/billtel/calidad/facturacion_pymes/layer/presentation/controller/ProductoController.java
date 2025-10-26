package com.billtel.calidad.facturacion_pymes.layer.presentation.controller;
import com.billtel.calidad.facturacion_pymes.layer.business.facade.IProductoFacade;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.ProductoRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.ProductoDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/producto")
@AllArgsConstructor
public class ProductoController {

    private final IProductoFacade productoFacade;

    // Listar todos los productos de una empresa específica
    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<?> listByEmpresa(@PathVariable Long empresaId) {
        return ResponseEntity.ok(productoFacade.findByEmpresaId(empresaId));
    }

    // Listar todos los productos (admin)
    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(productoFacade.findAll());
    }

    // Obtener un producto específico de una empresa
    @GetMapping("/{id}/empresa/{empresaId}")
    public ResponseEntity<?> detailsByEmpresa(@PathVariable Long id, @PathVariable Long empresaId) {
        Optional<ProductoDto> producto = productoFacade.findByIdAndEmpresaId(id, empresaId);
        if (producto.isPresent()) {
            return ResponseEntity.ok(producto.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {
        Optional<ProductoDto> producto = productoFacade.findById(id);
        if (producto.isPresent()) {
            return ResponseEntity.ok(producto.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoFacade.create(request));
    }

    @PutMapping("/{id}/empresa/{empresaId}")
    public ResponseEntity<?> updateByEmpresa(@PathVariable Long id, @PathVariable Long empresaId,
                                             @RequestBody ProductoRequest request) {
        Optional<ProductoDto> productoOptional = productoFacade.findByIdAndEmpresaId(id, empresaId);
        if (productoOptional.isPresent()) {
            request.setEmpresaId(empresaId);
            return ResponseEntity.ok(productoFacade.create(request));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductoRequest request) {
        Optional<ProductoDto> productoOptional = productoFacade.findById(id);
        if (productoOptional.isPresent()) {
            return ResponseEntity.ok(productoFacade.create(request));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/empresa/{empresaId}")
    public ResponseEntity<?> deleteByEmpresa(@PathVariable Long id, @PathVariable Long empresaId) {
        Optional<ProductoDto> productoOptional = productoFacade.findByIdAndEmpresaId(id, empresaId);
        if (productoOptional.isPresent()) {
            productoFacade.deleteByIdAndEmpresaId(id, empresaId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<ProductoDto> productoOptional = productoFacade.findById(id);
        if (productoOptional.isPresent()) {
            productoFacade.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
