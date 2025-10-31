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
    private final ProductoRequestMapper productoRequestMapper;
    private final ProductoDtoMapper productoDtoMapper;

    @Override
    public List<ProductoDto> findAll() {
        var productos = productoService.findAll();

        return productos.stream()
                .map(productoDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoDto> findByEmpresaId(Long empresaId) {
        var productos = productoService.findByEmpresaId(empresaId);

        return productos.stream()
                .map(productoDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductoDto> findById(Long id) {
        var producto = productoService.findById(id);

        return producto.map(productoDtoMapper::toDto);
    }

    @Override
    public Optional<ProductoDto> findByIdAndEmpresaId(Long id, Long empresaId) {
        var producto = productoService.findByIdAndEmpresaId(id, empresaId);

        return producto.map(productoDtoMapper::toDto);
    }

    @Override
    public ProductoDto create(ProductoRequest request) {
        var productoRequest = productoRequestMapper.toDomain(request);
        var productoCreated = productoService.save(productoRequest);
        return productoDtoMapper.toDto(productoCreated);
    }

    @Override
    public Optional<ProductoDto> update(Long id, ProductoRequest request) {
        var producto = productoRequestMapper.toDomain(request);
        return productoService.update(id, producto)
                .map(productoDtoMapper::toDto);
    }

    @Override
    public void deleteById(Long id) {
        productoService.deleteById(id);
    }

    public boolean deleteByIdAndEmpresaId(Long id, Long empresaId) {
        var producto = productoService.findByIdAndEmpresaId(id, empresaId);
        if (producto.isPresent()) {
            productoService.deleteById(id);
            return true;
        }
        return false;
    }
}
