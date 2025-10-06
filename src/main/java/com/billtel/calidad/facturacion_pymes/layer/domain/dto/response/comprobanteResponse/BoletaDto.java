package com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.comprobanteResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoletaDto extends ComprobanteDto {
    private TipoDocumentoCliente tipoDocumentoCliente;
    public enum TipoDocumentoCliente { DNI, CARNET_EXT, PASAPORTE, RUC }
}
