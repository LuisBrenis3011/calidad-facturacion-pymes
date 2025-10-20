package com.billtel.calidad.facturacion_pymes.layer.domain.dto.simplificados;

import lombok.Data;

@Data
public class EmpresaSimpleDto {
    private Long id;
    private String ruc;
    private String razonSocial;
    // Sin usuario ni productos
}
