package com.billtel.calidad.facturacion_pymes.layer.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false)
    @JsonBackReference
    private Empresa empresa;

    @Column(name = "nombre", length = 100)
    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "valor_unitario", precision = 10, scale = 2)
    @NotNull(message = "El valor unitario es obligatorio")
    private BigDecimal valorUnitario;

    @Column(name = "igv", precision = 10, scale = 2)
    private BigDecimal igv;

    @Column(name = "unidad_medida", length = 20)
    @NotBlank
    private String unidadMedida;

}
