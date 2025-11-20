package test;

import dataPackage.PodcastData;
import musicItems.Podcast;
import musicItems.PodcastEpisode;
import musicService.PodcastService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

//public class PodcastServiceTest {
    public  class PodcastServiceTest
    {


        private PodcastService podcastService;


        // Getting podcast data from already stored sample for podcast
        @Before
        public void setUp()
        {
            podcastService = new PodcastService();

            List<Podcast> podcastList = PodcastData.samplePodcastData();

            for (Podcast podcast : podcastList)
            {
                podcastService.addPodcast(podcast);
            }

        }


        // to avoid resource leak
        @After
        public void tearDown()
        {
            podcastService = null;
        }


        // method to test for adding podcast
        @Test
        public void addPodcastTest()
        {
            Podcast podcast = new Podcast("Wisdom Bytes", "Jay Shetty", "Motivation", "2025-15-18", "not available", new ArrayList<>());
            List<Podcast> updatedList = podcastService.addPodcast(podcast);

            assertTrue(updatedList.contains(podcast));
        }


        // method to test for adding new episode in existing podcast
        @Test
        public void addEpisodeTest()
        {
            Podcast podcast = podcastService.searchPodcastByCompleteTitle("Feel Better,Live More");
            PodcastEpisode episode = new PodcastEpisode("EP-New", "Growth Mindset", "30:00", "not available");
            List<PodcastEpisode> episodeList = podcastService.addEpisode(podcast, episode);

            assertTrue(episodeList.contains(episode));
        }


        // method to test for removing existing podcast
        @Test
        public void removeExistentPodcastTest()
        {
            Podcast podcast = podcastService.searchPodcastByCompleteTitle("Mind Talks");
            List<Podcast> updatedList = podcastService.removePodcast(podcast);

            assertFalse(updatedList.contains(podcast));
        }


        // method to test for removing non - existing podcast
        @Test
        public void removeNonExistentPodcastTest()
        {
            Podcast nonExistentPodcast = new Podcast("New Podcast", "Nobody", "Genre", "2025-05-18","not available",new ArrayList<>());
            List<Podcast> updatedList = podcastService.removePodcast(nonExistentPodcast);

            assertFalse(updatedList.contains(nonExistentPodcast));
            assertEquals(PodcastData.samplePodcastData().size(),updatedList.size());
        }


        // method to test for removing existing episode
        @Test
        public void removeExistentPodcastEpisodeTest()
        {
            Podcast podcast = podcastService.searchPodcastByCompleteTitle("Feel Better,Live More");
            PodcastEpisode episode = podcastService.searchPodcastEpisode(podcast, "The bitter truth about sugar");
            boolean result = podcastService.removePodcastEpisode(podcast, episode);

            assertTrue(result);
        }


        // method to test for removing non - existing episode
        @Test
        public void removeNonExistentPodcastEpisodeTest()
        {
            Podcast podcast = podcastService.searchPodcastByCompleteTitle("Feel Better,live more");
            PodcastEpisode nonExistentEpisode = new PodcastEpisode("NewEp", "not available", "10:00",  "not available");
            boolean result = podcastService.removePodcastEpisode(podcast, nonExistentEpisode);

            assertFalse(result);
        }


        // method to test for searching existing podcast by its full title
        @Test
        public void searchPodcastByCompleteTitleTest()
        {
            Podcast podcast = podcastService.searchPodcastByCompleteTitle("Feel Better,Live More");

            assertNotNull(podcast);
            assertEquals("Feel Better,Live More", podcast.getTitle());
        }


        // method to test for searching existing podcast episode
        @Test
        public void testSearchPodcastEpisode()
        {
            Podcast podcast = podcastService.searchPodcastByCompleteTitle("Feel Better,Live More");
            PodcastEpisode episode = podcastService.searchPodcastEpisode(podcast, "how to reinvent your life ?");

            assertNotNull(episode);
            assertEquals("How to reinvent your life ?", episode.getEpisodeTitle());
        }


        // method to test for searching non - existing podcast episode
        @Test
        public void testSearchNonExistentEpisode()
        {
            Podcast podcast = podcastService.searchPodcastByCompleteTitle("Feel Better,Live More");
            PodcastEpisode result = podcastService.searchPodcastEpisode(podcast, "What is Life ?");

            assertNull(result);
        }


        // method to test for searching non - existing podcast
        @Test
        public void testSearchNonExistentPodcast()
        {
            Podcast podcast = podcastService.searchPodcastByCompleteTitle("New Podcast");

            assertNull(podcast);
        }


        // method to test for searching existing podcast by its title
        @Test
        public void searchPodcastByTitleCategoryPassTest()
        {
            List<Podcast> result = podcastService.searchPodcastByCategory("title", "Feel Better");

            assertFalse(result.isEmpty());
            assertTrue(result.stream().anyMatch(podcast -> podcast.getTitle().contains("Feel Better,Live More")));

        }


        // method to test for searching existing podcast by its celebrity
        @Test
        public void searchPodcastByCelebrityCategoryPassTest()
        {
            List<Podcast> result = podcastService.searchPodcastByCategory("celebrity", "Alison");

            assertFalse(result.isEmpty());
            assertTrue(result.stream().anyMatch(podcast -> podcast.getCelebrity().contains("Alison Beard")));

        }


        // method to test for searching existing podcast by its genre
        @Test
        public void searchPodcastByGenreCategoryPassTest()
        {
            List<Podcast> result = podcastService.searchPodcastByCategory("genre", "heal");

            assertFalse(result.isEmpty());
            assertTrue(result.stream().anyMatch(podcast -> podcast.getGenre().contains("Health")));
        }


        // method to test the expected size of the sample data for podcast
        // method to test the total episode of sample of first Podcast
        @Test
        public void viewPodcastTest()
        {
            List<Podcast> result = podcastService.viewPodcast();

            assertNotNull(result);
            assertEquals(3,result.size());                                                   // store 3 podcast data
            assertEquals(5,result.get(0).getEpisodes().size());                              // 1 podcast stores total 5 episodes
        }




    }

