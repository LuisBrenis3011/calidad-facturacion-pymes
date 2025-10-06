package com.billtel.calidad.facturacion_pymes.layer.domain.dto.request.comprobanteRequest;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class ComprobanteRequest {
    private Integer idEmpresa;
    private String nroDocCliente;
    private String direccionCliente;
    private String serie;
    private Integer correlativo;
    private LocalDateTime fechaEmision;
    private BigDecimal subtotal;
    private BigDecimal igvTotal;
    private BigDecimal total;
    private EstadoSunat estadoSunat;

    public enum EstadoSunat {
        PENDIENTE,
        ENVIADO,
        RECHAZADO
    }
}
