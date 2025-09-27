package com.billtel.calidad.facturacion_pymes.Productos.entities;

import com.billtel.calidad.facturacion_pymes.Empresas.entities.Empresa;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "valor_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorUnitario;

    @Column(name = "igv", nullable = false, precision = 10, scale = 2)
    private BigDecimal igv;

    @Column(name = "unidad_medida", length = 20)
    private String unidadMedida;
} 
