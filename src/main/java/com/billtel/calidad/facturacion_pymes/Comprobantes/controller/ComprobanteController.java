package com.billtel.calidad.facturacion_pymes.Comprobantes.controller;

import com.billtel.calidad.facturacion_pymes.Comprobantes.dto.ComprobanteDTO;
import com.billtel.calidad.facturacion_pymes.Comprobantes.dto.ComprobanteMapper;
import com.billtel.calidad.facturacion_pymes.Comprobantes.entities.Comprobante;
import com.billtel.calidad.facturacion_pymes.Comprobantes.services.ComprobanteService;
import com.billtel.calidad.facturacion_pymes.DetalleComprobante.dto.DetalleComprobanteDTO;
import com.billtel.calidad.facturacion_pymes.DetalleComprobante.dto.DetalleComprobanteMapper;
import com.billtel.calidad.facturacion_pymes.DetalleComprobante.entities.DetalleComprobante;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comprobantes")
public class ComprobanteController {

    private final ComprobanteService comprobanteService;

    public ComprobanteController(ComprobanteService comprobanteService) {
        this.comprobanteService = comprobanteService;
    }

    @GetMapping
    public ResponseEntity<List<ComprobanteDTO>> findAll() {
        List<ComprobanteDTO> list = comprobanteService.findAll().stream()
                .map(ComprobanteMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComprobanteDTO> findById(@PathVariable Long id) {
        Optional<Comprobante> opt = comprobanteService.findById(id);
        return opt.map(c -> ResponseEntity.ok(ComprobanteMapper.toDTO(c)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ComprobanteDTO> create(@RequestBody ComprobanteDTO dto) {
        Comprobante entity = ComprobanteMapper.toEntity(dto);
        if (entity.getDetalles() != null) {
            for (DetalleComprobante d : entity.getDetalles()) {
                d.setComprobante(entity);
            }
        }
        Comprobante saved = comprobanteService.save(entity);
        ComprobanteDTO out = ComprobanteMapper.toDTO(saved);
        return ResponseEntity.created(URI.create("/api/comprobantes/" + out.getId())).body(out);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComprobanteDTO> update(@PathVariable Long id, @RequestBody ComprobanteDTO dto) {
        Optional<Comprobante> opt = comprobanteService.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Comprobante existing = opt.get();
        existing.setNroDocCliente(dto.getNroDocCliente());
        existing.setDireccionCliente(dto.getDireccionCliente());
        existing.setSerie(dto.getSerie());
        existing.setCorrelativo(dto.getCorrelativo());
        existing.setFechaEmision(dto.getFechaEmision());
        existing.setSubtotal(dto.getSubtotal());
        existing.setIgvTotal(dto.getIgvTotal());
        existing.setTotal(dto.getTotal());
        if (dto.getEstadoSunat() != null) {
            existing.setEstadoSunat(Comprobante.EstadoSunat.valueOf(dto.getEstadoSunat()));
        }
        if (dto.getEmpresaId() != null) {
            var emp = new com.billtel.calidad.facturacion_pymes.Empresas.entities.Empresa();
            emp.setId(dto.getEmpresaId());
            existing.setEmpresa(emp);
        }
        if (dto.getDetalles() != null) {
            existing.getDetalles().clear();
            for (DetalleComprobanteDTO dDTO : dto.getDetalles()) {
                DetalleComprobante det = DetalleComprobanteMapper.toEntity(dDTO);
                det.setComprobante(existing);
                existing.getDetalles().add(det);
            }
        }
        Comprobante saved = comprobanteService.save(existing);
        return ResponseEntity.ok(ComprobanteMapper.toDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Comprobante> opt = comprobanteService.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        comprobanteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/detalles")
    public ResponseEntity<List<DetalleComprobanteDTO>> listDetalles(@PathVariable Long id) {
        Optional<Comprobante> opt = comprobanteService.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        List<DetalleComprobanteDTO> detalles = opt.get().getDetalles().stream()
                .map(DetalleComprobanteMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(detalles);
    }
}