package com.billtel.calidad.facturacion_pymes.layer.business.facade.impl;

import com.billtel.calidad.facturacion_pymes.layer.business.facade.IEmpresaFacade;
import com.billtel.calidad.facturacion_pymes.layer.business.mapper.empresaMapper.EmpresaDtoMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.mapper.empresaMapper.EmpresaRequestMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.service.IEmpresaService;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.EmpresaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.EmpresaDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class EmpresaFacade implements IEmpresaFacade {
    private final IEmpresaService empresaService;
    private final EmpresaRequestMapper empresaRequestMapper;
    private final EmpresaDtoMapper empresaDtoMapper;

    @Override
    public List<EmpresaDto> findAll() {
        var empresas = empresaService.findAll();

        return StreamSupport.stream(empresas.spliterator(), false)
                .map(empresaDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmpresaDto> findByUsuarioId(Long usuarioId) {
        var empresas = empresaService.findByUsuarioId(usuarioId);

        return empresas.stream()
                .map(empresaDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EmpresaDto> findById(Long id) {
        var empresa = empresaService.findById(id);

        return empresa.map(empresaDtoMapper::toDto);
    }

    @Override
    public Optional<EmpresaDto> findByIdAndUsuarioId(Long id, Long usuarioId) {
        var empresa = empresaService.findByIdAndUsuarioId(id, usuarioId);

        return empresa.map(empresaDtoMapper::toDto);
    }

    @Override
    public EmpresaDto create(EmpresaRequest request) {
        var empresaRequest = empresaRequestMapper.toDomain(request);
        var empresaCreated = empresaService.save(request.getUsername(), empresaRequest);
        return empresaDtoMapper.toDto(empresaCreated);
    }

    @Override
    public void deleteById(Long id) {
        empresaService.deleteById(id);
    }

    @Override
    public void deleteByIdAndUsuarioId(Long id, Long usuarioId) {
        empresaService.deleteByIdAndUsuarioId(id, usuarioId);
    }

    @Override
    public List<EmpresaDto> findByUsername(String username) {
        var empresas = empresaService.findByUsername(username);
        return empresas.stream()
                .map(empresaDtoMapper::toDto)
                .toList();
    }

    @Override
    public Optional<EmpresaDto> update(EmpresaRequest request, Long id) {
        var empresaToUpdate = empresaRequestMapper.toDomain(request);

        var empresaUpdated = empresaService.update(empresaToUpdate, id);

        return empresaUpdated.map(empresaDtoMapper::toDto);
    }
}
