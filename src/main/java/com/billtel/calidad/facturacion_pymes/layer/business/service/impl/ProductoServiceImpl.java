package com.billtel.calidad.facturacion_pymes.layer.business.service.impl;

import com.billtel.calidad.facturacion_pymes.layer.business.service.IProductoService;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Producto;
import com.billtel.calidad.facturacion_pymes.layer.persistence.ProductoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@AllArgsConstructor
public class ProductoServiceImpl implements IProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public List<Producto> findAll() {
        return StreamSupport.stream(productoRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<Producto> findByEmpresaId(Long empresaId) {
        return productoRepository.findByEmpresaId(empresaId);
    }

    @Override
    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public Optional<Producto> findByIdAndEmpresaId(Long id, Long empresaId) {
        return productoRepository.findByIdAndEmpresaId(id, empresaId);
    }

    @Override
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public void deleteByIdAndEmpresaId(Long id, Long empresaId) {
        productoRepository.findByIdAndEmpresaId(id, empresaId)
                .ifPresent(producto -> productoRepository.deleteById(id));
    }
}