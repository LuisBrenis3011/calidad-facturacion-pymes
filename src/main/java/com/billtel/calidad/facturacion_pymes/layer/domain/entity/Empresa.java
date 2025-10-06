package com.billtel.calidad.facturacion_pymes.layer.domain.entity;

import com.billtel.calidad.facturacion_pymes.Productos.entities.Producto;
import com.billtel.calidad.facturacion_pymes.Usuarios.entities.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empresa")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnoreProperties({"empresas", "hibernateLazyInitializer", "handler"})
    private Usuario usuario;

    @Column(name = "ruc", nullable = false, length = 11, unique = true)
    private String ruc;

    @Column(name = "razon_social", nullable = false, length = 150)
    private String razonSocial;

    @Column(name = "direccion", length = 200)
    private String direccion;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "logo", length = 255)
    private String logo;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Producto> productos;
}