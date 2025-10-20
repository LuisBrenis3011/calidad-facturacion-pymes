package com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobanteRequest;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class ComprobanteRequest {
    private Long empresaId;
    private String nroDocCliente;
    private String nombreCliente;
    private String direccionCliente;
    private String serie;
    private LocalDateTime fechaEmision;
    private List<DetalleComprobanteRequest> detalles;

    private String tipoComprobante;
}
