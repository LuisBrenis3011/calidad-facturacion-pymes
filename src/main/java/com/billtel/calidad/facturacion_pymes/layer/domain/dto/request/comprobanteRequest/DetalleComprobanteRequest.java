package com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobanteRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleComprobanteRequest {
    private Long productoId;
    private Integer cantidad;
    // Los demás campos se calculan automáticamente desde el producto
}
