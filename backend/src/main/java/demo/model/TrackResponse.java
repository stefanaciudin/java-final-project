package demo.model;

import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * TrackResponse - models a response for tracks
 */
public class TrackResponse {
    private String name;
    private ArtistSimplified[] artists;
    private String imageUrl;
    private String songUrl;


    public static List<TrackResponse> buildResponse(Track[] tracks) {
        List<TrackResponse> response = new ArrayList<>();
        for (Track track : tracks) {
            TrackResponse trackResponse = new TrackResponse();
            trackResponse.setName(track.getName());
            trackResponse.setArtists(track.getArtists());
            trackResponse.setImageUrl(track.getAlbum().getImages()[0].getUrl()); // set the image url for the given artist
            trackResponse.setSongUrl(transformUriToUrl(track.getUri()));
            response.add(trackResponse);
        }
        return response;
    }

    public static String transformUriToUrl(String uri) {
        String[] uriParts = uri.split(":");
        return "http://open.spotify.com/track/" + uriParts[2];
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }
}
