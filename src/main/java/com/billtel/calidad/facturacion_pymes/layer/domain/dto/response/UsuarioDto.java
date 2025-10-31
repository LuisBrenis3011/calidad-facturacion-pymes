package com.billtel.calidad.facturacion_pymes.layer.domain.dto.response;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.simplificados.EmpresaSimpleDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {
    private Long id;
    private String username;
    private String email;
    private boolean admin;
    private List<EmpresaSimpleDto> empresas;

}
