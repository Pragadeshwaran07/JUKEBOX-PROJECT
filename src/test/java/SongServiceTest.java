package test;

import dataPackage.SongData;
import musicItems.Song;
import musicService.SongService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SongServiceTest {

    private SongService songService;

    @Before
    public void setUp() {
        songService = new SongService();
        // Pre-load sample data
        for (Song song : SongData.sampleSongs()) {
            songService.addSong(song);
        }
    }

    @After
    public void tearDown() {
        songService = null;
    }

    @Test
    public void addSongTest() {
        Song song = new Song("Attention", "Attention", "Charlie Puth", "Pop", "03:51", "2025-05-18", "not available");
        List<Song> updatedList = songService.addSong(song);
        assertTrue(updatedList.contains(song));
    }

    @Test
    public void addNullSongTest() {
        try {
            songService.addSong(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Song cannot be null !", e.getMessage());
        }
    }

    @Test
    public void addDuplicateSongTest() {
        Song duplicateSong = SongData.sampleSongs().get(0);
        List<Song> updatedList = songService.addSong(duplicateSong);
        long count = updatedList.stream().filter(song -> song.equals(duplicateSong)).count();
        assertEquals(1, count);
        assertTrue(updatedList.contains(duplicateSong));
    }

    @Test
    public void removeExistingSongTest() {
        Song testSong = SongData.sampleSongs().get(0);
        songService.addSong(testSong);
        List<Song> updatedList = songService.removeSong(testSong);
        assertFalse(updatedList.contains(testSong));
    }

    @Test
    public void removeNonExistentSongTest() {
        Song nonExistentSong = new Song("Tum Ho", "Rockstar", "Arijit Singh", "Pop", "03:00", "2025-05-18", "not available");
        List<Song> updatedList = songService.removeSong(nonExistentSong);
        assertFalse(updatedList.contains(nonExistentSong));
        assertEquals(SongData.sampleSongs().size(), updatedList.size());
    }

    @Test
    public void searchSongByCompleteTitlePassTest() {
        Song result = songService.searchSongByCompleteTitle("Vaseegara");
        assertNotNull(result);
        assertEquals("Vaseegara", result.getTitle());
    }

    @Test
    public void searchSongByCompleteTitleFailTest() {
        Song result = songService.searchSongByCompleteTitle("Tere Liye");
        assertNull(result);
    }

    @Test
    public void searchSongByTitleCategoryPassTest() {
        List<Song> result = songService.searchSongByCategory("title", "Vaseegara");
        assertFalse(result.isEmpty());
        assertTrue(result.get(0).getTitle().contains("Vaseegara"));
    }

    @Test
    public void searchSongByArtistCategoryPassTest() {
        List<Song> result = songService.searchSongByCategory("artist", "Sid Sriram");
        assertFalse(result.isEmpty());
        assertTrue(result.stream().anyMatch(song -> song.getArtist().contains("Sid Sriram")));
    }

    @Test
    public void searchSongByGenreCategoryPassTest() {
        List<Song> result = songService.searchSongByCategory("genre", "Tamil");
        assertFalse(result.isEmpty());
        assertTrue(result.stream().allMatch(song -> song.getGenre().equals("Tamil")));
    }

    @Test
    public void searchSongByAlbumCategoryPassTest() {
        List<Song> result = songService.searchSongByCategory("album", "Master");
        assertFalse(result.isEmpty());
        assertTrue(result.stream().anyMatch(song -> song.getAlbum().equals("Master")));
    }

    @Test
    public void viewSongTest() {
        List<Song> songTestView = songService.viewSong();
        assertNotNull(songTestView);
        assertEquals(15, songTestView.size()); // Your sampleSongs() returns 15 songs
    }
}


