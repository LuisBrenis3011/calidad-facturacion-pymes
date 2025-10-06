package com.billtel.calidad.facturacion_pymes.layer.business.facade.impl;

import com.billtel.calidad.facturacion_pymes.layer.business.facade.IComprobanteFacade;
import com.billtel.calidad.facturacion_pymes.layer.business.mapper.comprobanteMapper.ComprobanteDtoMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.mapper.comprobanteMapper.ComprobanteRequestMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.service.IComprobanteService;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobanteRequest.ComprobanteRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.comprobanteResponse.ComprobanteDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ComprobanteFacade implements IComprobanteFacade {
    private final IComprobanteService comprobanteService;
    private final ComprobanteRequestMapper comprobanteRequestMapper;
    private final ComprobanteDtoMapper comprobanteDtoMapper;

    @Override
    public List<ComprobanteDto> findAll() {
        return List.of();
    }

    @Override
    public Optional<ComprobanteDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public ComprobanteDto create(ComprobanteRequest request) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
