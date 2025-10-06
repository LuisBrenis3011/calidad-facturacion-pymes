package com.billtel.calidad.facturacion_pymes.layer.domain.dto.response;

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
public class ProductoDto {
    private Long id;
    private Empresa empresa;
    private String nombre;
    private String descripcion;
    private BigDecimal valorUnitario;
    private BigDecimal igv;
    private String unidadMedida;
}
