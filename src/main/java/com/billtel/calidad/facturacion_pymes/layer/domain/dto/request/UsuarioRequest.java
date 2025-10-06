package com.billtel.calidad.facturacion_pymes.layer.domain.dto.request;

import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.users.Rol;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {
    private String username;
    private String password;
    private Boolean enabled;
    @Transient
    private boolean admin;
    List<Empresa> empresas;
    private List<Rol> roles;
    public Boolean isEnabled() {

        return enabled;
    }
}
