package com.billtel.calidad.facturacion_pymes.Empresas.services;

import com.billtel.calidad.facturacion_pymes.Empresas.entities.Empresa;
import com.billtel.calidad.facturacion_pymes.Productos.entities.Producto;

import java.util.List;
import java.util.Optional;

public interface EmpresaService {
    List<Empresa> findAll();
    Optional<Empresa> findById(Long id);
    Empresa save(Empresa empresa);
    void deleteById(Long id);
}
