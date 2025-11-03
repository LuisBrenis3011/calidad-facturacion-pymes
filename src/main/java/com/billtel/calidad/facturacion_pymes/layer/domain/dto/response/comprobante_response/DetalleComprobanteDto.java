package com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.comprobante_response;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.simplificados.ProductoSimpleDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleComprobanteDto {
    private Long id;
    private ProductoSimpleDto producto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    private BigDecimal igv;
    private BigDecimal total;
}
