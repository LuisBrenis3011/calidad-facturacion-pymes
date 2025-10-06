package com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobanteRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FacturaRequest extends ComprobanteRequest {
    private TipoPago tipoPago;
    public enum TipoPago { CONTADO, CREDITO }
}
