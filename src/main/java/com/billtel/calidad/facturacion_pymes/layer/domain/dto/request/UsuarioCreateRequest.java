package com.billtel.calidad.facturacion_pymes.layer.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioCreateRequest {
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 4, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotEmpty
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank
    private String password;

    private boolean admin;
}
