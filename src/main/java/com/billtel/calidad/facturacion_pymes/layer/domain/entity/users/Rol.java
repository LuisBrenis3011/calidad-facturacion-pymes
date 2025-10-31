package com.billtel.calidad.facturacion_pymes.layer.domain.entity.users;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "roles")
public class Rol {

    public Rol() {
    }

    public Rol(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long id;

    @Column(name = "nombre")
    private String name;

}
