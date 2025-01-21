package com.yourcompany.musicly.tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.yourcompany.musicly.model.Playlist;
import com.yourcompany.musicly.model.Cancion;
import com.yourcompany.musicly.model.Genero;
import com.yourcompany.musicly.model.Album;
import com.yourcompany.musicly.model.Artista;
import com.yourcompany.musicly.model.Role;
import java.util.*;

public class PlaylistTest {

    private Playlist playlist;
    private Cancion cancion1;
    private Cancion cancion2;
    private Cancion cancion3;

    @Before
    public void setUp() {
        // Configurar género
        Genero genero = new Genero();
        genero.setId(1L);
        genero.setDescripcion("Pop");

        // Configurar artista
        Artista artista = new Artista();
        artista.setId(1L);
        artista.setNombre("Artista Pop");
        artista.setBiografia("Biografía del Artista Pop");
        artista.setRoles(Arrays.asList(new Role())); // Asumiendo que se requiere al menos un rol

        // Configurar álbum
        Album album = new Album();
        album.setId(1L);
        album.setNombre("Álbum Pop");
        album.setFechaLanzamiento(new Date());
        album.setCanciones(new ArrayList<>()); // Inicializar la lista de canciones

        // Configurar canciones con duraciones en minutos (decimal)
        cancion1 = new Cancion();
        cancion1.setId(1L);
        cancion1.setTitulo("Pop Song 1");
        cancion1.setDuracion(3.30f); // 3:30 minutos
        cancion1.setGenero(genero);
        cancion1.setAlbum(album);
        cancion1.setArtistas(Arrays.asList(artista));
        cancion1.setReproducciones(500);

        cancion2 = new Cancion();
        cancion2.setId(2L);
        cancion2.setTitulo("Pop Song 2");
        cancion2.setDuracion(3.20f); // 3:20 minutos
        cancion2.setGenero(genero);
        cancion2.setAlbum(album);
        cancion2.setArtistas(Arrays.asList(artista));
        cancion2.setReproducciones(800);

        cancion3 = new Cancion();
        cancion3.setId(3L);
        cancion3.setTitulo("Pop Song 3");
        cancion3.setDuracion(3.00f); // 3:00 minutos
        cancion3.setGenero(genero);
        cancion3.setAlbum(album);
        cancion3.setArtistas(Arrays.asList(artista));
        cancion3.setReproducciones(700);

        // Añadir canciones al álbum
        album.getCanciones().addAll(Arrays.asList(cancion1, cancion2, cancion3));

        // Configurar playlist
        playlist = new Playlist();
        playlist.setId(1L);
        playlist.setNombre("Playlist Pop");
        playlist.setCanciones(Arrays.asList(cancion1, cancion2, cancion3));
    }

    @Test
    public void testCalculoPopularidadPlaylist() {
        // Calcular la popularidad esperada
        int popularidadEsperada = cancion1.getReproducciones() + cancion2.getReproducciones() + cancion3.getReproducciones();

        // Obtener la popularidad calculada por la playlist
        int popularidadCalculada = playlist.getTotalPopularidad();

        // Verificar que la popularidad calculada coincide con la esperada
        assertEquals("La popularidad calculada de la playlist no coincide con la esperada.",
                popularidadEsperada,
                popularidadCalculada);
    }

    @Test
    public void testDuracionTotalEnSegundos() {
        // Duraciones individuales en segundos
        int duracion1 = 3 * 60 + 30; // 3:30 -> 210 segundos
        int duracion2 = 3 * 60 + 20; // 3:20 -> 200 segundos
        int duracion3 = 3 * 60 + 0;  // 3:00 -> 180 segundos

        // Duración total esperada
        int duracionTotalEsperada = duracion1 + duracion2 + duracion3; // 590 segundos

        // Obtener la duración total calculada por la playlist
        int duracionTotalCalculada = playlist.getDuracionTotalEnSegundos();

        // Verificar que la duración total calculada coincide con la esperada
        assertEquals("La duración total calculada de la playlist no coincide con la esperada.",
                duracionTotalEsperada,
                duracionTotalCalculada);
    }

    @Test
    public void testDuracionTotalFormateada() {
        // Duraciones individuales en segundos
        int duracion1 = 3 * 60 + 30; // 3:30 -> 210 segundos
        int duracion2 = 3 * 60 + 20; // 3:20 -> 200 segundos
        int duracion3 = 3 * 60 + 0;  // 3:00 -> 180 segundos

        // Duración total en segundos
        int duracionTotalSegundos = duracion1 + duracion2 + duracion3; // 590 segundos

        // Convertir la duración total a formato HH:MM:SS
        int horas = duracionTotalSegundos / 3600;
        int minutos = (duracionTotalSegundos % 3600) / 60;
        int segundos = duracionTotalSegundos % 60;
        String duracionFormateadaEsperada = String.format("%d:%02d:%02d", horas, minutos, segundos); // "0:09:50"

        // Obtener la duración total formateada calculada por la playlist
        String duracionFormateadaCalculada = playlist.getDuracionTotalFormateada();

        // Verificar que la duración formateada calculada coincide con la esperada
        assertEquals("La duración total formateada de la playlist no coincide con la esperada.",
                duracionFormateadaEsperada,
                duracionFormateadaCalculada);
    }

    @Test
    public void testPopulation() {
        // Calcular la popularidad esperada
        int popularidadEsperada = cancion1.getReproducciones() + cancion2.getReproducciones() + cancion3.getReproducciones();

        // Crear la cadena de población esperada
        String populationEsperada = "Population: " + popularidadEsperada;

        // Obtener la población calculada por la playlist
        String populationCalculada = playlist.getPopulation();

        // Verificar que la población calculada coincide con la esperada
        assertEquals("La población calculada de la playlist no coincide con la esperada.",
                populationEsperada,
                populationCalculada);
    }
}
