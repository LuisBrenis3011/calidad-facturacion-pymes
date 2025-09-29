package com.billtel.calidad.facturacion_pymes.Comprobantes.services;

import com.billtel.calidad.facturacion_pymes.Comprobantes.entities.Comprobante;
import com.billtel.calidad.facturacion_pymes.Comprobantes.repositories.ComprobanteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ComprobanteServiceImplements implements ComprobanteService {

    private final ComprobanteRepository repository;

    public ComprobanteServiceImplements(ComprobanteRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Comprobante> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<Comprobante> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Comprobante save(Comprobante comprobante) {
        return repository.save(comprobante);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
