package com.billtel.calidad.facturacion_pymes.layer.domain.dto.response.comprobanteResponse;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.simplificados.EmpresaSimpleDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes.Comprobante;
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
public abstract class ComprobanteDto {
    private Long id;
    private EmpresaSimpleDto empresa;
    private String nroDocCliente;
    private String nombreCliente;
    private String direccionCliente;
    private String serie;
    private Integer correlativo;
    private LocalDateTime fechaEmision;
    private BigDecimal subtotal;
    private BigDecimal igvTotal;
    private BigDecimal total;
    private Comprobante.EstadoSunat estadoSunat;
    private List<DetalleComprobanteDto> detalles;
}
