package com.billtel.calidad.facturacion_pymes.layer.presentation.controller;

import com.billtel.calidad.facturacion_pymes.layer.business.facade.IEmpresaFacade;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.EmpresaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.EmpresaDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuario/{usuarioId}/empresas")
public class EmpresaController {

    final private IEmpresaFacade iEmpresaFacade;

    public EmpresaController(IEmpresaFacade iEmpresaFacade) {
        this.iEmpresaFacade = iEmpresaFacade;
    }


    @GetMapping
    public ResponseEntity<?> List() {
        return ResponseEntity.ok(iEmpresaFacade.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {
        Optional<EmpresaDto> empresa = iEmpresaFacade.findById(id);
        if (empresa.isPresent()) {
            return ResponseEntity.ok(empresa.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody EmpresaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iEmpresaFacade.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id ,@RequestBody EmpresaRequest request) {

        Optional<EmpresaDto> empresaOptional = iEmpresaFacade.findById(id);
        if (empresaOptional.isPresent()) {
            EmpresaDto empresaDB= empresaOptional.orElseThrow();
            empresaDB.setEmail(request.getEmail());
            empresaDB.setRuc(request.getRuc());
            empresaDB.setProductos(request.getProductos());
            empresaDB.setDireccion(request.getDireccion());
            empresaDB.setTelefono(request.getTelefono());
            empresaDB.setUsuario(request.getUsuario());
            empresaDB.setRazonSocial(request.getRazonSocial());
            return ResponseEntity.status(HttpStatus.CREATED).body(iEmpresaFacade.create(request));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<EmpresaDto> empresaOptional = iEmpresaFacade.findById(id);
        if (empresaOptional.isPresent()) {
            iEmpresaFacade.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
