package com.billtel.calidad.facturacion_pymes.layer.domain.entity.users;
import com.billtel.calidad.facturacion_pymes.layer.domain.entity.Empresa;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import java.util.List;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    private String password;

    private Boolean enabled;

    @Transient
    private boolean admin;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("usuario")
    List<Empresa> empresas;

    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    @ManyToMany
    @JoinTable(
            name = "usuarios_roles",
            joinColumns = {@JoinColumn(name = "id_usuario")},
            inverseJoinColumns = {@JoinColumn(name = "id_rol")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"id_usuario", "id_rol"})}

    )
    private List<Rol> roles;

    public Boolean isEnabled() {

        return enabled;
    }

    public boolean isAdmin() {
        return admin;
    }

}