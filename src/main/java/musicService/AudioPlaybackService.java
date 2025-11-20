package musicService;

public interface AudioPlaybackService
{

    String play(String title);
    String pause(String title);
    String resume(String title);
    String stop(String title);
    String repeat(String title);
    String restart(String title);
    String jumpToSpecificTime(int minute, int second, String title);


}
