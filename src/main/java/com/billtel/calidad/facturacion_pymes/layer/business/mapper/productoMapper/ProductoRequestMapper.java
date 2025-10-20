package com.billtel.calidad.facturacion_pymes.layer.business.mapper.productoMapper;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.ProductoRequest;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductoRequestMapper {

    @Mapping(source = "empresaId", target = "empresa.id")
    Producto toDomain(ProductoRequest request);

    default Empresa map(Long empresaId) {
        if (empresaId == null) {
            return null;
        }
        Empresa empresa = new Empresa();
        empresa.setId(empresaId);
        return empresa;
    }
}
