package com.billtel.calidad.facturacion_pymes.layer.business.facade.impl;

import com.billtel.calidad.facturacion_pymes.layer.business.facade.IEmpresaFacade;
import com.billtel.calidad.facturacion_pymes.layer.business.mapper.empresaMapper.EmpresaDtoMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.mapper.empresaMapper.EmpresaRequestMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.service.IEmpresaService;
import com.billtel.calidad.facturacion_pymes.layer.business.service.IProductoService;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.EmpresaRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.EmpresaDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmpresaFacade implements IEmpresaFacade {
    private final IEmpresaService empresaService;
    private final IProductoService productoService;
    private final EmpresaRequestMapper empresaRequestMapper;
    private final EmpresaDtoMapper empresaDtoMapper;

    @Override
    public List<EmpresaDto> findAll() {
        return List.of();
    }

    @Override
    public Optional<EmpresaDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public EmpresaDto create(EmpresaRequest request) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
