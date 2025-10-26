package com.billtel.calidad.facturacion_pymes.layer.presentation.controller;

import com.billtel.calidad.facturacion_pymes.layer.business.facade.IComprobanteFacade;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobanteRequest.BoletaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobanteRequest.FacturaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.comprobanteResponse.ComprobanteDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/comprobante")
@AllArgsConstructor
public class ComprobanteController {

    private final IComprobanteFacade comprobanteFacade;

//  Listar todos los comprobantes de una empresa específica
    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<?> listByEmpresa(@PathVariable Long empresaId) {
        return ResponseEntity.ok(comprobanteFacade.findByEmpresaId(empresaId));
    }


//  Listar todos los comprobantes (admin)
    @GetMapping
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(comprobanteFacade.findAll());
    }

//   Obtener un comprobante por ID (sin validar empresa)
    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {
        Optional<ComprobanteDto> comprobante = comprobanteFacade.findById(id);
        if (comprobante.isPresent()) {
            return ResponseEntity.ok(comprobante.get());
        }
        return ResponseEntity.notFound().build();
    }

    //  Obtener un comprobante específico de una empresa
    @GetMapping("/{id}/empresa/{empresaId}")
    public ResponseEntity<?> detailsByEmpresa(@PathVariable Long id, @PathVariable Long empresaId) {
        Optional<ComprobanteDto> comprobante = comprobanteFacade.findByIdAndEmpresaId(id, empresaId);
        if (comprobante.isPresent()) {
            return ResponseEntity.ok(comprobante.get());
        }
        return ResponseEntity.notFound().build();
    }

    //  Crear una nueva FACTURA
    @PostMapping("/factura")
    public ResponseEntity<?> createFactura(@RequestBody FacturaRequest request) {
        try {
            ComprobanteDto comprobante = comprobanteFacade.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(comprobante);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear factura: " + e.getMessage());
        }
    }

//  Crear una nueva BOLETA

    @PostMapping("/boleta")
    public ResponseEntity<?> createBoleta(@RequestBody BoletaRequest request) {
        try {
            ComprobanteDto comprobante = comprobanteFacade.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(comprobante);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear boleta: " + e.getMessage());
        }
    }

    /**
     * Crear comprobante genérico (detecta el tipo automáticamente)
     * POST /comprobante
     */
//    @PostMapping
//    public ResponseEntity<?> create(@RequestBody ComprobanteRequest request) {
//        try {
//            ComprobanteDto comprobante = comprobanteFacade.create(request);
//            return ResponseEntity.status(HttpStatus.CREATED).body(comprobante);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error al crear comprobante: " + e.getMessage());
//        }
//    }

    /**
     * Eliminar un comprobante
     * DELETE /comprobante/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<ComprobanteDto> comprobanteOptional = comprobanteFacade.findById(id);
        if (comprobanteOptional.isPresent()) {
            comprobanteFacade.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Eliminar comprobante validando que pertenece a la empresa
     * DELETE /comprobante/{id}/empresa/{empresaId}
     */
    @DeleteMapping("/{id}/empresa/{empresaId}")
    public ResponseEntity<?> deleteByEmpresa(@PathVariable Long id, @PathVariable Long empresaId) {
        Optional<ComprobanteDto> comprobanteOptional = comprobanteFacade.findByIdAndEmpresaId(id, empresaId);
        if (comprobanteOptional.isPresent()) {
            comprobanteFacade.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
