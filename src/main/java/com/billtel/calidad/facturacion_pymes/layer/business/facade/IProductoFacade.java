package com.billtel.calidad.facturacion_pymes.layer.business.facade;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.ProductoRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.ProductoDto;

import java.util.List;
import java.util.Optional;

public interface IProductoFacade {
    List<ProductoDto> findAll();
    Optional<ProductoDto> findById(Long id);
    ProductoDto create(ProductoRequest request);
    void deleteById(Long id);
}
