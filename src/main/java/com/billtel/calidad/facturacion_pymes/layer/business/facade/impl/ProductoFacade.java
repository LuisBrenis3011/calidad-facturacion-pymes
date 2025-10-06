package com.billtel.calidad.facturacion_pymes.layer.business.facade.impl;

import com.billtel.calidad.facturacion_pymes.layer.business.facade.IProductoFacade;
import com.billtel.calidad.facturacion_pymes.layer.business.mapper.productoMapper.ProductoDtoMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.mapper.productoMapper.ProductoRequestMapper;
import com.billtel.calidad.facturacion_pymes.layer.business.service.IProductoService;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.ProductoRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.ProductoDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductoFacade implements IProductoFacade {
    private final IProductoService productoService;
    private final ProductoDtoMapper productoDtoMapper;
    private final ProductoRequestMapper productoRequestMapper;

    @Override
    public List<ProductoDto> findAll() {
        return List.of();
    }

    @Override
    public Optional<ProductoDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public ProductoDto create(ProductoRequest request) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
