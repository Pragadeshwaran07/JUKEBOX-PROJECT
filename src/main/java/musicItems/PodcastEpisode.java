package musicItems;


// represent episode in a podcast


public class PodcastEpisode implements Track
{

    private String episodeTitle;
    private String episodeDescription;
    private String duration;
    private String episodeFilePath;



    // parameterized constructor
    public PodcastEpisode(String episodeTitle, String episodeDescription, String duration, String episodeFilePath)
    {
        this.episodeTitle = episodeTitle;
        this.episodeDescription = episodeDescription;
        this.duration = duration;
        this.episodeFilePath = episodeFilePath;
    }



    // getters and setters method
    public String getEpisodeTitle() {return episodeTitle;}
    public void setEpisodeTitle(String episodeTitle) {this.episodeTitle = episodeTitle;}
    public String getEpisodeDescription() {return episodeDescription;}
    public void setEpisodeDescription(String episodeDescription) {this.episodeDescription = episodeDescription;}
    public String getDuration() {return duration;}
    public void setDuration(String duration) {this.duration = duration;}
    public String getEpisodeFilePath() {return episodeFilePath;}
    public void setEpisodeFilePath(String episodeFilePath) {this.episodeFilePath = episodeFilePath;}



    // implementing Track interface methods
    @Override
    public String getTitle() {return episodeTitle;}
    @Override
    public String getFilePath() {return episodeFilePath;}



    // toString()
    @Override
    public String toString() {

        return String.format(
                " Title      : %s%n" +
                        " About      : %s%n" +
                        " Duration   : %s%n" +
                        "------------------------------------------------------------------------------------------------------------------------------------------------------%n",
                getEpisodeTitle(),
                getEpisodeDescription(),
                getDuration()
        );
    }


}
