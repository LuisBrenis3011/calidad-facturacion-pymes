package com.billtel.calidad.facturacion_pymes.Productos.controller;

import com.billtel.calidad.facturacion_pymes.Productos.dto.ProductoDTO;
import com.billtel.calidad.facturacion_pymes.Productos.dto.ProductoMapper;
import com.billtel.calidad.facturacion_pymes.Productos.entities.Producto;
import com.billtel.calidad.facturacion_pymes.Productos.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> list() {
        List<ProductoDTO> productos = productService.findAll()
                .stream()
                .map(ProductoMapper::toDTO)
                .toList();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {
        return productService.findById(id)
                .map(product -> ResponseEntity.ok(ProductoMapper.toDTO(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductoDTO dto) {
        Producto producto = ProductoMapper.toEntity(dto);
        Producto saved = productService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductoMapper.toDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductoDTO dto) {
        return productService.findById(id).map(productDB -> {
            Producto producto = ProductoMapper.toEntity(dto);
            producto.setId(id); // mantener el id existente
            Producto saved = productService.save(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(ProductoMapper.toDTO(saved));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return productService.findById(id).map(product -> {
            productService.deleteById(id);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}

