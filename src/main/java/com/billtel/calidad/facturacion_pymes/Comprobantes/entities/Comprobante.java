package com.billtel.calidad.facturacion_pymes.Comprobantes.entities;

import com.billtel.calidad.facturacion_pymes.DetalleComprobante.entities.DetalleComprobante;
import com.billtel.calidad.facturacion_pymes.Empresas.entities.Empresa;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "comprobante")
public class Comprobante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comprobante")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @Column(name = "nro_doc_cliente", nullable = false, length = 20)
    private String nroDocCliente;

    @Column(name = "direccion_cliente", length = 200)
    private String direccionCliente;

    @Column(name = "serie", nullable = false, length = 4)
    private String serie;

    @Column(name = "correlativo", nullable = false)
    private Integer correlativo;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDateTime fechaEmision;

    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "igv_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal igvTotal;

    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_sunat", length = 10)
    private EstadoSunat estadoSunat = EstadoSunat.PENDIENTE;

    @OneToMany(mappedBy = "comprobante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleComprobante> detalles;

    public enum EstadoSunat {
        PENDIENTE,
        ENVIADO,
        ACEPTADO,
        RECHAZADO
    }

}
