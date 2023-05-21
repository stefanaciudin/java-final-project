package demo.service;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.data.artists.GetArtistsRelatedArtistsRequest;
import com.wrapper.spotify.requests.data.artists.GetArtistsTopTracksRequest;
import com.wrapper.spotify.requests.data.follow.GetUsersFollowedArtistsRequest;
import com.wrapper.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import com.wrapper.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;
import com.wrapper.spotify.requests.data.player.GetCurrentUsersRecentlyPlayedTracksRequest;
import com.wrapper.spotify.requests.data.playlists.CreatePlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static demo.service.AuthService.spotifyApi;

@Service
public class SpotifyService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public PlaylistSimplified[] getCurrentUsersPlaylists(String code) {
        spotifyApi.setAccessToken(code);
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

    public Artist[] getFollowedArtists(String code) {
        ModelObjectType type = ModelObjectType.ARTIST;
        spotifyApi.setAccessToken(code);
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

    public Artist[] getUsersTopArtists(String code, String timeRange) {
        spotifyApi.setAccessToken(code);
        GetUsersTopArtistsRequest getUsersTopArtistsRequest = spotifyApi.getUsersTopArtists().limit(50).time_range(timeRange).build();
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

    public Artist[] getTopArtistsRelatedArtists(String code) {
        // get related artists for the top 2 artists
        String timeRange = "medium_term";
        Artist[] topArtists = getUsersTopArtists(code, timeRange);
        Artist[] relatedArtists = new Artist[0];
        for (int i = 0; i < 2; i++) {
            GetArtistsRelatedArtistsRequest getArtistsRelatedArtistsRequest = spotifyApi.getArtistsRelatedArtists(topArtists[i].getId()).build();
            try {
                final Artist[] artists = getArtistsRelatedArtistsRequest.execute();
                relatedArtists = Arrays.copyOf(relatedArtists, relatedArtists.length + artists.length);
                System.arraycopy(artists, 0, relatedArtists, relatedArtists.length - artists.length, artists.length);
            } catch (IOException | SpotifyWebApiException e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return relatedArtists;
    }

    public PlayHistory[] getTrackHistory(String code) {
        spotifyApi.setAccessToken(code);
        GetCurrentUsersRecentlyPlayedTracksRequest getUsersRecentlyPlayedTracksRequest = spotifyApi.getCurrentUsersRecentlyPlayedTracks().limit(50).build();
        try {
            final PagingCursorbased<PlayHistory> playHistoryCursorBasedPaging = getUsersRecentlyPlayedTracksRequest.execute();
            return playHistoryCursorBasedPaging.getItems();
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return new PlayHistory[0];
    }

    public Track[] getUsersTopTracks(String code) {
        spotifyApi.setAccessToken(code);
        GetUsersTopTracksRequest getUsersTopTracksRequest = spotifyApi.getUsersTopTracks().time_range("long_term").limit(50).build();
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

    public Track[] getArtistsTopTracks(String artistId) {
        GetArtistsTopTracksRequest getArtistsTopTracksRequest = spotifyApi.getArtistsTopTracks(artistId, CountryCode.US).build();
        try {
            final Track[] tracks = getArtistsTopTracksRequest.execute();
            System.out.println("Total tracks: " + tracks.length);
            return tracks;
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return new Track[0];
    }

    public Playlist createPlaylistWithTopTracks(String code) throws IOException, SpotifyWebApiException {
        spotifyApi.setAccessToken(code);
        Track[] topTracks = getUsersTopTracks(code);
        return createNewPlaylist(topTracks);
    }

    public Playlist createPlaylistFromRelatedArtists(String code) throws IOException, SpotifyWebApiException {
        Artist[] relatedArtists = getTopArtistsRelatedArtists(code);
        Track[] relatedTracks = new Track[0];
        for (Artist artist : relatedArtists) {
            Track track = getArtistsTopTracks(artist.getId())[0];
            // create a new array with length one greater than relatedTracks
            Track[] newTracks = new Track[relatedTracks.length + 1];
            // copy all elements of relatedTracks into the new array
            System.arraycopy(relatedTracks, 0, newTracks, 0, relatedTracks.length);
            // add the new element to the end of the new array
            newTracks[newTracks.length - 1] = track;
            // update relatedTracks to point to the new array
            relatedTracks = newTracks;
        }
        System.out.println("Length of related tracks " + relatedTracks.length);
        return createNewPlaylist(relatedTracks);
    }


    public List<Map.Entry<String, Integer>> getUsersTopGenres(String code) throws IOException, SpotifyWebApiException {
        Track[] topTracks = getUsersTopTracks(code);

        Map<String, Integer> genreCounts = new HashMap<>();
        for (Track track : topTracks) {
            Artist artist = spotifyApi.getArtist(track.getArtists()[0].getId()).build().execute();
            for (String genre : artist.getGenres()) {
                genreCounts.put(genre, genreCounts.getOrDefault(genre, 0) + 1);
            }
        }
        List<Map.Entry<String, Integer>> sortedGenres = new ArrayList<>(genreCounts.entrySet());
        sortedGenres.sort(Map.Entry.<String, Integer>comparingByValue().reversed());
        return sortedGenres.subList(0, Math.min(sortedGenres.size(), 10));

    }


    public Playlist createNewPlaylist(Track[] listOfTracks) throws IOException, SpotifyWebApiException {
        System.out.println(Arrays.toString(listOfTracks));

        // Query to fetch a random playlist name from the database
        String query = "SELECT name FROM playlist_names ORDER BY RAND() LIMIT 1";

        // Execute the query and retrieve the random playlist name
        String playlistName = jdbcTemplate.queryForObject(query, String.class);
        System.out.println("Playlist name: " + playlistName);

        CreatePlaylistRequest createPlaylistRequest = spotifyApi.createPlaylist(getUserId(), playlistName).build();
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
        spotifyApi.addTracksToPlaylist(playlist.getId(), Arrays.stream(listOfTracks).map(Track::getUri).toArray(String[]::new)).build().execute();
        return playlist;
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

    public String retrieveAccessToken(String authorizationCode) throws IOException, SpotifyWebApiException {
        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(authorizationCode).build();
        AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();
        return authorizationCodeCredentials.getAccessToken();
    }

}
