package com.yourcompany.musicly.model;

import java.util.*;
import java.util.stream.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.yourcompany.musicly.calculadores.CalculadorReproduccionesIniciales;
import org.openxava.annotations.*;

import lombok.*;

@Entity
@View(members = "titulo; duracion; genero; album; artistas; reproducciones; popularidad")
@Getter @Setter
public class Cancion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "El título no puede estar vacío.")
    private String titulo;

    @Column(nullable = false)
    @Min(value = 1, message = "La duración debe ser al menos 1 minuto.")
    private float duracion; // Duración en minutos con decimales representando segundos (ej. 60.00)

    @ManyToOne(optional = false)
    private Genero genero;

    @ManyToOne(optional = false)
    private Album album;

    @ManyToMany
    private List<Artista> artistas = new ArrayList<>();

    @DefaultValueCalculator(CalculadorReproduccionesIniciales.class)
    @Column(nullable = false)
    private int reproducciones; // Número de reproducciones

    @ReadOnly
    @Calculation("reproducciones")
    private int popularidad; // Popularidad basada en reproducciones

    @ManyToMany(mappedBy = "canciones")
    private List<Playlist> playlists = new ArrayList<>();

    @PreRemove
    private void preRemove() {
        for (Playlist playlist : new ArrayList<>(playlists)) {
            playlist.getCanciones().remove(this);
        }
    }


    // ✅ Validación personalizada para la duración
    @PrePersist
    @PreUpdate
    private void validarDuracion() {
        if (duracion <= 0) {
            throw new IllegalArgumentException("La duración debe ser un valor positivo mayor a 0.");
        }

        if (duracion > 60.00) {
            throw new IllegalArgumentException("La duración no puede ser mayor a 60 minutos.");
        }

        // Validar que los segundos no excedan los 59
        int minutos = (int) Math.floor(duracion);
        float segundos = (duracion - minutos) * 100;

        if (segundos >= 60) {
            throw new IllegalArgumentException("Los segundos no pueden exceder los 59.");
        }
    }


}
