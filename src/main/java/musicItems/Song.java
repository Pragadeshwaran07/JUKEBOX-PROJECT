package musicItems;


// represents a song


public class Song implements Track
{

    private String title;
    private String album;
    private String artist;
    private String genre;
    private String duration;
    private String dateAdded;
    private String filePath;



    // parameterized constructor
    public Song(String title, String album, String artist, String genre, String duration, String dateAdded, String filePath)
    {
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
        this.dateAdded = dateAdded;
        this.filePath = filePath;
    }



    // getters and setters method
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getAlbum() {return album;}
    public void setAlbum(String album) {this.album = album;}
    public String getArtist() {return artist;}
    public void setArtist(String artist) {this.artist = artist;}
    public String getGenre() {return genre;}
    public void setGenre(String genre) {this.genre = genre;}
    public String getDuration() {return duration;}
    public void setDuration(String duration) {this.duration = duration;}
    public String getDateAdded() {return dateAdded;}
    public void setDateAdded(String dateAdded) {this.dateAdded = dateAdded;}
    public String getFilePath() {return filePath;}
    public void setFilePath(String filePath) {this.filePath = filePath;}




    // to detect and prevent duplicates data
    @Override
    public int hashCode()
    {
        int result = title.toLowerCase().hashCode();
        result = result + artist.toLowerCase().hashCode();
        return result;
    }
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Song song = (Song) obj;
        return title.equalsIgnoreCase(song.title) && artist.equalsIgnoreCase(song.artist);
    }



    // toString()
    @Override
    public String toString()
    {

        return String.format("Title : %-25s | Album : %-25s | Artist : %-25s | Genre : %-10s | Duration : %-10s | Date Added : %-15s" + "%n------------------------------------------------------------------------------------------------------------------------------------------------------%n",
                title, album, artist, genre, duration, dateAdded);

    }


}
