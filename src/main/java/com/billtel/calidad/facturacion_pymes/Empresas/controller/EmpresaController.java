package com.billtel.calidad.facturacion_pymes.Empresas.controller;

import com.billtel.calidad.facturacion_pymes.Empresas.entities.Empresa;
import com.billtel.calidad.facturacion_pymes.Empresas.services.EmpresaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    final private EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }


    @GetMapping
    public ResponseEntity<?> List() {
        return ResponseEntity.ok(empresaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {
        Optional<Empresa> empresa = empresaService.findById(id);
        if (empresa.isPresent()) {
            return ResponseEntity.ok(empresa.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Empresa empresa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empresaService.save(empresa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id ,@RequestBody Empresa empresa) {

        Optional<Empresa> empresaOptional = empresaService.findById(id);
        if (empresaOptional.isPresent()) {
            Empresa empresaDB= empresaOptional.orElseThrow();
            empresaDB.setEmail(empresa.getEmail());
            empresaDB.setRuc(empresa.getRuc());
            empresaDB.setProductos(empresa.getProductos());
            empresaDB.setDireccion(empresa.getDireccion());
            empresaDB.setTelefono(empresa.getTelefono());
            empresaDB.setUsuario(empresa.getUsuario());
            empresaDB.setRazonSocial(empresa.getRazonSocial());
            return ResponseEntity.status(HttpStatus.CREATED).body(empresaService.save(empresaDB));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Empresa> empresaOptional = empresaService.findById(id);
        if (empresaOptional.isPresent()) {
            empresaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}
