package com.billtel.calidad.facturacion_pymes.Productos.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductoDTO {

    private Long id;
    private Long empresaId;
    private String nombre;
    private String descripcion;
    private BigDecimal valorUnitario;
    private BigDecimal igv;
    private String unidadMedida;


}
