package menuService;

import java.util.Scanner;
import musicService.AudioPlaybackService;


// represent common menu to play any music item


public class AudioPlaybackMenu
{

    AudioPlaybackService audioPlaybackService;                                                                          // reference variable of interface AudioPlaybackService - will allow this class to work with any class that implements the interface


    public AudioPlaybackMenu(AudioPlaybackService audioPlaybackService)                       // constructor that will receive an object of that class with implements AudioPlaybackService
    {
        this.audioPlaybackService = audioPlaybackService;                                                               // assigning child class object (songService) to parent class interface  (AudioPlaybackService), now we can use all methods defined in interface but the actual implementation in songService will be called during runtime
    }

    public void audioPlaybackMenu(Scanner input, String title)
    {
        int choice = 0;

        while (choice != 8)
        {
            System.out.printf("\n\n%20s\n", "Audio Playback Menu");
            System.out.println("1. Play \u2586");
            System.out.println("2. Pause \u23F8");
            System.out.println("3. Resume \u23EF");
            System.out.println("4. Stop \u23F9");
            System.out.println("5. Repeat / Loop \uD83D\uDD01");
            System.out.println("6. Restart \uD83D\uDD03");
            System.out.println("7. Jump to specific time \u23E9");
            System.out.println("8. Back ");

            System.out.print("\nEnter the choice : ");
            choice = input.nextInt();
            input.nextLine();                                                                                           // to consume new line

            switch (choice)
            {
                case 1: System.out.println(audioPlaybackService.play(title));
                    break;
                case 2: System.out.println(audioPlaybackService.pause(title));
                    break;
                case 3: System.out.println(audioPlaybackService.resume(title));
                    break;
                case 4: System.out.println(audioPlaybackService.stop(title));
                    break;
                case 5: System.out.println(audioPlaybackService.repeat(title));
                    break;
                case 6: System.out.println(audioPlaybackService.restart(title));
                    break;
                case 7: System.out.print("Enter minute - ");
                    int min = input.nextInt();
                    System.out.print("Enter second - ");
                    int sec = input.nextInt();

                    System.out.println(audioPlaybackService.jumpToSpecificTime(min,sec,title));
                    break;
                case 8: System.out.println("Returning ....");
                    return;
                default:System.out.println("Invalid choice. Please try again !! \n");
            }
        }
    }


}
