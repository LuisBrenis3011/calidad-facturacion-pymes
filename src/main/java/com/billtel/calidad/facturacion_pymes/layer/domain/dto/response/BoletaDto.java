package com.billtel.calidad.facturacion_pymes.layer.domain.dto.response;

public class BoletaDto extends ComprobanteDto {
    private TipoDocumentoCliente tipoDocumentoCliente;
    public enum TipoDocumentoCliente { DNI, CARNET_EXT, PASAPORTE, RUC }
}
