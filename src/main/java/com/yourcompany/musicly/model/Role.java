package com.yourcompany.musicly.model;

import java.util.*;

import javax.persistence.*;

import lombok.*;
import org.openxava.annotations.Hidden;

@Entity
@Getter @Setter
public class Role {

    @Id
    @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String descripcion; // Ejemplo: "Vocalista", "Productor"

    @ManyToMany(mappedBy = "roles")
    private List<Artista> artistas = new ArrayList<>(); // Un rol puede pertenecer a varios artistas
}
