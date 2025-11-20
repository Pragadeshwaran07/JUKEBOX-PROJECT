package musicService;

import musicItems.Song;
import dataPackage.SongData;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


// performs all services related to a song i.e. add, remove, search, view and all playback services


public class SongService implements AudioPlaybackService
{

    private List<Song> songs;
    private Song currentSong;                                                                                           // it will hold the current song data
    private int countSong;                                                                                              // to count the total song played
    private AudioService audioService = new AudioService();                                                                                  // control actual audio operations


    // constructor
    public SongService ()
    {
        songs = new ArrayList<>(SongData.sampleSongs());
        countSong = 0;
    }



    // method to add song
    public List<Song> addSong (Song song)
    {
        if(song == null)
        {
            throw new IllegalArgumentException("Song cannot be null !");
        }
        if(!songs.contains(song))
        {
            songs.add(song);
            System.out.println(song.getTitle() + " added successfully !");
        }
        else
        {
            System.out.println(song.getTitle() + " already exists !");
        }
        return songs;
    }



    // method to remove song
    public List<Song> removeSong (Song song)
    {
        if(!songs.contains(song))
        {
            System.out.println("Song not found !");
        }
        else
        {
            songs.remove(song);
        }
        return songs;
    }



    // method to search song by complete title
    public Song searchSongByCompleteTitle (String title)
    {
        return songs.stream()
                .filter(song -> song.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }



    // method to search song by category like title, artist, genre, album  (pattern like blank, blan, blank space)
    public List<Song> searchSongByCategory(String category, String text)
    {
        return songs.stream()
                .filter(song -> { switch(category)
                {
                    case "title"  : return song.getTitle().toLowerCase().contains(text.toLowerCase());
                    case "artist" : return song.getArtist().toLowerCase().contains(text.toLowerCase());
                    case "genre"  : return song.getGenre().toLowerCase().contains(text.toLowerCase());
                    case "album"  : return song.getAlbum().toLowerCase().contains(text.toLowerCase());
                    default       : return false;
                }})
                .sorted(Comparator.comparing(Song::getTitle))
                .collect(Collectors.toList());
    }



    // method to view song
    public List<Song> viewSong()                                                                                        // list because in hashset order doesn't matter, so logically sorting is not possible
    {
        return songs.stream()
                .sorted(Comparator.comparing(Song::getTitle))
                .collect(Collectors.toList());

    }



    // method to set current song details
    public void setCurrentSong(Song song)
    {
        if(song == null)
        {
            System.out.println("No Song Selected !");
        }

        currentSong = song;
        try
        {
            audioService = new AudioService(currentSong.getFilePath());
            System.out.println("Current Song - " + currentSong.getTitle());
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



    // method to play all songs
    public void playAllSong()
    {
        if(songs.isEmpty())
        {
            System.out.println("No song available !");
        }

        else
        {
            System.out.println("Playing all songs :- ");
            for (Song song : songs)
            {
                setCurrentSong(song);
                System.out.println(++countSong + ". " + audioService.play(song.getTitle()) + "\n");

                String completed = audioService.waitUntilOneAudioFinish(song.getTitle());
                audioService.closeClip();                                                                               // ensures the clip is closed even if the user skips or quits early.

                if(completed.equals("skip"))
                {
                    continue;                                                                                           //continue to next episode
                }

                if(completed.equals("quit"))
                {
                    break;                                                                                              // break the loop
                }

            }
        }
    }




}
