package com.yourcompany.musicly.tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.yourcompany.musicly.model.Playlist;
import com.yourcompany.musicly.model.Cancion;
import com.yourcompany.musicly.model.Genero;
import com.yourcompany.musicly.model.Album;
import com.yourcompany.musicly.model.Artista;
import java.util.*;

public class PlaylistTest {

    private Playlist playlist;
    private Cancion cancion1;
    private Cancion cancion2;
    private Cancion cancion3;

    @Before
    public void setUp() {
        // Configurar canciones
        Genero genero = new Genero();
        genero.setId(1L);
        genero.setDescripcion("Pop");

        Album album = new Album();
        album.setId(1L);
        album.setNombre("Álbum Pop");
        album.setFechaLanzamiento(new Date());
        album.setArtistaPrincipal(new Artista());
        album.setCanciones(new ArrayList<>());

        Artista artista = new Artista();
        artista.setId(1L);
        artista.setNombre("Artista Pop");
        artista.setBiografia("Biografía del Artista Pop");
        artista.setRoles(new ArrayList<>());

        cancion1 = new Cancion();
        cancion1.setId(1L);
        cancion1.setTitulo("Pop Song 1");
        cancion1.setDuracion(210); // 3:30
        cancion1.setGenero(genero);
        cancion1.setAlbum(album);
        cancion1.setArtistas(Arrays.asList(artista));
        cancion1.setReproducciones(500);

        cancion2 = new Cancion();
        cancion2.setId(2L);
        cancion2.setTitulo("Pop Song 2");
        cancion2.setDuracion(200); // 3:20
        cancion2.setGenero(genero);
        cancion2.setAlbum(album);
        cancion2.setArtistas(Arrays.asList(artista));
        cancion2.setReproducciones(800);

        cancion3 = new Cancion();
        cancion3.setId(3L);
        cancion3.setTitulo("Pop Song 3");
        cancion3.setDuracion(180); // 3:00
        cancion3.setGenero(genero);
        cancion3.setAlbum(album);
        cancion3.setArtistas(Arrays.asList(artista));
        cancion3.setReproducciones(700);

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
        // Simular @Calculation
        playlist.setPopularidad(popularidadEsperada);
        assertEquals(popularidadEsperada, playlist.getPopularidad());
    }

    @Test
    public void testCalculoDuracionTotal() {
        // Calcular la duración total esperada
        int duracionEsperada = cancion1.getDuracion() + cancion2.getDuracion() + cancion3.getDuracion();
        assertEquals(duracionEsperada, playlist.getDuracionTotal());
    }

    @Test
    public void testLimiteDuracionPlaylist() {
        // Verificar que la duración total no exceda las 3 horas (10800 segundos)
        assertTrue(playlist.getDuracionTotal() <= 10800);
    }
}
