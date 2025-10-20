package com.billtel.calidad.facturacion_pymes.layer.business.service.impl;

import com.billtel.calidad.facturacion_pymes.layer.business.service.IComprobanteService;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.Comprobante;
import com.billtel.calidad.facturacion_pymes.layer.persistence.ComprobanteRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@AllArgsConstructor
public class ComprobanteServiceImpl implements IComprobanteService {

    private final ComprobanteRepository comprobanteRepository;

    @Override
    public List<Comprobante> findAll() {
        return StreamSupport.stream(comprobanteRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<Comprobante> findByEmpresaId(Long empresaId) {
        return comprobanteRepository.findByEmpresaId(empresaId);
    }

    @Override
    public Optional<Comprobante> findById(Long id) {
        return comprobanteRepository.findById(id);
    }

    @Override
    public Optional<Comprobante> findByIdAndEmpresaId(Long id, Long empresaId) {
        return comprobanteRepository.findByIdAndEmpresaId(id, empresaId);
    }

    @Override
    public Comprobante save(Comprobante comprobante) {
        return comprobanteRepository.save(comprobante);
    }

    @Override
    public void deleteById(Long id) {
        comprobanteRepository.deleteById(id);
    }

    @Override
    public Integer getNextCorrelativo(Long empresaId, String serie) {
        return comprobanteRepository
                .findTopByEmpresaIdAndSerieOrderByCorrelativoDesc(empresaId, serie)
                .map(c -> c.getCorrelativo() + 1)
                .orElse(1);
    }
}