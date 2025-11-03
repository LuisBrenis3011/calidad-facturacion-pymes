package com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.comprobante_response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.TipoDocumentoCliente;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoletaDto extends ComprobanteDto {
    private TipoDocumentoCliente tipoDocumentoCliente;
}
