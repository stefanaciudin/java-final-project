package demo.model;

import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;

import java.util.ArrayList;
import java.util.List;

public class PlaylistResponse {
    private String name;

    private String imageUrl;

    public static List<PlaylistResponse> buildResponse(List<PlaylistSimplified> playlists){
        List<PlaylistResponse> response = new ArrayList<>();
        for (PlaylistSimplified playlist : playlists) {
            PlaylistResponse playlistResponse = new PlaylistResponse();
            playlistResponse.setName(playlist.getName());
            playlistResponse.setImageUrl(playlist.getImages()[0].getUrl());
            response.add(playlistResponse);
        }
        return response;
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
}