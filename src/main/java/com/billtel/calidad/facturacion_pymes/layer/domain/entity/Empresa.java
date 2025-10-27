package com.billtel.calidad.facturacion_pymes.layer.domain.entity;

import com.billtel.calidad.facturacion_pymes.layer.domain.entity.users.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
    @NotBlank(message = "El RUC es obligatorio")
    private String ruc;

    @Column(name = "razon_social", nullable = false, length = 150)
    private String razonSocial;

    @Column(name = "direccion", length = 200)
    private String direccion;

    @Column(name = "email", length = 100)
    @Email(message = "El email debe ser valido")
    private String email;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "logo", length = 255)
    private String logo;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Producto> productos;

}