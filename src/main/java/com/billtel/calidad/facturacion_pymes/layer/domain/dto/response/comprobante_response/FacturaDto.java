package com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.comprobante_response;

import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.TipoPago;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FacturaDto extends ComprobanteDto {
    private TipoPago tipoPago;
}
