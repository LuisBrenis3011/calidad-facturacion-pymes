package com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "boleta")
@DiscriminatorValue("BOLETA")
public class Boleta extends Comprobante {
    @Enumerated(EnumType.STRING)
    private TipoDocumentoCliente tipoDocumentoCliente;
    public enum TipoDocumentoCliente { DNI, CARNET_EXT, PASAPORTE, RUC }
}