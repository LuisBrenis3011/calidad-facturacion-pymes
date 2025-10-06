package com.billtel.calidad.facturacion_pymes.layer.domain.dto.response;

import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.Comprobante;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class ComprobanteDto {
    private Integer id;
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
