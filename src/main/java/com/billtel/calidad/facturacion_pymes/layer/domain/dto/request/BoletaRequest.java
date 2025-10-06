package com.billtel.calidad.facturacion_pymes.layer.domain.dto.request;

public class BoletaRequest extends ComprobanteRequest {
    private TipoDocumentoCliente tipoDocumentoCliente;
    public enum TipoDocumentoCliente { DNI, CARNET_EXT, PASAPORTE, RUC }
}
