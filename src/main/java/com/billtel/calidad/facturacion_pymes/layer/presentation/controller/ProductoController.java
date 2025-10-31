package com.billtel.calidad.facturacion_pymes.layer.presentation.controller;
import com.billtel.calidad.facturacion_pymes.layer.business.facade.IProductoFacade;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.ProductoRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.ProductoDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*")
public class ProductoController {

    private final IProductoFacade productoFacade;

    // Listar todos los productos (solo admin)
    @GetMapping
    public ResponseEntity<List<ProductoDto>> list() {
        return ResponseEntity.ok(productoFacade.findAll());
    }

    // Listar productos de una empresa específica
    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<ProductoDto>> listByEmpresa(@PathVariable Long empresaId) {
        return ResponseEntity.ok(productoFacade.findByEmpresaId(empresaId));
    }

    // Obtener producto por id
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDto> getById(@PathVariable Long id) {
        return productoFacade.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtener producto de una empresa específica
    @GetMapping("/{id}/empresa/{empresaId}")
    public ResponseEntity<ProductoDto> getByIdAndEmpresa(@PathVariable Long id, @PathVariable Long empresaId) {
        return productoFacade.findByIdAndEmpresaId(id, empresaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear producto (empresaId obligatorio en el request)
    @PostMapping("/empresa/{empresaId}")
    public ResponseEntity<ProductoDto> create(
            @PathVariable Long empresaId,
            @RequestBody ProductoRequest request
    ) {
        request.setEmpresaId(empresaId);
        ProductoDto nuevo = productoFacade.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // Actualizar producto
    @PutMapping("/{id}/empresa/{empresaId}")
    public ResponseEntity<ProductoDto> update(
            @PathVariable Long id,
            @PathVariable Long empresaId,
            @RequestBody ProductoRequest request
    ) {
        request.setEmpresaId(empresaId);
        return productoFacade.update(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar producto de empresa específica
    @DeleteMapping("/{id}/empresa/{empresaId}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @PathVariable Long empresaId) {
        if (productoFacade.deleteByIdAndEmpresaId(id, empresaId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

