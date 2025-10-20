package com.billtel.calidad.facturacion_pymes.layer.domain.dto.simplificados;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductoSimpleDto {
    private Long id;
    private String nombre;
    private BigDecimal valorUnitario;
    private String unidadMedida;
    // Sin empresa
}