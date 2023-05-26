package demo.entity;

import javax.persistence.*;

/**
 * TopSong - entity class for a top song
 */
@Entity
@Table(name = "top_songs")
public class TopSong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "artist_name")
    private String artistName;

    @Column(name = "song_name")
    private String songName;

    public TopSong() {
    }

    public Long getId() {
        return id;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getSongName() {
        return songName;
    }
}
