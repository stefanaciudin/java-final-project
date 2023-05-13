package demo.service;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.data.artists.GetArtistsRelatedArtistsRequest;
import com.wrapper.spotify.requests.data.artists.GetArtistsTopTracksRequest;
import com.wrapper.spotify.requests.data.follow.GetUsersFollowedArtistsRequest;
import com.wrapper.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import com.wrapper.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;
import com.wrapper.spotify.requests.data.playlists.CreatePlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

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
        GetUsersTopArtistsRequest getUsersTopArtistsRequest = spotifyApi.getUsersTopArtists().limit(50).time_range("long_term").build();
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

    public Artist[] getTopArtistsRelatedArtists(String code) throws IOException, SpotifyWebApiException {
        // get related artists for the top 2 artists
        Artist[] topArtists = getUsersTopArtists(code);
        Artist[] relatedArtists = new Artist[0];
        for (int i = 0; i < 2; i++) {
            GetArtistsRelatedArtistsRequest getArtistsRelatedArtistsRequest = spotifyApi.getArtistsRelatedArtists(topArtists[i].getId()).build();
            try {
                final Artist[] artists = getArtistsRelatedArtistsRequest.execute();
                //System.out.println("Total related artists: " + artists.length);
                relatedArtists = Arrays.copyOf(relatedArtists, relatedArtists.length + artists.length);
                System.arraycopy(artists, 0, relatedArtists, relatedArtists.length - artists.length, artists.length);
            } catch (IOException | SpotifyWebApiException e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return relatedArtists;
    }

    public Track[] getUsersTopTracks(String code) throws IOException, SpotifyWebApiException {
        setToken(code);
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
        //setToken(code);
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
        //setToken(code);
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
        CreatePlaylistRequest createPlaylistRequest = spotifyApi.createPlaylist(getUserId(), "New playlist").build();
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

}
