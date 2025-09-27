package com.billtel.calidad.facturacion_pymes.Productos.services;

import com.billtel.calidad.facturacion_pymes.Productos.entities.Producto;
import com.billtel.calidad.facturacion_pymes.Productos.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductServiceImplements implements ProductService {
    final private ProductRepository repository;

    public ProductServiceImplements(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Producto> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<Producto> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Producto save(Producto product) {
        return repository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
