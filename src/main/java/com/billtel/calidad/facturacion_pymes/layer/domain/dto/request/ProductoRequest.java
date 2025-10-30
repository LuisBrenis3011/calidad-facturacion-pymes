package com.billtel.calidad.facturacion_pymes.layer.domain.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Data
public class ProductoRequest {

    private String nombre;
    private String descripcion;
    private BigDecimal valorUnitario;
    private String unidadMedida;
}
