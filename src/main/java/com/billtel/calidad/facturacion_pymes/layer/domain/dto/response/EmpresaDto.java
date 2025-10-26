package com.billtel.calidad.facturacion_pymes.layer.domain.dto.response;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.simplificados.ProductoSimpleDto;
import com.billtel.calidad.facturacion_pymes.layer.domain.dto.simplificados.UsuarioSimpleDto;
import lombok.Data;
import java.util.List;

@Data
public class EmpresaDto {
    private Long id;
    private String ruc;
    private String razonSocial;
    private String direccion;
    private String email;
    private String telefono;
    private String logo;

    private UsuarioSimpleDto usuario;

    private List<ProductoSimpleDto> productos;
}
