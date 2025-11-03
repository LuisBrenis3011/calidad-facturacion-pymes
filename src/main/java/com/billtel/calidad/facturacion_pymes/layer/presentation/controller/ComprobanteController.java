package com.billtel.calidad.facturacion_pymes.layer.presentation.controller;

import com.billtel.calidad.facturacion_pymes.layer.business.facade.IComprobanteFacade;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobante_request.BoletaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobante_request.FacturaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.comprobante_response.ComprobanteDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@RestController
@RequestMapping("/comprobante")
@AllArgsConstructor
public class ComprobanteController {

    private final IComprobanteFacade comprobanteFacade;

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<ComprobanteDto>> listByEmpresa(@PathVariable Long empresaId) {
        return ResponseEntity.ok(comprobanteFacade.findByEmpresaId(empresaId));
    }

    @GetMapping
    public ResponseEntity<List<ComprobanteDto>> listAll() {
        return ResponseEntity.ok(comprobanteFacade.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComprobanteDto> details(@PathVariable Long id) {
        return toResponseEntity(comprobanteFacade.findById(id));
    }

    @GetMapping("/{id}/empresa/{empresaId}")
    public ResponseEntity<ComprobanteDto> detailsByEmpresa(@PathVariable Long id, @PathVariable Long empresaId) {
        return toResponseEntity(comprobanteFacade.findByIdAndEmpresaId(id, empresaId));
    }

    @PostMapping("/factura")
    public ResponseEntity<Object> createFactura(@RequestBody FacturaRequest request) {
        return createComprobante(() -> comprobanteFacade.create(request));
    }

    @PostMapping("/boleta")
    public ResponseEntity<Object> createBoleta(@RequestBody BoletaRequest request) {
        return createComprobante(() -> comprobanteFacade.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return deleteComprobante(comprobanteFacade.findById(id), id);
    }

    @DeleteMapping("/{id}/empresa/{empresaId}")
    public ResponseEntity<Void> deleteByEmpresa(@PathVariable Long id, @PathVariable Long empresaId) {
        return deleteComprobante(comprobanteFacade.findByIdAndEmpresaId(id, empresaId), id);
    }

    private ResponseEntity<ComprobanteDto> toResponseEntity(Optional<ComprobanteDto> optional) {
        return optional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private ResponseEntity<Object> createComprobante(Supplier<ComprobanteDto> creator) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(creator.get());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    private ResponseEntity<Void> deleteComprobante(Optional<ComprobanteDto> optional, Long id) {
        return optional
                .map(dto -> {
                    comprobanteFacade.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}