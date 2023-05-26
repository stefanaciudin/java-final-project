package model;

public class TopSong {

    private long id;

    private static String songName;

    private static String artistName;

    public TopSong(String songName, String artistName) {
        TopSong.songName = songName;
        TopSong.artistName = artistName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        TopSong.songName = songName;
    }

    public static String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        TopSong.artistName = artistName;
    }

}
