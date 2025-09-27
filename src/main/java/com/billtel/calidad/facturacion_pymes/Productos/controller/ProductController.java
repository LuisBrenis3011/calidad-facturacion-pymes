package com.billtel.calidad.facturacion_pymes.Productos.controller;

import com.billtel.calidad.facturacion_pymes.Productos.entities.Producto;
import com.billtel.calidad.facturacion_pymes.Productos.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/producto")
public class ProductController {

    final private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> List() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {
        Optional<Producto> product = productService.findById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Producto product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id ,@RequestBody Producto product) {

        Optional<Producto> productOptional = productService.findById(id);
        if (productOptional.isPresent()) {
            Producto productDB= productOptional.orElseThrow();
            productDB.setNombre(product.getNombre());
            productDB.setDescripcion(product.getDescripcion());
            productDB.setIgv(product.getIgv());
            productDB.setUnidadMedida(product.getUnidadMedida());
            productDB.setEmpresa(product.getEmpresa());
            productDB.setValorUnitario(product.getValorUnitario());
            return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productDB));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Producto> productOptional = productService.findById(id);
        if (productOptional.isPresent()) {
            productService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
