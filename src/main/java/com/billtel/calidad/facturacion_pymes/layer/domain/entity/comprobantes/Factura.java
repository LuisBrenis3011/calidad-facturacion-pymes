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
@Table(name = "factura")
@DiscriminatorValue("FACTURA")
public class Factura extends Comprobante {

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pago")
    private TipoPago tipoPago;
}