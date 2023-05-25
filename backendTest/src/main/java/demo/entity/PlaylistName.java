package demo.entity;

import javax.persistence.*;

@Table(name = "playlist_names")
@Entity
public class PlaylistName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    public PlaylistName() {
    }

    public PlaylistName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
