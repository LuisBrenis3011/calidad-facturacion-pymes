package com.billtel.calidad.facturacion_pymes.layer.presentation.controller;

import com.billtel.calidad.facturacion_pymes.layer.business.facade.IEmpresaFacade;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.EmpresaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.EmpresaDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    final private IEmpresaFacade iEmpresaFacade;

    public EmpresaController(IEmpresaFacade iEmpresaFacade) {
        this.iEmpresaFacade = iEmpresaFacade;
    }

    // Listar todas las empresas de un usuario específico
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> listByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(iEmpresaFacade.findByUsuarioId(usuarioId));
    }

    // Listar todas las empresas (admin)
    @GetMapping
    public ResponseEntity<?> List() {
        return ResponseEntity.ok(iEmpresaFacade.findAll());
    }

    // Obtener una empresa específica de un usuario
    @GetMapping("/{id}/usuario/{usuarioId}")
    public ResponseEntity<?> detailsByUsuario(@PathVariable Long id, @PathVariable Long usuarioId) {
        Optional<EmpresaDto> empresa = iEmpresaFacade.findByIdAndUsuarioId(id, usuarioId);
        if (empresa.isPresent()) {
            return ResponseEntity.ok(empresa.orElseThrow());
        }
        return ResponseEntity.notFound().build();
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

    @PutMapping("/{id}/usuario/{usuarioId}")
    public ResponseEntity<?> updateByUsuario(@PathVariable Long id, @PathVariable Long usuarioId, @RequestBody EmpresaRequest request) {
        Optional<EmpresaDto> empresaOptional = iEmpresaFacade.findByIdAndUsuarioId(id, usuarioId);
        if (empresaOptional.isPresent()) {
            // El request ya no tiene objetos anidados completos
            request.setUsuarioId(usuarioId);  // Aseguramos que use el usuario correcto
            return ResponseEntity.status(HttpStatus.OK).body(iEmpresaFacade.create(request));
        }
        return ResponseEntity.notFound().build();
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody EmpresaRequest request) {
//        Optional<EmpresaDto> empresaOptional = iEmpresaFacade.findById(id);
//        if (empresaOptional.isPresent()) {
//            EmpresaDto empresaDB = empresaOptional.orElseThrow();
//            empresaDB.setEmail(request.getEmail());
//            empresaDB.setRuc(request.getRuc());
//            empresaDB.setProductos(request.getProductos());
//            empresaDB.setDireccion(request.getDireccion());
//            empresaDB.setTelefono(request.getTelefono());
//            empresaDB.setUsuario(request.getUsuario());
//            empresaDB.setRazonSocial(request.getRazonSocial());
//            return ResponseEntity.status(HttpStatus.OK).body(iEmpresaFacade.create(request));
//        }
//        return ResponseEntity.notFound().build();
//    }

    @DeleteMapping("/{id}/usuario/{usuarioId}")
    public ResponseEntity<?> deleteByUsuario(@PathVariable Long id, @PathVariable Long usuarioId) {
        Optional<EmpresaDto> empresaOptional = iEmpresaFacade.findByIdAndUsuarioId(id, usuarioId);
        if (empresaOptional.isPresent()) {
            iEmpresaFacade.deleteByIdAndUsuarioId(id, usuarioId);
            return ResponseEntity.noContent().build();
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
