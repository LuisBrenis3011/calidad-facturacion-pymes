package com.billtel.calidad.facturacion_pymes.layer.domain.dto.response;
import lombok.Data;
import java.util.List;

@Data
public class EmpresaDto {
    private Long id;
    private String ruc;
    private String razonSocial;
    private String direccion;
    private String email;
    private List<ProductoDto> productos;
}
