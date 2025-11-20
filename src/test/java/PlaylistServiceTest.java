import dataPackage.PodcastData;
import dataPackage.SongData;
import musicItems.Track;
import musicService.PlaylistService;
import musicService.PodcastService;
import musicService.SongService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

//public class PlaylistServiceTest {
    public class PlaylistServiceTest
    {


        private PlaylistService playlistService;

        // creating instances of song and podcast services
        @Before
        public void setUp()
        {
            SongService songService = new SongService();
            PodcastService podcastService = new PodcastService();
            playlistService = new PlaylistService(songService, podcastService);
        }


        // to avoid resource leak
        @After
        public void tearDown()
        {
            playlistService = null;
        }


        // method to test for creating a new playlist
        @Test
        public void createPlaylistTest()
        {
            boolean result = playlistService.createPlaylist("Chill Vibes");
            assertTrue(result);

            boolean duplicate = playlistService.createPlaylist("Mix Up");                        // already exist in playlist sample class
            assertFalse(duplicate);
        }


        //  method to test for adding tracks (songs+podcast) into playlist
        @Test
        public void addTrackIntoPlaylistTest()
        {
            // A song - A Thousand Years
            Track song = SongData.sampleSongs().get(0);

            boolean songAdd = playlistService.addTrackIntoPlaylist("fav songs", song);
            assertTrue(songAdd);

            // A podcast - how to reinvent your life ?
            Track podcast = PodcastData.samplePodcastData().get(0).getEpisodes().get(0);

            boolean podcastAdd = playlistService.addTrackIntoPlaylist("Mix Up",podcast);
            assertTrue(podcastAdd);

        }


        // method to test for removing tracks from the playlist
        @Test
        public void removeTrackFromPlaylistTest()
        {
            List<Track> favSongsTracks = playlistService.viewPlaylistContent("fav songs");
            Track track = favSongsTracks.get(0);         // A Thousand Years

            boolean remove = playlistService.removeTrackFromPlaylist("fav songs", track);
            assertTrue(remove);

        }


        // method to test for removing the whole playlist
        @Test
        public void removePlaylistTest()
        {
            boolean remove = playlistService.removePlaylist("fav songs");
            assertTrue(remove);

            boolean removeAgain = playlistService.removePlaylist("fav songs");
            assertFalse(removeAgain);
        }


        // method to test for searching playlist by its name
        @Test
        public void searchPlaylistTest()
        {
            List<String> result = playlistService.searchPlaylist("mix");

            assertEquals(1, result.size());
            assertEquals("mix up", result.get(0));
        }


        // method to test for get all available playlist name
        @Test
        public void getPlaylistsNameTest()
        {
            List<String> names = playlistService.getPlaylistsName();

            assertNotNull(names);
            assertTrue(names.contains("fav songs"));
            assertTrue(names.contains("sharp mind"));
            assertTrue(names.contains("mix up"));
        }


        // method to test for view a specific playlist content
        @Test
        public void viewPlaylistContentTest()
        {
            List<Track> tracks = playlistService.viewPlaylistContent("fav songs");

            assertNotNull(tracks);
            assertFalse(tracks.isEmpty());
            assertEquals(6,tracks.size());                       // fav songs contains 6 songs acc. to playlist data class
        }


        // method to test for getting list of available genres/albums/artist of songs
        @Test
        public void getAvailableSongCategoryTest()
        {
            List<String> genres = playlistService.getAvailableSongCategory("genre");
            assertNotNull(genres);

            List<String> albums = playlistService.getAvailableSongCategory("album");
            assertNotNull(albums);

            List<String> artists = playlistService.getAvailableSongCategory("artist");
            assertNotNull(artists);

        }


        // method to test for getting all songs based on specific category - genre
        @Test
        public void getSongsBySpecificCategoryTest()
        {
            String genre = SongData.sampleSongs().get(0).getGenre();
            List<Track> tracksByGenre = playlistService.getAllSongsBySpecificCategory("genre", genre);

            assertNotNull(tracksByGenre);
            assertFalse(tracksByGenre.isEmpty());

        }


        // method to test for getting list of available genres/celebrity of podcast
        @Test
        public void getAvailablePodcastCategoryTest()
        {
            List<String> genres = playlistService.getAvailablePodcastCategory("genre");
            assertNotNull(genres);

            List<String> celebrities = playlistService.getAvailablePodcastCategory("celebrity");
            assertNotNull(celebrities);

        }


        // method to test for getting all podcast based on specific category - genre / celebrity
        @Test
        public void getPodcastEpisodesBySpecificCategoryTest()
        {
            String genre = PodcastData.samplePodcastData().get(0).getGenre();

            List<Track> episodes = playlistService.getAllPodcastEpisodeBySpecificCategory("genre", "genre");
            assertNotNull(episodes);

            String celebrity = PodcastData.samplePodcastData().get(0).getCelebrity();
            List<Track> episodesByCelebrity = playlistService.getAllPodcastEpisodeBySpecificCategory("celebrity", "celebrity");
            assertNotNull(episodesByCelebrity);

        }


    }

