package com.billtel.calidad.facturacion_pymes.layer.business.service.impl;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;

import com.billtel.calidad.facturacion_pymes.layer.business.service.IEmpresaService;
import com.billtel.calidad.facturacion_pymes.layer.persistence.EmpresaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class EmpresaServiceImpl implements IEmpresaService {
    final private EmpresaRepository empresaRepository;

    public EmpresaServiceImpl(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Override
    public List<Empresa> findAll() {
        return StreamSupport.stream(empresaRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<Empresa> findById(Long id) {
        return empresaRepository.findById(id);
    }

    @Override
    public Empresa save(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    @Override
    public void deleteById(Long id) {
        empresaRepository.deleteById(id);
    }
}
