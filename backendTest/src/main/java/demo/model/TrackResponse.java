package demo.model;

import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Track;

import java.util.ArrayList;
import java.util.List;

public class TrackResponse {
    private String name;
    private ArtistSimplified[] artists;


    public static List<TrackResponse> buildResponse(Track[] tracks) {
        List<TrackResponse> response = new ArrayList<>();
        for (Track track : tracks) {
            TrackResponse trackResponse = new TrackResponse();
            trackResponse.setName(track.getName());
            trackResponse.setArtists(track.getArtists());
            response.add(trackResponse);
        }
        return response;
    }

    public ArtistSimplified[] getArtists() {
        return artists;
    }

    public void setArtists(ArtistSimplified[] artists) {
        this.artists = artists;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}