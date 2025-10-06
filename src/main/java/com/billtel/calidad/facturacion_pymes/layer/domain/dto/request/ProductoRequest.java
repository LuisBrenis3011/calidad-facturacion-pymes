package com.billtel.calidad.facturacion_pymes.layer.domain.dto.request;

import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;
import lombok.*;

import java.math.BigDecimal;

@Data
public class ProductoRequest {
    private Empresa empresa;
    private String nombre;
    private String descripcion;
    private BigDecimal valorUnitario;
    private BigDecimal igv;
    private String unidadMedida;

}
