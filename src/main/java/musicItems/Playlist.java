package musicItems;

import java.util.ArrayList;
import java.util.List;

public class Playlist
{

    private String name;
    private List<Track> tracks;                                                                                         // list that stores song or podcast or both


    // constructor
    public Playlist(String name)
    {
        this.name = name;
        this.tracks = new ArrayList<>();
    }



    // getters and setters method
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public List<Track> getTracks() {return tracks;}
    public void setTracks(List<Track> tracks) {this.tracks = tracks;}



    // toString()
    @Override
    public String toString()
    {

        StringBuilder playlistContent = new StringBuilder(" Playlist : " + name );

        if (tracks.isEmpty())
        {
            playlistContent.append("No tracks available !");
        }
        else
        {
            int count = 1;
            for (Track track : tracks)
            {
                playlistContent.append(count++).append(". ").append(track.getTitle()).append(" | ").append(track.getDuration()).append("\n");
            }
        }

        return playlistContent.toString();
    }


}
