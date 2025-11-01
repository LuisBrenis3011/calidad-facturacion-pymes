package com.billtel.calidad.facturacion_pymes.Empresas.services;

import com.billtel.calidad.facturacion_pymes.Empresas.entities.Empresa;
import com.billtel.calidad.facturacion_pymes.Empresas.repositories.EmpresaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EmpresaServiceImplements implements EmpresaService {

    final private EmpresaRepository empresaRepository;

    public EmpresaServiceImplements(EmpresaRepository empresaRepository) {
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
