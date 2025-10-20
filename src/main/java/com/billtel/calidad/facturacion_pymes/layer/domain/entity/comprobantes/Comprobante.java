package com.billtel.calidad.facturacion_pymes.layer.domain.entity.comprobantes;

import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comprobante")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_comprobante")
public abstract class Comprobante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comprobante")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false)
    @JsonIgnoreProperties({"productos", "usuario", "hibernateLazyInitializer", "handler"})
    private Empresa empresa;

    @Column(name = "nro_doc_cliente", length = 20)
    private String nroDocCliente;

    @Column(name = "nombre_cliente", length = 200)
    private String nombreCliente;

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

    @OneToMany(mappedBy = "comprobante", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<DetalleComprobante> detalles = new ArrayList<>();

    public enum EstadoSunat {
        PENDIENTE,
        ENVIADO,
        RECHAZADO
    }
}
