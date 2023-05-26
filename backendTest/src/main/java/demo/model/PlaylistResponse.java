package demo.model;

import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;

import java.util.ArrayList;
import java.util.List;

/**
 * PlaylistResponse - models a response for playlists
 */

public class PlaylistResponse {
    private String name;
    private String imageUrl;
    private String playlistUrl;

    public static List<PlaylistResponse> buildResponse(List<PlaylistSimplified> playlists){
        List<PlaylistResponse> response = new ArrayList<>();
        for (PlaylistSimplified playlist : playlists) {
            PlaylistResponse playlistResponse = new PlaylistResponse();
            playlistResponse.setName(playlist.getName());
            playlistResponse.setImageUrl(playlist.getImages()[0].getUrl());
            playlistResponse.setPlaylistUrl(transformUriToUrl(playlist.getUri()));
            response.add(playlistResponse);
        }
        return response;
    }

    public static String transformUriToUrl(String uri) {
        String[] uriParts = uri.split(":");
        return "http://open.spotify.com/playlist/" + uriParts[2];
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

    public void setPlaylistUrl(String playlistUrl) {
        this.playlistUrl = playlistUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPlaylistUrl() {
        return playlistUrl;
    }
}