package com.billtel.calidad.facturacion_pymes.layer.domain.dto.request;

import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.Factura;

public class FacturaRequest extends ComprobanteRequest {
    private TipoPago tipoPago;
    public enum TipoPago { CONTADO, CREDITO }
}
