package com.billtel.calidad.facturacion_pymes.layer.business.service.impl;

import com.billtel.calidad.facturacion_pymes.layer.business.service.IComprobanteService;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.Comprobante;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ComprobanteServiceImpl implements IComprobanteService {
    @Override
    public List<Comprobante> findAll() {
        return List.of();
    }

    @Override
    public Optional<Comprobante> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Comprobante save(Comprobante comprobante) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
