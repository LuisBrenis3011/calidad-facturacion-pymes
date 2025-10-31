package com.billtel.calidad.facturacion_pymes.layer.business.facade;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.ProductoRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.ProductoDto;

import java.util.List;
import java.util.Optional;

public interface IProductoFacade {
    List<ProductoDto> findAll();
    List<ProductoDto> findByEmpresaId(Long empresaId);
    Optional<ProductoDto> findById(Long id);
    Optional<ProductoDto> findByIdAndEmpresaId(Long id, Long empresaId);
    ProductoDto create(ProductoRequest request);
    Optional<ProductoDto> update(Long id, ProductoRequest request);
    void deleteById(Long id);
    boolean deleteByIdAndEmpresaId(Long id, Long empresaId);
}
