package musicItems;

import java.util.List;


// represents a podcast


public class Podcast
{

    private String title;
    private String celebrity;
    private String genre;
    private String datePublished;
    private String description;
    private List<PodcastEpisode> episodes;



    // parameterized constructor
    public Podcast(String title, String celebrity, String genre, String datePublished, String description, List<PodcastEpisode> episodes)
    {
        this.title = title;
        this.celebrity = celebrity;
        this.genre = genre;
        this.datePublished = datePublished;
        this.description = description;
        this.episodes = episodes;
    }



    // getters and setters method
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getCelebrity() {return celebrity;}
    public void setCelebrity(String celebrity) {this.celebrity = celebrity;}
    public String getGenre() {return genre;}
    public void setGenre(String genre) {this.genre = genre;}
    public String getDatePublished() {return datePublished;}
    public void setDatePublished(String datePublished) {this.datePublished = datePublished;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public List<PodcastEpisode> getEpisodes() {return episodes;}
    public void setEpisodes(List<PodcastEpisode> episodes) {this.episodes = episodes;}



    // to detect and prevent duplicates data
    @Override
    public int hashCode()
    {
        int result = title.toLowerCase().hashCode();
        result = result + celebrity.toLowerCase().hashCode();
        return result;
    }
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Podcast podcast = (Podcast) obj;
        return title.equalsIgnoreCase(podcast.title) && celebrity.equalsIgnoreCase(podcast.celebrity);
    }



    // toString()
    @Override
    public String toString() {

        StringBuilder podcastDetails = new StringBuilder();

        podcastDetails.append(String.format("\nTitle           : %s%n", title));
        podcastDetails.append(String.format("Celebrity       : %s%n", celebrity));
        podcastDetails.append(String.format("Genre           : %s%n", genre));
        podcastDetails.append(String.format("Date Published  : %s%n", datePublished));
        podcastDetails.append(String.format("Total Episodes  : %d%n", episodes.size()));
        podcastDetails.append(String.format("About           : %s%n", description));

        if(episodes.isEmpty())
        {
            podcastDetails.append("No episodes available !");
        }
        else
        {
            int count = 1;
            for (PodcastEpisode episode : episodes)
            {
                podcastDetails.append("\nEpisode " + count++ + " :- ").append(episode);
            }
        }

        return podcastDetails.toString();
    }


}
