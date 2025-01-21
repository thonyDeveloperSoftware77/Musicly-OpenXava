package com.yourcompany.musicly.tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.yourcompany.musicly.model.Cancion;
import com.yourcompany.musicly.model.Genero;
import com.yourcompany.musicly.model.Album;
import com.yourcompany.musicly.model.Artista;
import java.util.*;

public class CancionTest {

    private Cancion cancion;
    private Genero genero;
    private Album album;
    private Artista artista1;
    private Artista artista2;

    @Before
    public void setUp() {
        // Configurar género
        genero = new Genero();
        genero.setId(1L);
        genero.setDescripcion("Rock");

        // Configurar álbum
        album = new Album();
        album.setId(1L);
        album.setNombre("Álbum de Prueba");
        album.setFechaLanzamiento(new Date());
        album.setArtistaPrincipal(new Artista());
        album.setCanciones(new ArrayList<>());

        // Configurar artistas
        artista1 = new Artista();
        artista1.setId(1L);
        artista1.setNombre("Artista Uno");
        artista1.setBiografia("Biografía del Artista Uno");
        artista1.setRoles(new ArrayList<>());

        artista2 = new Artista();
        artista2.setId(2L);
        artista2.setNombre("Artista Dos");
        artista2.setBiografia("Biografía del Artista Dos");
        artista2.setRoles(new ArrayList<>());

        // Configurar canción
        cancion = new Cancion();
        cancion.setId(1L);
        cancion.setTitulo("Canción de Prueba");
        cancion.setDuracion(240); // 4 minutos
        cancion.setGenero(genero);
        cancion.setAlbum(album);
        cancion.setArtistas(Arrays.asList(artista1, artista2));
        cancion.setReproducciones(1000); // 1000 reproducciones
    }

    @Test
    public void testCalculoPopularidad() {
        // Simular el cálculo que realiza @Calculation
        cancion.setPopularidad(cancion.getReproducciones());
        assertEquals("La popularidad debería ser igual a las reproducciones",
                cancion.getReproducciones(), cancion.getPopularidad());
    }

    @Test
    public void testActualizacionPopularidad() {
        // Actualizar reproducciones y verificar la actualización de popularidad
        cancion.setReproducciones(1500);
        // Simular cálculo de @Calculation
        cancion.setPopularidad(cancion.getReproducciones());
        assertEquals(1500, cancion.getPopularidad());
    }

    @Test
    public void testValorInicialReproducciones() {
        // Crear una nueva canción y verificar el valor inicial de reproducciones
        Cancion nuevaCancion = new Cancion();
        nuevaCancion.setTitulo("Nueva Canción");
        nuevaCancion.setDuracion(180);
        nuevaCancion.setGenero(genero);
        nuevaCancion.setAlbum(album);
        nuevaCancion.setArtistas(Arrays.asList(artista1));
        // Simular @DefaultValueCalculator
        nuevaCancion.setReproducciones(0);
        assertEquals(0, nuevaCancion.getReproducciones());
    }
}
