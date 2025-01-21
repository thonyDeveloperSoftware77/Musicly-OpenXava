package com.yourcompany.musicly.model;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity
@View(members = "nombre; fechaLanzamiento; artistas; canciones")
@Getter @Setter
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "El nombre del álbum no puede estar vacío.")
    private String nombre;

    @Temporal(TemporalType.DATE)
    @PastOrPresent(message = "La fecha de lanzamiento no puede ser futura.")
    private Date fechaLanzamiento;

    @ManyToMany
    @JoinTable(
            name = "album_artista",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "artista_id")
    )
    @NotEmpty(message = "Debe haber al menos un artista asociado al álbum.")
    private List<Artista> artistas = new ArrayList<>();

    @OneToMany(mappedBy = "album")
    private List<Cancion> canciones;

    // Validaciones personalizadas
    @PrePersist
    @PreUpdate
    private void validarCampos() {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del álbum no puede estar vacío.");
        }

        if (fechaLanzamiento == null) {
            throw new IllegalArgumentException("La fecha de lanzamiento es obligatoria.");
        }

        if (fechaLanzamiento.after(new Date())) {
            throw new IllegalArgumentException("La fecha de lanzamiento no puede ser futura.");
        }

        if (artistas == null || artistas.isEmpty()) {
            throw new IllegalArgumentException("Debe haber al menos un artista asociado al álbum.");
        }
    }
}
