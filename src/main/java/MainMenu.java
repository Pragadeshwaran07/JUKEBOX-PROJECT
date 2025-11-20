import menuService.PlaylistMenu;
import menuService.PodcastMenu;
import menuService.SongMenu;
import menuService.ViewMenu;
import musicService.PlaylistService;
import musicService.PodcastService;
import musicService.SongService;
import java.util.Scanner;


// entry point of entire application


public class MainMenu
{

    public static void main (String[] args)
    {

        Scanner input = new Scanner(System.in);

        // to maintain data consistency through - out the application

        SongService songService = new SongService();
        PodcastService podcastService = new PodcastService();
        PlaylistService playlistService = new PlaylistService(songService,podcastService);
        ViewMenu viewMenu = new ViewMenu(songService,podcastService,playlistService);

        MainMenu mainMenu = new MainMenu();
        mainMenu.displayMainMenu(input, songService, podcastService, viewMenu);

        input.close();

    }



    // main menu display
    public void displayMainMenu(Scanner input, SongService songService, PodcastService podcastService, ViewMenu viewMenu)
    {


        int choice = 0;

        while(choice != 5)
        {

            System.out.printf("%35s\n","\uD83C\uDFB6 Welcome to JukeBox Menu \uD83C\uDFB6");
            System.out.println("1. Songs ");
            System.out.println("2. Podcasts ");
            System.out.println("3. Playlists ");
            System.out.println("4. View ");
            System.out.println("5. Exit ");

            System.out.print("\nEnter the choice : ");
            choice = input.nextInt();
            input.nextLine();                                                                                           // to consume new line

            switch (choice)
            {
                case 1 : SongMenu songMenu = new SongMenu(songService,viewMenu);
                    songMenu.displaySongMenu(input);
                    break;
                case 2 : PodcastMenu podcastMenu = new PodcastMenu(podcastService,viewMenu);
                    podcastMenu.displayPodcastMenu(input);
                    break;
                case 3 : PlaylistMenu playlistMenu = new PlaylistMenu(songService,podcastService);
                    playlistMenu.displayPlaylistMenu(input);
                    break;
                case 4 : viewMenu.displayViewMenu(input);
                    break;
                case 5 : System.out.println("JukeBox Exiting ....");
                    break;
                default : System.out.println("Invalid choice. Please try again !! \n");
            }
        }
    }


}
