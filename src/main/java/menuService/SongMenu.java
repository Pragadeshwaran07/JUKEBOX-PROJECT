package menuService;

import java.util.List;
import java.util.Scanner;
import musicItems.Song;
import musicService.SongService;


// represent song menu


public class SongMenu
{

    private SongService songService;
    private ViewMenu viewMenu;


    public SongMenu (SongService songService, ViewMenu viewMenu)
    {
        this.songService = songService;
        this.viewMenu = viewMenu;
    }



    public void displaySongMenu(Scanner input)
    {
        int choice = 0;

        while (choice != 7)
        {
            System.out.printf("\n%20s\n", "Song Menu");
            System.out.println("1. Add a song ");
            System.out.println("2. Remove a song ");
            System.out.println("3. Search a song ");
            System.out.println("4. View all songs  ");
            System.out.println("5. Play specific song ");
            System.out.println("6. Play all songs");
            System.out.println("7. Back ");

            System.out.print("\nEnter the choice : ");
            choice = input.nextInt();
            input.nextLine();                                                                                            // to consume new line

            switch (choice)
            {
                case 1: addSongFeature(input);
                    break;
                case 2: removeSongFeature(input);
                    break;
                case 3: songSearchFeature(input);
                    break;
                case 4: viewMenu.viewAllSongFeature();
                    break;
                case 5: viewMenu.viewAllSongFeature();
                    playSongFeature(input);
                    break;
                case 6: songService.playAllSong();
                    break;
                case 7: System.out.println("Returning ....");
                    return;
                default:System.out.println("Invalid choice. Please try again !! \n");
            }
        }

    }



    // method to call for adding a song
    public void addSongFeature (Scanner input)
    {

        System.out.println("\nEnter the song details : ");
        System.out.print("Title : ");
        String title = input.nextLine();
        System.out.print("Album : ");
        String album = input.nextLine();
        System.out.print("Artist : ");
        String artist = input.nextLine();
        System.out.print("Genre : ");
        String genre = input.nextLine();
        System.out.print("Duration : ");
        String duration = input.nextLine();
        System.out.print("Date Added : ");
        String dateAdded = input.nextLine();
        System.out.print("File Path : ");
        String filePath = input.nextLine();

        Song song = new Song(title, album, artist, genre, duration, dateAdded, filePath);
        songService.addSong(song);

    }



    // method to call to remove a song
    public void removeSongFeature (Scanner input)
    {

        System.out.print("\nEnter the Song Title : ");
        String title = input.nextLine();

        Song song = songService.searchSongByCompleteTitle(title);

        if(song != null)
        {
            songService.removeSong(song);
            System.out.println("Song removed successfully !");
        }
        else
        {
            System.out.println("Song not found !");
        }

    }


    // method to search song details on the basis of title, album, genre, artist
    public void songSearchFeature(Scanner input)
    {

        System.out.println("\nSearch song ");
        System.out.println("1. By Title ");
        System.out.println("2. By Artist ");
        System.out.println("3. By Genre ");
        System.out.println("4. By Album ");

        System.out.print("\nEnter the choice : ");
        int choice = input.nextInt();
        input.nextLine();

        String category = null;
        switch(choice)
        {
            case 1: category = "title";
                break;
            case 2: category = "artist";
                break;
            case 3: category = "genre";
                break;
            case 4: category = "album";
                break;
            default:System.out.println("Invalid choice. Please try again !! \n");
        }

        if(category == null)
        {
            return;
        }

        System.out.print("Search " + category + " : ");
        String text = input.nextLine();

        List<Song> songMatch = songService.searchSongByCategory(category,text);

        if(songMatch == null || songMatch.isEmpty())
        {
            System.out.println("No such " + category + " available !");
        }
        else
        {
            int count = 1;
            for (Song song : songMatch)
            {
                System.out.println(count++ + ". " + song);
            }
        }

    }



    // method to play song
    public void playSongFeature (Scanner input)
    {

        System.out.print("\nEnter the song to play : ");
        String title = input.nextLine();

        Song currentSong = songService.searchSongByCompleteTitle(title);

        if(currentSong != null)
        {
            songService.setCurrentSong(currentSong);
            AudioPlaybackMenu playbackFeature = new AudioPlaybackMenu(songService);                                     // dynamic polymorphism - songService (object of songService class) is passed to AudioPlaybackMenu as an AudioPlaybackService constructor
            playbackFeature.audioPlaybackMenu(input,currentSong.getTitle());                                            // audioPlaybackService reference in AudioPlaybackMenu now points to a SongService object,so calling audioPlaybackService.play() will execute SongService’s implementation of play()
        }
        else
        {
            System.out.println("Song not found !");
        }

    }



}
