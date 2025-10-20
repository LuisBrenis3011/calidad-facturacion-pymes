package com.billtel.calidad.facturacion_pymes.layer.domain.dto.request;

import com.billtel.calidad.facturacion_pymes.layer.domain.dto.simplificados.ProductoSimpleDto;
import lombok.Data;

import java.util.List;

@Data
public class EmpresaRequest {
    private Long usuarioId;
    private String ruc;
    private String razonSocial;
    private String direccion;
    private String email;
    private String telefono;
    private String logo;
    private List<ProductoSimpleDto> productos;
}
