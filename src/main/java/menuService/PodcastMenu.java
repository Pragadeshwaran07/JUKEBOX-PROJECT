package menuService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import musicItems.Podcast;
import musicItems.PodcastEpisode;
import musicService.PodcastService;


// represent podcast menu


public class PodcastMenu
{

    private PodcastService podcastService;
    private ViewMenu viewMenu;


    public PodcastMenu (PodcastService podcastService,ViewMenu viewMenu)
    {
        this.podcastService = podcastService;
        this.viewMenu = viewMenu;
    }


    public void displayPodcastMenu(Scanner input)
    {
        int choice = 0;

        while (choice != 7)
        {
            System.out.printf("\n%20s\n","Podcast Menu");
            System.out.println("1. Add a podcast ");
            System.out.println("2. Remove a podcast ");
            System.out.println("3. Search a podcast ");
            System.out.println("4. View  all podcasts ");
            System.out.println("5. Play One Episode ");
            System.out.println("6. Play All Episodes ");
            System.out.println("7. Back ");

            System.out.print("\nEnter the choice : ");
            choice = input.nextInt();
            input.nextLine();                                                                                          // to consume next line

            switch(choice)
            {
                case 1:addPodcastFeature(input);
                    break;
                case 2:removePodcastFeature(input);
                    break;
                case 3:podcastSearchFeature(input);
                    break;
                case 4:viewMenu.viewAllPodcastFeature();
                    break;
                case 5:playOneEpisodeFeature(input);
                    break;
                case 6:playAllEpisodesFeature(input);
                    break;
                case 7:System.out.println("Returning ....");
                    break;
                default:System.out.println("Invalid choice. Please try again !! \n");
            }
        }
    }


    // method to call for adding a podcast
    public void addPodcastFeature (Scanner input)
    {
        System.out.println("1. Add a new podcast ");
        System.out.println("2. Add episodes ");

        System.out.print("\nEnter the choice : ");
        int choice = input.nextInt();
        input.nextLine();

        switch (choice)
        {
            case 1: addNewPodcastFeature(input);
                break;
            case 2: addEpisodeFeature(input);
                break;
            default: System.out.println("Invalid choice. Please try again !! \n");
        }

    }


    // method to call for adding a new podcast
    public void addNewPodcastFeature (Scanner input)
    {
        System.out.println("Enter the podcast details : ");
        System.out.print("Title : ");
        String title = input.nextLine();
        System.out.print("Celebrity : ");
        String celebrity = input.nextLine();
        System.out.print("Genre : ");
        String genre = input.nextLine();
        System.out.print("Date Published : ");
        String datePublished = input.nextLine();
        System.out.print("About : ");
        String description = input.nextLine();

        Podcast podcast = new Podcast(title,celebrity,genre,datePublished,description,new ArrayList<>());
        podcastService.addPodcast(podcast);

    }


    // method to call for adding episode in podcast
    public void addEpisodeFeature (Scanner input)
    {
        System.out.print("Enter the podcast title to add episode : ");
        String title = input.nextLine();

        Podcast podcast = podcastService.searchPodcastByCompleteTitle(title);
        if(podcast == null)
        {
            System.out.println("Podcast not found !");
        }
        else
        {
            System.out.print("How many episodes do you want to add ? : ");
            int episodeNum = input.nextInt();
            input.nextLine();

            for(int i = 1 ; i <= episodeNum ; i++)
            {
                System.out.println("Enter episode " + i + " details : ");
                System.out.print("Title : ");
                String episodeTitle = input.nextLine();
                System.out.print("About : ");
                String episodeDescription = input.nextLine();
                System.out.print("Duration : ");
                String duration = input.nextLine();
                System.out.print("Episode File Path : ");
                String episodeFilePath = input.nextLine();

                PodcastEpisode episode = new PodcastEpisode(episodeTitle,episodeDescription,duration,episodeFilePath);
                podcastService.addEpisode(podcast,episode);
            }
        }
    }


    // method to call to removing a podcast
    public void removePodcastFeature (Scanner input)
    {
        System.out.println("1. Remove a podcast ");
        System.out.println("2. Remove a episode ");

        System.out.print("\nEnter the choice : ");
        int choice = input.nextInt();
        input.nextLine();

        switch (choice)
        {
            case 1: removeAPodcastFeature(input);
                break;
            case 2: removeEpisodeFeature(input);
                break;
            default: System.out.println("Invalid choice. Please try again !! \n");
        }

    }

    // method to call for removing a podcast
    public void removeAPodcastFeature (Scanner input)
    {
        System.out.print("Enter the podcast title : ");
        String title = input.nextLine();

        Podcast podcast = podcastService.searchPodcastByCompleteTitle(title);

        if(podcast != null)
        {
            podcastService.removePodcast(podcast);
            System.out.println("Podcast removed successfully !");
        }
        else
        {
            System.out.println("Podcast not found !");
        }
    }


    // method to call for removing a episode from podcast
    public void removeEpisodeFeature (Scanner input)
    {
        System.out.print("Enter the podcast title : ");
        String podcastTitle = input.nextLine();
        System.out.print("Enter the episode title : ");
        String episodeTitle = input.nextLine();

        Podcast podcast = podcastService.searchPodcastByCompleteTitle(podcastTitle);
        PodcastEpisode episode = podcastService.searchPodcastEpisode(podcast,episodeTitle);
        if(episode == null)
        {
            System.out.println("No episode found !");
        }
        else
        {
            boolean result = podcastService.removePodcastEpisode(podcast,episode);
            System.out.println(result ? episodeTitle + " removed successfully from " +podcastTitle : "Unable to delete or doesn't exist !");

        }
    }


    // method to search podcast details on the basis of title, celebrity
    public void podcastSearchFeature(Scanner input)
    {
        System.out.println("\nSearch Podcast ");
        System.out.println("1. By Title ");
        System.out.println("2. By Celebrity ");
        System.out.println("3. By Genre ");

        System.out.print("\nEnter the choice : ");
        int choice = input.nextInt();
        input.nextLine();

        String category = null;
        switch(choice)
        {
            case 1: category = "title";
                break;
            case 2: category = "celebrity";
                break;
            case 3: category = "genre";
                break;
            default:System.out.println("Invalid choice. Please try again !! \n");
        }

        if(category == null)
        {
            return;
        }

        System.out.print("Search " + category + " : ");
        String text = input.nextLine();

        List<Podcast> podcastMatch = podcastService.searchPodcastByCategory(category,text);

        if(podcastMatch == null || podcastMatch.isEmpty())
        {
            System.out.println("No such " + category + " available !");
        }
        else
        {
            int count = 1;
            for (Podcast podcast : podcastMatch)
            {
                System.out.println(count++ + ". " + podcast);
            }
        }
    }


    // method to call for play one episode from podcast
    public void playOneEpisodeFeature(Scanner input)
    {
        List<Podcast> allPodcastsAvailable = podcastService.viewPodcast();
        if(allPodcastsAvailable == null || allPodcastsAvailable.isEmpty())
        {
            System.out.println("No podcast available !");
        }
        else
        {
            int count = 1;
            for (Podcast podcast : allPodcastsAvailable)
            {
                System.out.println(count++ + ". " + podcast.getTitle());
            }

            System.out.print("Enter podcast title : ");
            String podcastTitle = input.nextLine();

            Podcast podcast = podcastService.searchPodcastByCompleteTitle(podcastTitle);

            if(podcast == null)
            {
                System.out.println("Podcast not found !");
                return;
            }

            List<PodcastEpisode> allEpisodes = podcast.getEpisodes();
            if (allEpisodes == null || allEpisodes.isEmpty())
            {
                System.out.println("No episodes available !");
            }
            else
            {
                count = 1;
                System.out.println("Episodes :-");
                for (PodcastEpisode episode : allEpisodes)
                {
                    System.out.println(String.format(" %2d. %s%n", count++, episode));
                }
                System.out.print("Enter episode title : ");
                String episodeTitle = input.nextLine();

                PodcastEpisode currentEpisode = podcastService.searchPodcastEpisode(podcast, episodeTitle);

                podcastService.setCurrentEpisode(currentEpisode);
                AudioPlaybackMenu playbackFeature = new AudioPlaybackMenu(podcastService);
                playbackFeature.audioPlaybackMenu(input, currentEpisode.getEpisodeTitle());
            }
        }

    }


    // method to play all episodes of a podcast
    public void playAllEpisodesFeature (Scanner input)
    {
        List<Podcast> allPodcastsAvailable = podcastService.viewPodcast();
        if (allPodcastsAvailable == null || allPodcastsAvailable.isEmpty())
        {
            System.out.println("No podcast available !");
        }
        else
        {
            int count = 1;
            for (Podcast podcast : allPodcastsAvailable)
            {
                System.out.println(count++ + ". " + podcast.getTitle());
            }

            System.out.print("Enter podcast title : ");
            String podcastTitle = input.nextLine();

            Podcast podcast = podcastService.searchPodcastByCompleteTitle(podcastTitle);
            podcastService.playAllEpisodes(podcast);

        }
    }

}
