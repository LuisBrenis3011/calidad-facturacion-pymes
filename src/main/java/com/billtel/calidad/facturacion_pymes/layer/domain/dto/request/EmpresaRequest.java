package com.billtel.calidad.facturacion_pymes.layer.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmpresaRequest {

    @NotBlank(message = "El campo 'ruc' es obligatorio y no puede ser duplicado")
    private String ruc;
    @NotBlank(message = "El campo 'razon social' es obligatorio")
    private String razonSocial;
    @NotBlank(message = "El campo 'direccion' es obligatorio")
    private String direccion;

    private String username;
    @Email(message = "El email debe ser valido")
    private String email;

}
