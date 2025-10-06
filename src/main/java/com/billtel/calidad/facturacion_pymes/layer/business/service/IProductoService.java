package com.billtel.calidad.facturacion_pymes.layer.business.service;


import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {
    List<Producto> findAll();
    Optional<Producto> findById(Long id);
    Producto save(Producto product);
    void deleteById(Long id);
}
