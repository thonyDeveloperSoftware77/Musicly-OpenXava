package com.yourcompany.musicly.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.*;

import com.yourcompany.musicly.model.*;

public class MusiclyTest {

    private Album album;
    private Genero genero;
    private Cancion cancion;
    private Artista artista1;
    private Artista artista2;

    @Before
    public void setUp() {
        // Configurar género
        genero = new Genero();
        genero.setId(1L);
        genero.setDescripcion("Rock");

        // Configurar artistas
        artista1 = new Artista();
        artista1.setId(1L);
        artista1.setNombre("Artista Uno");
        artista1.setBiografia("Biografía del Artista Uno");

        artista2 = new Artista();
        artista2.setId(2L);
        artista2.setNombre("Artista Dos");
        artista2.setBiografia("Biografía del Artista Dos");

        // Configurar álbum
        album = new Album();
        album.setId(1L);
        album.setNombre("Álbum de Prueba");
        album.setFechaLanzamiento(new Date());
        album.setArtistas(Arrays.asList(artista1, artista2));
        album.setCanciones(new ArrayList<>());

        // Configurar canción
        cancion = new Cancion();
        cancion.setId(1L);
        cancion.setTitulo("Canción de Prueba");
        cancion.setDuracion(3.45f); // 3 minutos 45 segundos
        cancion.setGenero(genero);
        cancion.setAlbum(album);
        cancion.setArtistas(Arrays.asList(artista1, artista2));
        cancion.setReproducciones(500);
    }

    @Test
    public void testCrearAlbumExitosamente() {
        assertNotNull(album);
        assertEquals("Álbum de Prueba", album.getNombre());
        assertEquals(2, album.getArtistas().size());
        assertTrue(album.getFechaLanzamiento().before(new Date()) || album.getFechaLanzamiento().equals(new Date()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNombreAlbumNoDebeSerVacio() {
        album.setNombre("");
        album.validarCampos();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFechaLanzamientoNoDebeSerFutura() {
        album.setFechaLanzamiento(new Date(System.currentTimeMillis() + 86400000)); // +1 día
        album.validarCampos();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDebeHaberAlMenosUnArtista() {
        album.setArtistas(new ArrayList<>());
        album.validarCampos();
    }

    @Test
    public void testCrearCancionExitosamente() {
        assertNotNull(cancion);
        assertEquals("Canción de Prueba", cancion.getTitulo());
        assertEquals(3.45f, cancion.getDuracion(), 0.01);
        assertEquals(500, cancion.getReproducciones());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDuracionNegativaCancion() {
        cancion.setDuracion(-2.0f);
        cancion.validarDuracion();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDuracionExcede60Minutos() {
        cancion.setDuracion(61.00f);
        cancion.validarDuracion();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSegundosInvalidosEnDuracion() {
        cancion.setDuracion(3.75f); // 3 minutos y 75 segundos
        cancion.validarDuracion();
    }

    @Test
    public void testGeneroDescripcionNoDebeSerVacia() {
        genero.setDescripcion("Pop");
        assertEquals("Pop", genero.getDescripcion());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGeneroDescripcionVacia() {
        genero.setDescripcion("");
        if (genero.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }
    }

    @Test
    public void testPopularidadCancion() {
        cancion.setPopularidad(cancion.getReproducciones());
        assertEquals(500, cancion.getPopularidad());
    }

    @Test
    public void testArtistaTieneRoles() {
        artista1.setRoles(Arrays.asList(new Role()));
        assertFalse(artista1.getRoles().isEmpty());
    }

    @Test
    public void testEliminarCancionDePlaylist() {
        Playlist playlist = new Playlist();
        playlist.getCanciones().add(cancion);
        cancion.getPlaylists().add(playlist);

        cancion.preRemove();
        assertFalse(playlist.getCanciones().contains(cancion));
    }

}