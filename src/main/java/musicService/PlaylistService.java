package musicService;

import musicItems.*;
import dataPackage.PlaylistData;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


// performs all services related to a playlist i.e. create, add, remove, view and play


public class PlaylistService  implements AudioPlaybackService
{


    private Map<String,Playlist> playlists;                                                                             // to access playlists fast by its name i.e. key - playlist name, value - playlist new
    private SongService songService ;
    private PodcastService podcastService ;
    private Track currentTrack;
    private AudioService audioService;



    // constructor
    public PlaylistService (SongService songService, PodcastService podcastService)
    {
        playlists = new HashMap<>(PlaylistData.samplePlaylistData());
        this.songService = songService;
        this.podcastService = podcastService;

    }



    // method to create new playlist
    public boolean createPlaylist (String name)
    {
        if(playlists.containsKey(name.toLowerCase()))
        {
            return false;
        }
        else
        {
            playlists.put(name.toLowerCase(),new Playlist(name));
            return true;
        }
    }



    // method to add track into specific Playlist
    public boolean addTrackIntoPlaylist (String playlistName, Track selectedTrack)
    {
        if(playlists.containsKey(playlistName.toLowerCase()))
        {
            Playlist selectedPlaylist = playlists.get(playlistName.toLowerCase());
            List<Track> tracks = selectedPlaylist.getTracks();

            if(!tracks.contains(selectedTrack))
            {
                tracks.add(selectedTrack);
                return true;
            }
        }
        return false;
    }



    // method to get all available category of genre, album, artist in songs
    public List<String> getAvailableSongCategory (String specificCategory)
    {
        switch (specificCategory)
        {
            case "genre": return songService.viewSong().stream()
                    .map(Song::getGenre)
                    .filter(value -> value != null)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());

            case "album": return songService.viewSong().stream()
                    .map(Song::getAlbum)
                    .filter(value -> value != null)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());

            case "artist": return songService.viewSong().stream()
                    .map(Song::getArtist)
                    .filter(value -> value != null)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());
            default: return Collections.emptyList();
        }
    }




    // helper method to get all songs available of a specific category - genre or album or artist
    public List<Track> getAllSongsBySpecificCategory (String specificCategory, String selectedCategory)
    {
        switch (specificCategory)
        {
            case "genre": return songService.viewSong().stream()
                    .filter(song ->song.getGenre().equalsIgnoreCase(selectedCategory))
                    .sorted(Comparator.comparing(Track::getTitle))
                    .collect(Collectors.toList());

            case "album": return songService.viewSong().stream()
                    .filter(song -> song.getAlbum().equalsIgnoreCase(selectedCategory))
                    .sorted(Comparator.comparing(Track::getTitle))
                    .collect(Collectors.toList());

            case "artist": return songService.viewSong().stream()
                    .filter(song -> song.getArtist().equalsIgnoreCase(selectedCategory))
                    .sorted(Comparator.comparing(Track::getTitle))
                    .collect(Collectors.toList());
            default: return Collections.emptyList();
        }
    }



    // method to get all available category of genre, celebrity in podcast
    public List<String> getAvailablePodcastCategory (String specificCategory)
    {
        switch (specificCategory)
        {
            case "genre": return podcastService.viewPodcast().stream()
                    .map(Podcast::getGenre)
                    .filter(value -> value != null)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());

            case "celebrity": return podcastService.viewPodcast().stream()
                    .map(Podcast::getCelebrity)
                    .filter(Objects::nonNull)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());
            default: return Collections.emptyList();
        }
    }



    // helper method to get all podcast episode available of a specific category - genre or album or artist
    public List<Track> getAllPodcastEpisodeBySpecificCategory (String specificCategory, String selectedCategory)
    {
        switch (specificCategory)
        {
            case "genre": return podcastService.viewPodcast().stream()
                    .filter(podcast ->podcast.getGenre().equalsIgnoreCase(selectedCategory))
                    .flatMap(podcast -> podcast.getEpisodes().stream())                 // Flatten list of episodes
                    .sorted(Comparator.comparing(Track::getTitle))
                    .collect(Collectors.toList());

            case "celebrity": return podcastService.viewPodcast().stream()
                    .filter(podcast -> podcast.getCelebrity().equalsIgnoreCase(selectedCategory))
                    .flatMap(podcast -> podcast.getEpisodes().stream())
                    .sorted(Comparator.comparing(Track::getTitle))
                    .collect(Collectors.toList());
            default: return Collections.emptyList();
        }
    }



    // method to remove playlist
    public boolean removePlaylist (String name)
    {
        if(playlists.containsKey(name.toLowerCase()))
        {
            playlists.remove(name.toLowerCase());
            return true;
        }
        return false;
    }



    // method to remove track from a specific playlist
    public boolean removeTrackFromPlaylist (String playlistName, Track selectedTrack)
    {
        if(playlists.containsKey(playlistName.toLowerCase()))
        {
            Playlist selectedPlaylist = playlists.get(playlistName.toLowerCase());
            List<Track> tracks = selectedPlaylist.getTracks();

            if(tracks.contains(selectedTrack))
            {
                tracks.remove(selectedTrack);
                return true;
            }
        }
        return false;
    }



    // method to search playlist by its name
    public List<String> searchPlaylist (String name)
    {
        return playlists.keySet().stream()
                .filter(playlist -> playlist.toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }



    // method to get all available playlist name
    public  List<String> getPlaylistsName()
    {
        return new ArrayList<>(playlists.keySet());
    }



    // method to view a specific playlist content
    public List<Track> viewPlaylistContent (String name)
    {
        return playlists.containsKey(name.toLowerCase()) ? playlists.get(name.toLowerCase()).getTracks() : null;
    }



    // method to set current playlist details
    public void setCurrentTrack(Track selectedTracks)
    {
        if(selectedTracks == null)
        {
            System.out.println("No Track Selected !");
        }

        currentTrack = selectedTracks;
        try
        {
            audioService = new AudioService(currentTrack.getFilePath());
            if(currentTrack instanceof Song)
            {
                System.out.println("Current Song - " + currentTrack.getTitle());
            }
            else if(currentTrack instanceof Podcast)
            {
                System.out.println("Current Podcast - " + currentTrack.getTitle());
            }
        }
        catch(NullPointerException exception)
        {
            System.out.println("Error : File is not found");
        }
        catch (UnsupportedAudioFileException exception)
        {
            System.out.println("Error : Wrong audio format !");
        }
        catch (IOException exception)
        {
            System.out.println("Error : Problem in reading file !");
        }
        catch (LineUnavailableException exception)
        {
            System.out.println("Error : Audio line is unavailable. Please close other audio apps !");
        }
        catch (Exception exception)
        {
            System.out.println("Error : Loading audio file !");
        }

    }



    // overriding all methods of audio playback service interface
    @Override
    public String play(String title)
    {
        return (audioService != null) ? audioService.play(title) : "No audio loaded !";
    }
    @Override
    public String pause(String title) {return (audioService != null) ? audioService.pause(title) : "No audio loaded !";}
    @Override
    public String resume(String title) {return (audioService != null) ? audioService.resume(title) : "No audio loaded !";}
    @Override
    public String stop(String title)
    {
        return (audioService != null) ? audioService.stop(title) : "No audio loaded !";
    }
    @Override
    public String repeat(String title) {return (audioService != null) ? audioService.repeat(title) : "No audio loaded !";}
    @Override
    public String restart(String title) {return (audioService != null) ? audioService.restart(title) : "No audio loaded !";}
    @Override
    public String jumpToSpecificTime(int minute, int second, String title) {return (audioService != null) ? audioService.jumpToSpecificTime(minute,second,title) : "No audio loaded !";}



    // method to play playlist
    public void playPlaylist (List<Track> selectedTracks, boolean shuffle)
    {
        if(selectedTracks.isEmpty())
        {
            System.out.println("No tracks available !");
        }
        else
        {
            if(shuffle)
            {
                Collections.shuffle(selectedTracks);
            }

            System.out.println("Playing all tracks :- ");

            int count = 0;
            for(Track track : selectedTracks)
            {
                setCurrentTrack(track);
                System.out.println(++count + ". " + audioService.play(track.getTitle()));

                String completed = audioService.waitUntilOneAudioFinish(track.getTitle());
                audioService.closeClip();

                if(completed.equalsIgnoreCase("skip"))
                {
                    continue;
                }
                if(completed.equalsIgnoreCase("quit"))
                {
                    break;
                }
            }
        }
    }




}



