package demo.model;

import com.wrapper.spotify.model_objects.specification.Artist;

import java.util.ArrayList;
import java.util.List;

public class ArtistResponse {
    private String name;

    public static List<ArtistResponse> buildResponse(Artist[] artists) {
        List<ArtistResponse> response = new ArrayList<>();
        for (Artist artist : artists) {
            ArtistResponse artistResponse = new ArtistResponse();
            artistResponse.setName(artist.getName());
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
}
