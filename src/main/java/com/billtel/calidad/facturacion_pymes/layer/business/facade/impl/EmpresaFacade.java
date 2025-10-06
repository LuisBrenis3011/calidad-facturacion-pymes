package com.billtel.calidad.facturacion_pymes.layer.business.facade.impl;

import com.billtel.calidad.facturacion_pymes.layer.business.facade.IEmpresaFacade;
import com.billtel.calidad.facturacion_pymes.layer.business.mapper.empresaMapper.EmpresaDtoMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.mapper.empresaMapper.EmpresaRequestMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.service.IEmpresaService;
import com.billtel.calidad.facturacion_pymes.layer.business.service.IProductoService;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.EmpresaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.EmpresaDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;
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
    private final IProductoService productoService;
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
    public Optional<EmpresaDto> findById(Long id) {
        var empresa = empresaService.findById(id);

        return empresa.map(empresaDtoMapper::toDto);
    }

    @Override
    public EmpresaDto create(EmpresaRequest request) {
        var empresaRequest = empresaRequestMapper.toDomain(request);
        var empresaCreated = empresaService.save(empresaRequest);
        return empresaDtoMapper.toDto(empresaCreated);
    }

    @Override
    public void deleteById(Long id) {
        empresaService.deleteById(id);
    }
}
