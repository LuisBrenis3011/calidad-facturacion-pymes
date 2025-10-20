package com.billtel.calidad.facturacion_pymes.layer.domain.dto.response;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.simplificados.EmpresaSimpleDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;
import lombok.*;


import java.math.BigDecimal;

@Data
public class ProductoDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal valorUnitario;
    private BigDecimal igv;
    private String unidadMedida;

    // Empresa simplificada (sin usuario ni productos)
    private EmpresaSimpleDto empresa;
}
