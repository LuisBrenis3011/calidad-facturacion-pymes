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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductoFacade implements IProductoFacade {
    private final IProductoService productoService;
    private final ProductoDtoMapper productoDtoMapper;
    private final ProductoRequestMapper productoRequestMapper;

    @Override
    public List<ProductoDto> findAll() {
        var users = productoService.findAll();

        return users.stream()
                .map(productoDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductoDto> findById(Long id) {
        var user = productoService.findById(id);

        return user.map(productoDtoMapper::toDto);
    }

    @Override
    public ProductoDto create(ProductoRequest request) {
        var userRequest = productoRequestMapper.toDomain(request);
        var userCreated = productoService.save(userRequest);
        return productoDtoMapper.toDto(userCreated);
    }

    @Override
    public void deleteById(Long id) {
        productoService.deleteById(id);
    }
}
