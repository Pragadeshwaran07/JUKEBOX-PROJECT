package musicService;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


// it deals with all audio operations mention in AudioPlaybackService interface
// helper class that tells how the actual audio operates behind the program


public class AudioService
{


    private Clip clip;
    private long pausePosition;

    public AudioService() {
    }

    // constructor
    public AudioService(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException, NullPointerException
    {
        // load sound file from your resource folder into memory, so program can play it.
        // getClass() --> the class in which we're currently
        // getClassloader() --> get the class loader that knows how to load files from resource folder
        // getResource() --> URL access

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource(filePath));
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        pausePosition = 0;
    }


    // method to play audio
    public String play(String title)
    {
        if(clip != null && !clip.isRunning())
        {
            clip.setMicrosecondPosition(0);
            clip.start();
            return title + " - play         |   Duration - " + getFormatTime(clip.getMicrosecondLength());
        }

        return "Cannot play - audio is not loaded or already playing !";
    }



    // method to pause audio
    public String pause(String title)
    {
        if(clip != null && clip.isRunning())
        {
            pausePosition = clip.getMicrosecondPosition();
            clip.stop();
            return title + " - pause at " + getFormatTime(pausePosition);
        }

        return "Cannot pause - no audio is currently playing to pause !";
    }



    // method to resume audio
    public String resume(String title)
    {
        if(clip != null && !clip.isRunning())
        {
            clip.setMicrosecondPosition(pausePosition);
            clip.start();
            return title + " - resume from " + getFormatTime(pausePosition);
        }

        return "Cannot resume - no audio is currently playing to resume !";
    }



    // method to stop audio
    public String stop(String title)
    {
        if(clip != null && clip.isRunning())
        {
            clip.stop();
            clip.setMicrosecondPosition(0);
            return title + " - stop ";
        }

        return "Cannot stop - no audio is currently playing to stop !";
    }



    // method to repeat audio continuously
    public String repeat(String title)
    {
        if(clip != null)
        {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            return title + " - repeat mode enabled";
        }
        return "Cannot enable repeat mode - audio is not loaded !";
    }



    // method to restart the audio / play from the beginning
    public String restart(String title)
    {
        if(clip != null)
        {
            clip.setMicrosecondPosition(0);
            clip.start();
            return title + " - restart";
        }
        return "Cannot restart - audio is not playing or at paused !";
    }



    // method to jump to a specific time in the audio / forward or backward
    public String jumpToSpecificTime(int minute, int second, String title)
    {
        long jumpMicroSec = ((minute * 60L) + second) * 1000000L;

        if( jumpMicroSec < clip.getMicrosecondLength() )
        {
            clip.setMicrosecondPosition(jumpMicroSec);
            clip.start();
            return title + " started playing from " + minute +" : " + second;
        }

        return "Time exceeds audio length !";
    }



    // method to convert time from micro sec to min:sec like 2:43 for better readability
    public String getFormatTime (long microseconds)
    {
        long totalSeconds = microseconds / 1000000L;
        long min = totalSeconds / 60L;
        long sec = totalSeconds % 60L;

        return String.format("%d : %02d",min,sec);
    }



    // method to wait for one audio to be finished and then next audio be played or the user pauses it.
    // Returns false if user pauses the audio, true if audio finishes normally.
    public String waitUntilOneAudioFinish (String title)
    {
        Scanner input = new Scanner(System.in);

        try {
            Thread.sleep(500);     //0.5 sec                                                                      // Let clip stabilize into the system

            while (true)                                                                                                // loop will only exit if audio finish,skip or quit
            {
                if (!clip.isRunning() && clip.getMicrosecondPosition() >= clip.getMicrosecondLength())                 // exit if audio finished playing completely
                {
                    System.out.println(title + " finished playing.");                                                   // true when audio finished
                    return "true";                                                                                                                                                                          // let the next song play
                }
                // this if will execute only if user has paused the audio at least once
                if (!clip.isRunning() && pausePosition > 0)                                                             // if clip isn't playing and pausePosition > 0, it means it’s audio is paused.
                {
                    System.out.print("\nPress r - resume , b - restart , s - skip , j- jump , q - quit : ");
                    String choice = input.nextLine().toLowerCase();

                    switch (choice)
                    {
                        case "r": System.out.println(resume(title));
                            break;
                        case "b": pausePosition = 0;
                            System.out.println("Audio restarted. Playing from the beginning now.");
                            System.out.println(restart(title));
                            break;
                        case "s": clip.stop();
                            pausePosition = 0;
                            System.out.println(title + " skipped.");
                            return "skip";                                                                              // skip current audio
                        case "j": System.out.print("Enter min - ");
                            int min = input.nextInt();
                            System.out.print("Enter sec - ");
                            int sec = input.nextInt();
                            input.nextLine();
                            System.out.println(jumpToSpecificTime(min,sec,title));
                            break;
                        case "q": clip.stop();
                            pausePosition = 0;
                            System.out.println("Returning .....");
                            return "quit";                                                                        // when user wants to exit to main menu
                        default:  System.out.println("Invalid choice! Choose between r, b or s.");
                    }
                }
                else if (clip.isRunning())                                                                              // if running, allow pause or skip
                {
                    System.out.print("\nPress p - pause , s - skip , j - jump , q - quit : ");
                    if (input.hasNextLine())                                                                            // it checks if the user has typed anything or press enter.
                    {
                        String choice = input.nextLine().toLowerCase();
                        switch (choice)
                        {
                            case "p": System.out.println(pause(title));
                                break;
                            case "s": clip.stop();
                                pausePosition = 0;
                                System.out.println(title + " skipped.");
                                return "skip";                                                                     // skip current song
                            case "j": System.out.print("Enter min - ");
                                int min = input.nextInt();
                                System.out.print("Enter sec - ");
                                int sec = input.nextInt();
                                input.nextLine();
                                System.out.println(jumpToSpecificTime(min,sec,title));
                                break;
                            case "q": clip.stop();
                                pausePosition = 0;
                                System.out.println("Returning ......");
                                return "quit";
                            default:  System.out.println("Invalid input! You can only pause or skip now.");
                        }
                    }
                }

                Thread.sleep(500);    //0.5 sec                                                                    // Delay before next check - it will give enough time to change the state of clip
            }

        }
        catch (InterruptedException exception)
        {
            System.out.println("Thread interrupted.");
        }
        return "true";

    }



    // method to properly close the clip in order not to resource leak
    public void closeClip()
    {
        if(clip != null)
        {
            clip.stop();
            clip.close();
        }
    }


}
