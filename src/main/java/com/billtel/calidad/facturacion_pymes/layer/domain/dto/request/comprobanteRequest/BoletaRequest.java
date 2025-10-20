package com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobanteRequest;

import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.TipoDocumentoCliente;
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
}
