package com.yourcompany.musicly.model;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import org.openxava.annotations.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import lombok.*;
@Entity
@View(members = "nombre; canciones; duracionTotalFormateada; population")
@Getter @Setter
public class Playlist {

    private static final Log log = LogFactory.getLog(Playlist.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 100, nullable = false)
    @Required
    @NotBlank(message = "El nombre de la playlist no puede estar vacío.")
    String nombre;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "playlist_cancion",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "cancion_id")
    )
    List<Cancion> canciones = new ArrayList<>();


    @ReadOnly
    @Calculation("sum(canciones.reproducciones)")
    int popularidad;



    /**
     * Método para obtener la duración total en segundos.
     * Realiza el cálculo basado en la duración de cada canción y asegura que los segundos no excedan 59.
     */
    public int getDuracionTotalEnSegundos() {
        int total = 0;
        for (Cancion cancion : canciones) {
            float duracion = cancion.getDuracion();
            int minutos = (int) Math.floor(duracion);
            int segundos = Math.round((duracion - minutos) * 100);
            log.info("Canción: " + cancion.getTitulo() + ", Duración: " + duracion + " minutos (" + minutos + "m " + segundos + "s)");

            // Validación adicional para asegurar que los segundos no excedan 59
            if (segundos >= 60) {
                minutos += segundos / 60;
                segundos = segundos % 60;
                log.warn("Duración corregida: " + minutos + "m " + segundos + "s");
            }

            total += minutos * 60 + segundos;
        }
        log.info("Duración total en segundos calculada: " + total);
        return total;
    }

    public int getTotalPopularidad() {
        int popularidad = 0;
        for (Cancion cancion : canciones) {
            popularidad += cancion.getReproducciones();
        }
        return popularidad;
    }
    /**
     * Método auxiliar para obtener la duración total en formato HH:MM:SS.
     * Utiliza la duración total en segundos para formatear la cadena.
     */
    @Transient
    public String getDuracionTotalFormateada() {
        int totalSegundos = getDuracionTotalEnSegundos();
        int horas = totalSegundos / 3600;
        int minutos = (totalSegundos % 3600) / 60;
        int segundos = totalSegundos % 60;
        return String.format("%d:%02d:%02d", horas, minutos, segundos);
    }

    @Transient
    public String getPopulation() {
        return "Population: " + getTotalPopularidad();
    }


    // Constructor por defecto
    public Playlist() {
        this.canciones = new ArrayList<>();
    }
}
