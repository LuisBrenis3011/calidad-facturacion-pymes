package com.billtel.calidad.facturacion_pymes.layer.domain.dto.request;

import com.billtel.calidad.facturacion_pymes.layer.domain.entity.users.Rol;

import lombok.Data;
import java.util.List;

@Data
public class UsuarioRequest {
    private String username;
    private String password;
    private Boolean enabled;
//     private boolean admin;
//    List<Empresa> empresas;
    private List<Rol> roles;
//    public Boolean isEnabled() {
//
//        return enabled;
//    }

}
