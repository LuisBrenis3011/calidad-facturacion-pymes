package com.billtel.calidad.facturacion_pymes.Usuarios.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
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

    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    @ManyToMany
    @JoinTable(
            name = "usuarios_roles",
            joinColumns = {@JoinColumn(name = "id_usuario")},
            inverseJoinColumns = {@JoinColumn(name = "id_rol")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"id_usuario", "id_rol"})}

    )
    private List<Rol> roles;

    public boolean isAdmin() {
        return admin;
    }


    public Boolean isEnabled() {

        return enabled;
    }

    public void setEnabled(Boolean enabled) {

        this.enabled = enabled;
    }


}