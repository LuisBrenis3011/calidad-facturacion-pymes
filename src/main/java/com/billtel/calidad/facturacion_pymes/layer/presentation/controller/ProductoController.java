package com.billtel.calidad.facturacion_pymes.layer.presentation.controller;
import com.billtel.calidad.facturacion_pymes.layer.business.facade.IProductoFacade;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.ProductoRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.ProductoDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    private final IProductoFacade iProductoFacade;

    public ProductoController(IProductoFacade iProductoFacade) {
        this.iProductoFacade = iProductoFacade;
    }

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(iProductoFacade.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {
        Optional<ProductoDto> producto = iProductoFacade.findById(id);
        if (producto.isPresent()) {
            return ResponseEntity.ok(producto.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iProductoFacade.create(request));
    }


//    @PutMapping("/{id}")
//    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductoDTO dto) {
//        return iProductoFacade.findById(id).map(productDB -> {
//            Producto producto = ProductoMapper.toEntity(dto);
//            producto.setId(id);
//            Producto saved = productService.save(producto);
//            return ResponseEntity.status(HttpStatus.CREATED).body(ProductoMapper.toDTO(saved));
//        }).orElse(ResponseEntity.notFound().build());
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<ProductoDto> productoOptional = iProductoFacade.findById(id);
        if (productoOptional.isPresent()) {
            iProductoFacade.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
