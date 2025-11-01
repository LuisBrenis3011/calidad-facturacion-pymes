package com.billtel.calidad.facturacion_pymes.layer.domain.dto.response;

import lombok.*;


import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductoDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal valorUnitario;
    private String unidadMedida;

}
