package com.billtel.calidad.facturacion_pymes.DetalleComprobante.services;

import com.billtel.calidad.facturacion_pymes.DetalleComprobante.entities.DetalleComprobante;
import com.billtel.calidad.facturacion_pymes.DetalleComprobante.repositories.DetalleComprobanteRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DetalleComprobanteServiceImplements implements DetalleComprobanteService {

    private final DetalleComprobanteRepository repository;

    public DetalleComprobanteServiceImplements(DetalleComprobanteRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DetalleComprobante> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DetalleComprobante> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public DetalleComprobante save(DetalleComprobante detalle) {
        if (detalle.getSubtotal() == null && detalle.getCantidad() != null && detalle.getPrecioUnitario() != null) {
            BigDecimal sub = detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(detalle.getCantidad()));
            detalle.setSubtotal(sub);
        }
        return repository.save(detalle);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}