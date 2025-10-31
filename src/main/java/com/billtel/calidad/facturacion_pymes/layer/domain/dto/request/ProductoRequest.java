package com.billtel.calidad.facturacion_pymes.layer.domain.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
public class ProductoRequest {

    @NotBlank(message = "El campo 'nombre' es obligatorio")
    private String nombre;
    @NotBlank(message = "El campo 'descripcion' es obligatorio")
    private String descripcion;

    @NotNull(message = "El campo 'valor unitario' es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El valor unitario debe ser mayor que 0")
    private BigDecimal valorUnitario;
    @NotBlank(message = "El campo 'unidad medida' es obligatorio")
    private String unidadMedida;

    @NotNull(message = "Debe especificarse la empresa")
    private Long empresaId;
}
