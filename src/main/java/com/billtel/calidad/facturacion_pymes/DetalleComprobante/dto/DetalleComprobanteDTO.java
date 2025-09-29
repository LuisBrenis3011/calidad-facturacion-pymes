package com.billtel.calidad.facturacion_pymes.DetalleComprobante.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DetalleComprobanteDTO {

    private Long id;
    private Long comprobanteId;
    private Long productoId;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

}