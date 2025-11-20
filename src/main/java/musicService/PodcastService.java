package musicService;

import musicItems.Podcast;
import musicItems.PodcastEpisode;
import dataPackage.PodcastData;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


// performs all services related to a podcast i.e. add, remove, search, view, and play


public class PodcastService implements AudioPlaybackService
{

    private List<Podcast> podcasts;
    private PodcastEpisode currentEpisode;
    private int countEpisode;
    private AudioService audioService;



    // constructor
    public PodcastService ()
    {
        podcasts = new ArrayList<>(PodcastData.samplePodcastData());
    }



    // method to add podcast
    public List<Podcast> addPodcast (Podcast podcast)
    {
        if(podcast == null)
        {
            throw  new IllegalArgumentException("Podcast cannot be null");
        }

        if(!podcasts.contains(podcast))
        {
            podcasts.add(podcast);
            System.out.println(podcast.getTitle() + " added successfully !");
        }
        else
        {
            System.out.println(podcast.getTitle() + " already exists !");
        }
        return podcasts;
    }



    // method to add episode into podcast
    public List<PodcastEpisode> addEpisode (Podcast podcast, PodcastEpisode episode)
    {
        if(episode != null)
        {
            podcast.getEpisodes().add(episode);
            System.out.println(episode.getEpisodeTitle() + " added successfully !");
        }
        else
        {
            System.out.println("Unable to add episode !");
        }

        return podcast.getEpisodes();
    }



    // method to remove podcast
    public List<Podcast> removePodcast (Podcast podcast)
    {
        if(!podcasts.contains(podcast))
        {
            System.out.println("Podcast not found !");
        }
        else
        {
            podcasts.remove(podcast);
        }
        return podcasts;
    }



    // method to remove episode
    public boolean removePodcastEpisode (Podcast podcast,PodcastEpisode episode)
    {
        if (podcast.getEpisodes().contains(episode))
        {
            podcast.getEpisodes().remove(episode);
            return true;
        }
        return false;
    }



    // method to search podcast by complete title
    public Podcast searchPodcastByCompleteTitle (String title)
    {
        return podcasts.stream()
                .filter(podcast -> podcast.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }



    // method to search particular episode
    public PodcastEpisode searchPodcastEpisode (Podcast podcast, String episodeTitle)
    {
        List<PodcastEpisode> allEpisodes = podcast.getEpisodes();

        return allEpisodes.stream()
                .filter(episode -> episode.getEpisodeTitle().toLowerCase().contains(episodeTitle.toLowerCase()))
                .findFirst()
                .orElse(null);
    }



    // method to search podcast by category like title, celebrity, genre (pattern like motivational speech, motivat, mot, m)
    public List<Podcast> searchPodcastByCategory(String category, String text)
    {
        return podcasts.stream()
                .filter(podcast -> { switch(category)
                {
                    case "title"     : return podcast.getTitle().toLowerCase().contains(text.toLowerCase());
                    case "celebrity" : return podcast.getCelebrity().toLowerCase().contains(text.toLowerCase());
                    case "genre"     : return podcast.getGenre().toLowerCase().contains(text.toLowerCase());
                    default          : return false ;
                }})
                .sorted(Comparator.comparing(Podcast::getTitle))
                .collect(Collectors.toList());
    }



    // method to view podcast
    public List<Podcast> viewPodcast ()
    {
        return podcasts.stream()
                .sorted(Comparator.comparing(Podcast::getTitle))
                .collect(Collectors.toList());
    }



    // method to set current episode details
    public void setCurrentEpisode(PodcastEpisode episode)
    {
        if(episode == null)
        {
            System.out.println("No Episode Selected !");
        }

        currentEpisode = episode;
        try
        {
            audioService = new AudioService(currentEpisode.getEpisodeFilePath());
            System.out.println("Current Episode - " + currentEpisode.getEpisodeTitle());
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



    // method to play all episodes
    public void playAllEpisodes(Podcast podcast)
    {
        if(podcast.getEpisodes().isEmpty())
        {
            System.out.println("No episode available !");
        }
        else
        {
            System.out.println("Playing all episodes :- ");
            List<PodcastEpisode> episodesList = podcast.getEpisodes();

            for(PodcastEpisode episode : episodesList)
            {
                setCurrentEpisode(episode);
                System.out.println(++countEpisode + ". " + audioService.play(episode.getEpisodeTitle()));

                String completed = audioService.waitUntilOneAudioFinish(episode.getEpisodeTitle());
                audioService.closeClip();                                                                                // ensures the clip is closed even if the user skips or quits early.

                if(completed.equals("skip"))
                {
                    continue;                                                                                            // continue to next episode
                }
                if(completed.equals("quit"))
                {
                    break;                                                                                                // break the loop
                }

            }
        }
    }



}

