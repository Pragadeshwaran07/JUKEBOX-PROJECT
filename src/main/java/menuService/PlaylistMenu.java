package menuService;

import musicItems.Track;
import musicService.PlaylistService;
import musicService.PodcastService;
import musicService.SongService;

import java.util.List;
import java.util.Scanner;


// represent playlist menu


public class PlaylistMenu
{

    PlaylistService playlistService;


    public PlaylistMenu (SongService songService, PodcastService podcastService)
    {
        this.playlistService = new PlaylistService(songService, podcastService);
    }


    public void displayPlaylistMenu(Scanner input)
    {

        int choice = 0;

        while(choice != 7)
        {

            System.out.printf("\n%20s\n", "Playlist Menu");
            System.out.println("1. Create new Playlist ");
            System.out.println("2. Add Tracks into Playlist ");
            System.out.println("3. Remove Playlist ");
            System.out.println("4. Search Playlist ");
            System.out.println("5. View Playlist  ");
            System.out.println("6. Play Playlist ");
            System.out.println("7. Back ");

            System.out.print("\nEnter the choice : ");
            choice = input.nextInt();
            input.nextLine();                                                                                           // to consume new line

            switch (choice)
            {
                case 1: createPlaylistFeature(input);
                    break;
                case 2: addTracksIntoPlaylistFeature(input);
                    break;
                case 3: removePlaylistFeature(input);
                    break;
                case 4: searchPlaylistFeature(input);
                    break;
                case 5: viewPlaylistFeature(input);
                    break;
                case 6: playPlaylistFeature(input);
                    break;
                case 7: System.out.println("Returning ....");
                    return;
                default:System.out.println("Invalid choice. Please try again !! \n");
            }
        }


    }



    // method to call for creating a new playlist
    public void createPlaylistFeature(Scanner input)
    {

        System.out.print("Enter new playlist name : ");
        String name = input.nextLine();

        boolean result = playlistService.createPlaylist(name);
        System.out.println( result ? "Playlist successfully created !" : "Playlist already exists !" );


    }



    // method to call for adding tracks into playlist
    public void addTracksIntoPlaylistFeature (Scanner input)
    {

        System.out.print("Enter the playlist name : ");
        String playlistName = input.nextLine();

        List<Track> selectedPlaylistTracks = playlistService.viewPlaylistContent(playlistName);

        if(selectedPlaylistTracks == null)
        {
            System.out.println("No playlist found !");
        }
        else if (selectedPlaylistTracks.isEmpty())
        {
            System.out.println("Empty Playlist !");
            addTracks(input, playlistName);
        }
        else
        {
            selectedPlaylistTracks.forEach(System.out::println);
            addTracks(input, playlistName);
        }

    }



    // helper method to select the type of track - song or podcast to add into playlist
    public void addTracks(Scanner input, String playlistName)
    {

        System.out.print("Do you want to add tracks ? (y/n) : ");
        char response = input.next().charAt(0);

        if(response == 'y' || response == 'Y')
        {

            System.out.println("\nSelect the type of track you want to add :- ");
            System.out.println("1. Add Songs ");
            System.out.println("2. Add Podcasts ");

            System.out.print("Enter the choice : ");
            int choice = input.nextInt();
            input.nextLine();

            switch (choice)
            {
                case 1:addSongTrack(input,playlistName);
                    break;
                case 2:addPodcastTrack(input,playlistName);
                    break;
                default:System.out.println("Invalid choice. Please try again !! \n");

            }
        }


    }



    // method to add song track into playlist
    public void addSongTrack(Scanner input, String playlistName)
    {

        char response = 'y';

        while (response == 'y' || response == 'Y')
        {

            System.out.println("\n");
            System.out.println("1. Add Song By Genre ");
            System.out.println("2. Add Song By Album ");
            System.out.println("3. Add Song By Artist ");

            System.out.print("\nEnter the choice : ");
            int choice = input.nextInt();
            input.nextLine();

            String specificCategory = null;
            switch (choice)
            {
                case 1: specificCategory = "genre";
                    break;
                case 2: specificCategory = "album";
                    break;
                case 3: specificCategory = "artist";
                    break;
                default: System.out.println("Invalid choice. Please try again !! \n");
            }

            if (specificCategory == null)
            {
                return;
            }

            List<String> availableCategory = playlistService.getAvailableSongCategory(specificCategory);

            if (availableCategory == null || availableCategory.isEmpty())
            {
                System.out.println("No such " + specificCategory + " available !");
                return;
            }
            else
            {
                System.out.println("Available " + specificCategory + " :-");
                int count = 1;
                for (String category : availableCategory)
                {
                    System.out.println(count++ + ". " + category);
                }

                System.out.print("Enter a specific " + specificCategory + " : ");
                String selectedCategory = input.nextLine();

                List<Track> matchTracks = playlistService.getAllSongsBySpecificCategory(specificCategory, selectedCategory);

                if (matchTracks == null || matchTracks.isEmpty())
                {
                    System.out.println("No song found !");
                }
                else
                {
                    count = 1;
                    for (Track track : matchTracks)
                    {
                        System.out.println(count++ + ". " + track);
                    }

                    System.out.print("\nEnter the title of song to add : ");
                    String selectedTrackTitle = input.nextLine();

                    Track selectedTrack = matchTracks.stream()
                            .filter(track -> track.getTitle().equalsIgnoreCase(selectedTrackTitle))
                            .findFirst()
                            .orElse(null);

                    if (selectedTrack == null)
                    {
                        System.out.println("No such song found !");
                    }
                    else
                    {
                        boolean result = playlistService.addTrackIntoPlaylist(playlistName, selectedTrack);
                        System.out.println(result ? selectedTrack.getTitle() + " successfully added to " + playlistName + " playlist !" : "Unable to add " + selectedTrack.getTitle() + " to " + playlistName + " playlist or already exits !");
                    }
                }

                System.out.print("\nDo you want to add more songs ? (y/n) : ");
                response = input.next().charAt(0);
                input.nextLine();
            }
        }


    }



    // method to add podcast episode track into playlist
    public void addPodcastTrack(Scanner input, String playlistName)
    {

        char response = 'y';

        while (response == 'y' || response == 'Y')
        {
            System.out.println("1. Add Podcast By Genre ");
            System.out.println("2. Add Podcast By Celebrity ");

            System.out.print("\nEnter the choice : ");
            int choice = input.nextInt();
            input.nextLine();

            String specificCategory = null;
            switch (choice)
            {
                case 1: specificCategory = "genre";
                    break;
                case 2: specificCategory = "celebrity";
                    break;
                default: System.out.println("Invalid choice. Please try again !! \n");
            }

            if (specificCategory == null)
            {
                return;
            }

            List<String> availableCategory = playlistService.getAvailablePodcastCategory(specificCategory);

            if (availableCategory == null || availableCategory.isEmpty())
            {
                System.out.println("No such " + specificCategory + " available !");
            }
            else
            {
                System.out.println("Available " + specificCategory + " :-");
                int count = 1;
                for (String category : availableCategory)
                {
                    System.out.println(count++ + ". " + category);
                }

                System.out.print("Enter a specific " + specificCategory + " : ");
                String selectedCategory = input.nextLine();
                input.nextLine();

                List<Track> matchTracks = playlistService.getAllPodcastEpisodeBySpecificCategory(specificCategory, selectedCategory);

                if (matchTracks == null || matchTracks.isEmpty())
                {
                    System.out.println("No podcast episode found !");
                }
                else
                {
                    count = 1;
                    for (Track track : matchTracks)
                    {
                        System.out.println(count++ + ". " + track);
                    }

                    System.out.print("\nEnter the title of podcast episode to add : ");
                    String selectedTrackTitle = input.nextLine();

                    Track selectedTrack = matchTracks.stream()
                            .filter(track -> track.getTitle().equalsIgnoreCase(selectedTrackTitle))
                            .findFirst()
                            .orElse(null);

                    if (selectedTrack == null)
                    {
                        System.out.println("No such podcast episode found !");
                    }
                    else
                    {
                        boolean result = playlistService.addTrackIntoPlaylist(playlistName, selectedTrack);
                        System.out.println(result ? selectedTrack.getTitle() + " successfully added to " + playlistName + " playlist !" : "Unable to add " + selectedTrack.getTitle() + " to " + playlistName + " playlist or already exits !");
                    }
                }

                System.out.print("\nDo you want to add more podcast episode ? (y/n) : ");
                response = input.next().charAt(0);
                input.nextLine();
            }
        }


    }



    // method to call for removing a playlist
    public void removePlaylistFeature(Scanner input)
    {

        System.out.println("1. Remove track from Playlist ");
        System.out.println("2. Remove Playlist ");

        System.out.print("Enter the choice : ");
        int choice = input.nextInt();
        input.nextLine();

        switch (choice)
        {
            case 1 : System.out.print("Enter playlist name : ");
                String playlistName = input.nextLine();
                List<Track> tracks = playlistService.viewPlaylistContent(playlistName);
                if(tracks == null || tracks.isEmpty())
                {
                    System.out.println("No playlist found !");
                }
                else
                {
                    int count = 1;
                    for (Track track : tracks)
                    {
                        System.out.println(count++ + ". " + track);
                    }

                    System.out.print("\nEnter track title to delete : ");
                    String selectedTrackTitle = input.nextLine();

                    Track selectedTrack = tracks.stream()
                            .filter(track -> track.getTitle().equalsIgnoreCase(selectedTrackTitle))
                            .findFirst()
                            .orElse(null);
                    if(selectedTrack == null)
                    {
                        System.out.println("No such track found !");
                    }
                    else
                    {
                        boolean result = playlistService.removeTrackFromPlaylist(playlistName, selectedTrack);
                        System.out.println(result ? selectedTrack.getTitle() + " successfully removed  from " + playlistName + " playlist !" : "Unable to remove " + selectedTrack.getTitle() + " from " + playlistName + " playlist or don't exist !");
                    }
                }
                break;
            case 2 : System.out.print("Enter playlist name to delete : ");
                playlistName = input.nextLine();

                boolean result = playlistService.removePlaylist(playlistName);
                System.out.println( result ? playlistName + " successfully deleted !" : "Unable to delete " + playlistName + " or don't exist !");
                break;
            default: System.out.println("Invalid choice. Please try again !! \n");
        }


    }



    // method to call for searching a playlist
    public void searchPlaylistFeature (Scanner input)
    {

        System.out.print("Search Playlist : ");
        String name = input.nextLine();

        List<String> playlistNameList = playlistService.searchPlaylist(name);

        if(playlistNameList == null || playlistNameList.isEmpty())
        {
            System.out.println("No such playlist found !");
        }
        else
        {
            System.out.println("Available Playlist :- ");
            int count = 1;
            for (String playlistName : playlistNameList)
            {
                System.out.println(count++ + ". " + playlistName);
            }
        }


    }



    // method to call for view playlist
    public void viewPlaylistFeature (Scanner input)
    {

        List<String> playlistNameList = playlistService.getPlaylistsName();

        if(playlistNameList.isEmpty())
        {
            System.out.println("No playlist available !");
        }
        else
        {
            System.out.println("Available Playlist :- ");
            int count = 1;
            for (String name : playlistNameList)
            {
                System.out.println(count++ + ". " + name);
            }

            System.out.print("Enter the playlist to view all tracks : ");
            String selectedPlaylist = input.nextLine();

            List<Track> tracks = playlistService.viewPlaylistContent(selectedPlaylist);
            if(tracks == null)
            {
                System.out.println("Playlist not found !");
            }
            else if (tracks.isEmpty())
            {
                System.out.println("Empty Playlist !");
            }
            else
            {
                System.out.println("Tracks are :-");
                count = 1;
                for (Track track : tracks)
                {
                    System.out.println(count++ + ". " + track);
                }
            }
        }


    }



    // method to call for playing all tracks in a playlist
    public void playPlaylistFeature (Scanner input)
    {

        List<String> playlistNameList = playlistService.getPlaylistsName();

        if(playlistNameList.isEmpty())
        {
            System.out.println("No playlist available !");
        }
        else
        {
            int count = 1;
            for (String name : playlistNameList)
            {
                System.out.println(count++ + ". " + name);
            }

            System.out.print("Enter the playlist name to play : ");
            String selectedPlaylist = input.nextLine();

            System.out.print("Do you not play as shuffle mode ? (y/n) : ");
            String shuffleInput = input.nextLine();

            // Convert input to boolean
            boolean shuffle = shuffleInput.equalsIgnoreCase("y");

            List<Track> selectedTracks = playlistService.viewPlaylistContent(selectedPlaylist);

            playlistService.playPlaylist(selectedTracks,shuffle);

        }

    }




}

