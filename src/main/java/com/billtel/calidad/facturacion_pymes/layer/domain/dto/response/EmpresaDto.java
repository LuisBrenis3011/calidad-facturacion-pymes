package com.billtel.calidad.facturacion_pymes.layer.domain.dto.response;

import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Producto;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.users.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaDto {
    private Long id;
    private Usuario usuario;
    private String ruc;
    private String razonSocial;
    private String direccion;
    private String email;
    private String telefono;
    private String logo;
    private List<Producto> productos;
}
