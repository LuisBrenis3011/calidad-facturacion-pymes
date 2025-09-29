package com.billtel.calidad.facturacion_pymes.Comprobantes.dto;

import com.billtel.calidad.facturacion_pymes.DetalleComprobante.dto.DetalleComprobanteDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ComprobanteDTO {

    private Long id;
    private Long empresaId;
    private String nroDocCliente;
    private String direccionCliente;
    private String serie;
    private Integer correlativo;
    private LocalDateTime fechaEmision;
    private BigDecimal subtotal;
    private BigDecimal igvTotal;
    private BigDecimal total;
    private String estadoSunat;
    private List<DetalleComprobanteDTO> detalles;


}
