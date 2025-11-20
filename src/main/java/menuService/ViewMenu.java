package menuService;

import musicItems.Podcast;
import musicItems.Song;
import musicItems.Track;
import musicService.PlaylistService;
import musicService.PodcastService;
import musicService.SongService;
import java.util.List;
import java.util.Scanner;


// this class sole purpose is to display i.e. no add/remove/search operations


public class ViewMenu
{

    SongService songService;
    PodcastService podcastService;
    PlaylistService playlistService;


    public ViewMenu(SongService songService, PodcastService podcastService, PlaylistService playlistService)
    {
        this.songService = songService;
        this.podcastService = podcastService;
        this.playlistService = playlistService;
    }


    public void displayViewMenu(Scanner input)
    {

        int choice = 0;

        while (choice != 7)
        {

            System.out.printf("\n%20s\n", "View Menu");
            System.out.println("1. View All Songs ");
            System.out.println("2. View All Podcasts");
            System.out.println("3. View All Genres ");
            System.out.println("4. View All Albums ");
            System.out.println("5. View All Artist ");
            System.out.println("6. View All Celebrities");
            System.out.println("7. Back ");

            System.out.print("Enter the choice : ");
            choice = input.nextInt();
            input.nextLine();                                                                                           // to consume new line

            switch (choice)
            {
                case 1: viewAllSongFeature();
                    break;
                case 2: viewAllPodcastFeature();
                    break;
                case 3: System.out.println("1. Song ");
                    System.out.println("2. Podcast ");

                    System.out.print("Enter choice : ");
                    int choice1 = input.nextInt();
                    input.nextLine();

                    if(choice1 == 1)
                    {
                        viewAllSongCategoryFeature(input,"genre");
                    }
                    else
                    {
                        viewAllPodcastCategoryFeature(input,"genre");
                    }
                    break;
                case 4: viewAllSongCategoryFeature(input,"album");
                    break;
                case 5: viewAllSongCategoryFeature(input,"artist");
                    break;
                case 6: viewAllPodcastCategoryFeature(input,"celebrity");
                    break;
                case 7: System.out.println("Returning ....");
                    return;
                default: System.out.println("Invalid choice. Please try again !! \n");
            }
        }

    }



    // method to view all songs
    public void viewAllSongFeature()
    {

        List<Song> allSongs = songService.viewSong();

        if (allSongs == null || allSongs.isEmpty())
        {
            System.out.println("No song available !");
        }
        else
        {
            int count = 1;
            for (Song song : allSongs)
            {
                System.out.println(count++ + ". " + song);
            }
        }

    }




    // method to view all podcasts
    public void viewAllPodcastFeature()
    {

        List<Podcast> allPodcasts = podcastService.viewPodcast();

        if (allPodcasts.isEmpty())
        {
            System.out.println("No podcast available !");
        }
        else
        {
            int count = 1;
            for (Podcast podcast : allPodcasts)
            {
                System.out.println(count++ + ". " + podcast);
            }
        }

    }



    // method to view songs related data based on album, artist
    public void viewAllSongCategoryFeature(Scanner input, String category)
    {
        List<String> allAvailableCategory = playlistService.getAvailableSongCategory(category);

        if(allAvailableCategory == null || allAvailableCategory.isEmpty())
        {
            System.out.println("No " + category + " available !");
            return;
        }
        else
        {
            System.out.println("Available " + category + " :-" );
            int count = 1;
            for (String list : allAvailableCategory)
            {
                System.out.println(count++ + ". " + list);
            }
        }

        System.out.print("Enter " + category + " name you want to see its content : ");
        String text = input.nextLine();

        List<Track> fullContentList = playlistService.getAllSongsBySpecificCategory (category, text);

        if(fullContentList == null || fullContentList.isEmpty())
        {
            System.out.println("No content available !");
        }
        else
        {
            int count = 1;
            for (Track track : fullContentList)
            {
                System.out.println(count++ + ". " + track);
            }
        }

    }



    // method to view podcast related data based on celebrity
    public void viewAllPodcastCategoryFeature(Scanner input, String category)
    {
        List<String> allAvailableCategory = playlistService.getAvailablePodcastCategory(category);

        if(allAvailableCategory == null || allAvailableCategory.isEmpty())
        {
            System.out.println("No " + category + " available !");
            return;
        }
        else
        {
            System.out.println("Available " + category + " :-" );
            int count = 1;
            for (String list : allAvailableCategory)
            {
                System.out.println(count++ + ". " + list);
            }
        }

        System.out.print("Enter " + category + " name you want to see its content : ");
        String text = input.nextLine();

        List<Track> fullContentList = playlistService.getAllPodcastEpisodeBySpecificCategory (category, text);

        if(fullContentList == null || fullContentList.isEmpty())
        {
            System.out.println("No content available !");
        }
        else
        {
            int count = 1;
            for (Track track : fullContentList)
            {
                System.out.println(count++ + ". " + track);
            }
        }

    }




}
