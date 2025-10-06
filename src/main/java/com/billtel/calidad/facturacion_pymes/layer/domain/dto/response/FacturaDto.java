package com.billtel.calidad.facturacion_pymes.layer.domain.dto.response;

public class FacturaDto extends ComprobanteDto {
    private TipoPago tipoPago;
    public enum TipoPago { CONTADO, CREDITO }
}
