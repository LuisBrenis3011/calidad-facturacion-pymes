package com.billtel.calidad.facturacion_pymes.DetalleComprobante.controller;

import com.billtel.calidad.facturacion_pymes.Comprobantes.entities.Comprobante;
import com.billtel.calidad.facturacion_pymes.Comprobantes.services.ComprobanteService;
import com.billtel.calidad.facturacion_pymes.DetalleComprobante.dto.DetalleComprobanteDTO;
import com.billtel.calidad.facturacion_pymes.DetalleComprobante.dto.DetalleComprobanteMapper;
import com.billtel.calidad.facturacion_pymes.DetalleComprobante.entities.DetalleComprobante;
import com.billtel.calidad.facturacion_pymes.DetalleComprobante.services.DetalleComprobanteService;
import com.billtel.calidad.facturacion_pymes.Productos.entities.Producto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/detalles")
public class DetalleComprobanteController {

    private final DetalleComprobanteService detalleService;
    private final ComprobanteService comprobanteService;

    public DetalleComprobanteController(DetalleComprobanteService detalleService,
                                        ComprobanteService comprobanteService) {
        this.detalleService = detalleService;
        this.comprobanteService = comprobanteService;
    }

    @GetMapping
    public ResponseEntity<List<DetalleComprobanteDTO>> findAll() {
        List<DetalleComprobanteDTO> list = StreamSupport.stream(detalleService.findAll().spliterator(), false)
                .map(DetalleComprobanteMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleComprobanteDTO> findById(@PathVariable Long id) {
        Optional<DetalleComprobante> opt = detalleService.findById(id);
        return opt.map(d -> ResponseEntity.ok(DetalleComprobanteMapper.toDTO(d)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody DetalleComprobanteDTO dto) {
        if (dto.getComprobanteId() == null) {
            return ResponseEntity.badRequest().body("comprobanteId es obligatorio");
        }
        Optional<Comprobante> compOpt = comprobanteService.findById(dto.getComprobanteId());
        if (compOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("El comprobante con id " + dto.getComprobanteId() + " no existe");
        }
        DetalleComprobante entity = DetalleComprobanteMapper.toEntity(dto);
        entity.setComprobante(compOpt.get());
        if (dto.getProductoId() != null) {
            Producto p = new Producto();
            p.setId(dto.getProductoId());
            entity.setProducto(p);
        }
        DetalleComprobante saved = detalleService.save(entity);
        DetalleComprobanteDTO out = DetalleComprobanteMapper.toDTO(saved);
        return ResponseEntity.created(URI.create("/api/detalles/" + out.getId())).body(out);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody DetalleComprobanteDTO dto) {
        Optional<DetalleComprobante> opt = detalleService.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        DetalleComprobante existing = opt.get();
        if (dto.getComprobanteId() != null && !dto.getComprobanteId().equals(
                existing.getComprobante() != null ? existing.getComprobante().getId() : null)) {
            Optional<Comprobante> newComp = comprobanteService.findById(dto.getComprobanteId());
            if (newComp.isEmpty()) {
                return ResponseEntity.badRequest().body("El comprobante con id " + dto.getComprobanteId() + " no existe");
            }
            existing.setComprobante(newComp.get());
        }
        if (dto.getProductoId() != null) {
            Producto p = new Producto();
            p.setId(dto.getProductoId());
            existing.setProducto(p);
        }
        existing.setCantidad(dto.getCantidad());
        existing.setPrecioUnitario(dto.getPrecioUnitario());
        existing.setSubtotal(dto.getSubtotal());
        DetalleComprobante saved = detalleService.save(existing);
        return ResponseEntity.ok(DetalleComprobanteMapper.toDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<DetalleComprobante> opt = detalleService.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        detalleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}