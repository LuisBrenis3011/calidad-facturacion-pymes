package com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobanteRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoletaRequest extends ComprobanteRequest {
    private TipoDocumentoCliente tipoDocumentoCliente;
    public enum TipoDocumentoCliente { DNI, CARNET_EXT, PASAPORTE, RUC }
}
