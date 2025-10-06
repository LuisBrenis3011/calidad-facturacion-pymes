package com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "comprobante")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_comprobante")
public abstract class Comprobante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comprobante")
    private Long id;

    @Column(name = "id_empresa")
    private Integer idEmpresa;

    @Column(name = "nro_doc_cliente", length = 20)
    private String nroDocCliente;

    @Column(name = "direccion_cliente")
    private String direccionCliente;

    @Column(name = "serie", length = 4)
    private String serie;

    @Column(name = "correlativo")
    private Integer correlativo;

    @Column(name = "fecha_emision")
    private LocalDateTime fechaEmision;

    @Column(name = "subtotal", precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "igv_total", precision = 10, scale = 2)
    private BigDecimal igvTotal;

    @Column(name = "total", precision = 10, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_sunat")
    private EstadoSunat estadoSunat;


    public enum EstadoSunat {
        PENDIENTE,
        ENVIADO,
        RECHAZADO
    }
}
