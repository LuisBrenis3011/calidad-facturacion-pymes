package com.billtel.calidad.facturacion_pymes.layer.domain.dto.request;

import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoRequest {
    private Empresa empresa;
    private String nombre;
    private String descripcion;
    private BigDecimal valorUnitario;
    private BigDecimal igv;
    private String unidadMedida;
}
