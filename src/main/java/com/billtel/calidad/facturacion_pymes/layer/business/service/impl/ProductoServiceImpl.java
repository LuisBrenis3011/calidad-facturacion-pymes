package com.billtel.calidad.facturacion_pymes.layer.business.service.impl;

import com.billtel.calidad.facturacion_pymes.layer.business.service.IProductoService;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Producto;
import com.billtel.calidad.facturacion_pymes.layer.persistence.EmpresaRepository;
import com.billtel.calidad.facturacion_pymes.layer.persistence.ProductoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@AllArgsConstructor
public class ProductoServiceImpl implements IProductoService {

    private final ProductoRepository productoRepository;
    private final EmpresaRepository empresaRepository;

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
        producto.setIgv(producto.getValorUnitario().multiply(new BigDecimal("0.18")));
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> update(Long id, Producto producto) {
        Optional<Producto> productoOptional = findById(id);

        if (productoOptional.isPresent()) {
            Producto productoDB = productoOptional.orElseThrow();

            productoDB.setEmpresa(producto.getEmpresa());
            productoDB.setNombre(producto.getNombre());
            productoDB.setDescripcion(producto.getDescripcion());
            productoDB.setValorUnitario(producto.getValorUnitario());
            productoDB.setUnidadMedida(producto.getUnidadMedida());
            BigDecimal igv = producto.getValorUnitario()
                    .multiply(new BigDecimal("0.18"))
                    .add(producto.getValorUnitario());

            productoDB.setIgv(igv);

            return Optional.of(productoRepository.save(productoDB));
        }

        return Optional.empty();
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