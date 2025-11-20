package dataPackage;

import musicItems.Playlist;
import musicItems.Song;
import musicItems.Podcast;
import musicItems.Track;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PlaylistData
{

    public static Map<String, Playlist> samplePlaylistData()
    {

        List<Song> allSongsList = SongData.sampleSongs();
        List<Podcast> allPodcastsList = PodcastData.samplePodcastData();

        Map<String,Playlist> samplePlaylist = new HashMap<>();

        // Playlist 1 - Sharp Mind
        List<Track> sharpMindTrack = new ArrayList<>();
        sharpMindTrack.add(allPodcastsList.get(1).getEpisodes().get(1));        //The Consumer Psychology of Adopting AI
        sharpMindTrack.add(allPodcastsList.get(2).getEpisodes().get(2));        //How smartphones are rewiring our brains ?
        sharpMindTrack.add(allPodcastsList.get(2).getEpisodes().get(1));        //How to future proof your brain ?
        sharpMindTrack.add(allPodcastsList.get(1).getEpisodes().get(0));        //Unexpectedly Out of a Job? Here's How to Bounce Back

        Playlist playlist1 = new Playlist("Sharp Mind");
        playlist1.setTracks(sharpMindTrack);
        samplePlaylist.put(playlist1.getName().toLowerCase(),playlist1);




        // Playlist 2 - Fav Songs
        List<Track> favSongsTrack = new ArrayList<>();
        favSongsTrack.add(allSongsList.get(3));                //Blank Space
        favSongsTrack.add(allSongsList.get(6));                //Cruel Summer
        favSongsTrack.add(allSongsList.get(1));                // Aaj Ki Raat
        favSongsTrack.add(allSongsList.get(8));               //Die With A Smile
        favSongsTrack.add(allSongsList.get(9));               //Hall of Fames
        favSongsTrack.add(allSongsList.get(4));               //Bulleya

        Playlist playlist2 = new Playlist("Fav Songs");
        playlist2.setTracks(favSongsTrack);
        samplePlaylist.put(playlist2.getName().toLowerCase(),playlist2);




        // Playlist 3 - Mix Up
        List<Track> mixUpTrack = new ArrayList<>();
        mixUpTrack.add(allSongsList.get(9));                                  //Hall of Fames
        mixUpTrack.add(allPodcastsList.get(0).getEpisodes().get(1));          //Mr. Mendoza's Paintbrush
        mixUpTrack.add(allSongsList.get(14));                                 //Who says
        mixUpTrack.add(allPodcastsList.get(0).getEpisodes().get(2));          // Quarraopts can't dance
        mixUpTrack.add(allSongsList.get(13));                                 //Try Everything

        Playlist playlist3 = new Playlist("Mix Up");
        playlist3.setTracks(mixUpTrack);
        samplePlaylist.put(playlist3.getName().toLowerCase(),playlist3);




        return samplePlaylist;



    }


}
