package demo.model;

import com.wrapper.spotify.model_objects.specification.Artist;

import java.util.ArrayList;
import java.util.List;

public class ArtistResponse {
    private String name;

    private String[] genres;

    public static List<ArtistResponse> buildResponse(Artist[] artists) {
        List<ArtistResponse> response = new ArrayList<>();
        for (Artist artist : artists) {
            ArtistResponse artistResponse = new ArtistResponse();
            artistResponse.setName(artist.getName());
            artistResponse.setGenres(artist.getGenres());
            response.add(artistResponse);
        }
        return response;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }
}
