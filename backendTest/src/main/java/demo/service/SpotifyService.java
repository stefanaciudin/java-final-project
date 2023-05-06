package demo.service;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PagingCursorbased;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.requests.data.follow.GetUsersFollowedArtistsRequest;
import com.wrapper.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import com.wrapper.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static demo.service.AuthService.setToken;
import static demo.service.AuthService.spotifyApi;

@Service
public class SpotifyService {
    public PlaylistSimplified[] getCurrentUsersPlaylists(String code) throws IOException, SpotifyWebApiException {
        setToken(code);
        Paging<PlaylistSimplified> playlists = null;
        GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest = spotifyApi.getListOfCurrentUsersPlaylists().limit(50).offset(0).build();
        try {
            playlists = getListOfCurrentUsersPlaylistsRequest.execute();
            System.out.println("Total: " + playlists.getTotal());
            return playlists.getItems();
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return new PlaylistSimplified[0];
    }

    public Artist[] getFollowedArtists(String code) throws IOException, SpotifyWebApiException {
        ModelObjectType type = ModelObjectType.ARTIST;
        setToken(code);
        GetUsersFollowedArtistsRequest getUsersFollowedArtistsRequest = spotifyApi.getUsersFollowedArtists(type).build();
        try {
            final PagingCursorbased<Artist> artistPagingCursorbased = getUsersFollowedArtistsRequest.execute();
            System.out.println("Total: " + artistPagingCursorbased.getTotal());
            return artistPagingCursorbased.getItems();
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return new Artist[0];
    }


    public Artist[] getUsersTopArtists(String code) throws IOException, SpotifyWebApiException {
        setToken(code);
        GetUsersTopArtistsRequest getUsersTopArtistsRequest = spotifyApi.getUsersTopArtists().time_range("long_term")
                .limit(15)
                .build();
        try {
            final Paging<Artist> artists = getUsersTopArtistsRequest.execute();
            System.out.println("Total artists: " + artists.getTotal());
            return artists.getItems();
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return new Artist[0];
    }

}
