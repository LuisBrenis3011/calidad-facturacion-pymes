package com.billtel.calidad.facturacion_pymes.layer.business.service;


import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {
    List<Producto> findAll();
    List<Producto> findByEmpresaId(Long empresaId);
    Optional<Producto> findById(Long id);
    Optional<Producto> findByIdAndEmpresaId(Long id, Long empresaId);
    Producto save(Producto producto);
    void deleteById(Long id);
    void deleteByIdAndEmpresaId(Long id, Long empresaId);
}
