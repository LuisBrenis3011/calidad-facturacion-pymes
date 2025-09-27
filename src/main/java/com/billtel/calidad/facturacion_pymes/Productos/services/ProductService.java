package com.billtel.calidad.facturacion_pymes.Productos.services;

import com.billtel.calidad.facturacion_pymes.Productos.entities.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Producto> findAll();
    Optional<Producto> findById(Long id);
    Producto save(Producto product);
    void deleteById(Long id);
}
