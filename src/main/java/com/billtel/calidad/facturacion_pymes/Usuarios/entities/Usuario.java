package com.billtel.calidad.facturacion_pymes.Usuarios.entities;

import com.billtel.calidad.facturacion_pymes.Empresas.entities.Empresa;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "username", nullable = false, length = 50, unique = true)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false, length = 10)
    private Rol rol = Rol.VENDEDOR;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Empresa> empresas;

    public enum Rol {
        ADMIN,
        VENDEDOR
    }
}

