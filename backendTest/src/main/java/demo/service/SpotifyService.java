package demo.service;

import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.data.follow.GetUsersFollowedArtistsRequest;
import com.wrapper.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import com.wrapper.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;
import com.wrapper.spotify.requests.data.playlists.CreatePlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        GetUsersTopArtistsRequest getUsersTopArtistsRequest = spotifyApi
                .getUsersTopArtists()
                .limit(50)
                .time_range("long_term")
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

    public Track[] getUsersTopTracks(String code) throws IOException, SpotifyWebApiException {
        setToken(code);
        GetUsersTopTracksRequest getUsersTopTracksRequest = spotifyApi
                .getUsersTopTracks()
                .time_range("long_term")
                .limit(50)
                .build();
        try {
            final Paging<Track> tracks = getUsersTopTracksRequest.execute();
            System.out.println("Total tracks: " + tracks.getTotal());
            return tracks.getItems();
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();

        }
        return new Track[0];
    }

    public Playlist createPlaylistWithTopTracks(String code) throws IOException, SpotifyWebApiException {
        //setToken(code);
        Track[] topTracks = getUsersTopTracks(code);
        CreatePlaylistRequest createPlaylistRequest = spotifyApi.createPlaylist(getUserId(), "Playlist with top tracks long term").build();
        Playlist playlist = null;
        try {
            playlist = createPlaylistRequest.execute();

            System.out.println("Playlist name: " + playlist.getName());
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println(playlist.getId());

        // add tracks to playlist
        spotifyApi.addTracksToPlaylist(playlist.getId(), Arrays.stream(topTracks).map(Track::getUri).toArray(String[]::new)).build().execute();
        return playlist;
    }

    public List<String> getUsersTopGenres(String code) throws IOException, SpotifyWebApiException {
        Track[] topTracks = getUsersTopTracks(code);

        Map<String, Integer> genreCounts = new HashMap<>();
        for (Track track : topTracks) {
            Artist artist = spotifyApi.getArtist(track.getArtists()[0].getId()).build().execute();
            for (String genre : artist.getGenres()) {
                genreCounts.put(genre, genreCounts.getOrDefault(genre, 0) + 1);
            }
        }
        return genreCounts.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

    }

    public String getUserId() {
        GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile().build();
        try {
            final User user = getCurrentUsersProfileRequest.execute();
            return user.getId();
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
