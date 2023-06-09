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
import demo.entity.PlaylistName;
import demo.entity.TopSong;
import demo.repository.PlaylistNameRepository;
import demo.repository.TopSongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static demo.service.AuthService.spotifyApi;

/**
 * SpotifyService class - contains methods that make calls to the Spotify API
 */

@Service
public class SpotifyService {

    @Autowired
    PlaylistNameRepository playlistNameRepository;

    @Autowired
    TopSongRepository topSongRepository;
    String timeRange = "medium_term";

    public PlaylistSimplified[] getCurrentUsersPlaylists(String code) { // get the current user's playlists
        spotifyApi.setAccessToken(code);
        List<PlaylistSimplified> allPlaylists = new ArrayList<>();
        int offset = 0;
        int limit = 50;
        try {
            Paging<PlaylistSimplified> playlists;
            do {
                GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest = spotifyApi.getListOfCurrentUsersPlaylists()
                        .limit(limit)
                        .offset(offset)
                        .build();
                playlists = getListOfCurrentUsersPlaylistsRequest.execute();
                PlaylistSimplified[] playlistItems = playlists.getItems();
                allPlaylists.addAll(Arrays.asList(playlistItems));
                offset += limit;
            } while (playlists.getNext() != null);
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return allPlaylists.toArray(new PlaylistSimplified[0]);
    }


    public Artist[] getFollowedArtists(String code) { // get the current user's followed artists
        ModelObjectType type = ModelObjectType.ARTIST;
        spotifyApi.setAccessToken(code);
        GetUsersFollowedArtistsRequest getUsersFollowedArtistsRequest = spotifyApi.getUsersFollowedArtists(type).limit(50).build();

        List<Artist> allFollowedArtists = new ArrayList<>();
        PagingCursorbased<Artist> artistPagingCursorbased = null;

        try {
            do {
                artistPagingCursorbased = getUsersFollowedArtistsRequest.execute();
                Artist[] artists = artistPagingCursorbased.getItems();
                allFollowedArtists.addAll(Arrays.asList(artists));
                // check if there are more pages to fetch
                if (artistPagingCursorbased.getNext() != null) {
                    System.out.println("Next page: " + artistPagingCursorbased.getNext());
                    getUsersFollowedArtistsRequest = spotifyApi.getUsersFollowedArtists(type)
                            .after(getCursorFromNextLink(artistPagingCursorbased.getNext()))
                            .build();
                }
            } while (artistPagingCursorbased.getNext() != null);
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return allFollowedArtists.toArray(new Artist[0]);
    }

    private String getCursorFromNextLink(String nextLink) {
        // Extracts the cursor value from the nextLink
        String[] parts = nextLink.split("after=");
        if (parts.length > 1) {
            return parts[1];
        }
        return null;
    }


    public Artist[] getUsersTopArtists(String code, String timeRange) {// get the current user's top artists
        spotifyApi.setAccessToken(code);
        setTimeRange(timeRange);
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

    public Artist[] getTopArtistsRelatedArtists(String code) { // used to create a playlist of related artists
        // get the top 10 artists
        Artist[] topArtists = getUsersTopArtists(code, timeRange);
        // for each artist, get their top 5 related artists
        int numberOfRelatedArtists = 5;
        // array to store the related artists
        List<Artist> relatedArtists = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            // get related artists for the current top artist
            GetArtistsRelatedArtistsRequest getArtistsRelatedArtistsRequest = spotifyApi.getArtistsRelatedArtists(topArtists[i].getId()).build();
            try {
                final Artist[] artists = getArtistsRelatedArtistsRequest.execute();
                // limit the number of related artists to 5
                int numArtistsToAdd = Math.min(numberOfRelatedArtists, artists.length);
                relatedArtists.addAll(Arrays.asList(artists).subList(0, numArtistsToAdd));
            } catch (IOException | SpotifyWebApiException e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return relatedArtists.toArray(new Artist[0]);
    }


    public PlayHistory[] getTrackHistory(String code) {// get the last 50 played tracks
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

    public Track[] getUsersTopTracks(String code, String timeRange) {// get the current user's top tracks
        spotifyApi.setAccessToken(code);
        setTimeRange(timeRange);
        GetUsersTopTracksRequest getUsersTopTracksRequest = spotifyApi.getUsersTopTracks().time_range(timeRange).limit(50).build();
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

    public Track[] getArtistsTopTracks(String artistId) {// get an artist's top tracks
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

    public Playlist createPlaylistWithTopTracks(String code) throws IOException, SpotifyWebApiException { // create a playlist with the user's top tracks
        spotifyApi.setAccessToken(code);
        Track[] topTracks = getUsersTopTracks(code, timeRange);
        return createNewPlaylist(topTracks);
    }

    public Playlist createPlaylistFromRelatedArtists(String code) throws IOException, SpotifyWebApiException {// create a playlist with recommended tracks
        Artist[] relatedArtists = getTopArtistsRelatedArtists(code);
        Track[] relatedTracks = new Track[0];
        for (Artist artist : relatedArtists) {
            Track track = getArtistsTopTracks(artist.getId())[0];
            Track[] newTracks = new Track[relatedTracks.length + 1];
            // copy into the new array
            System.arraycopy(relatedTracks, 0, newTracks, 0, relatedTracks.length);
            // add the new element to the end of the new array and update relatedTracks
            newTracks[newTracks.length - 1] = track;
            relatedTracks = newTracks;
        }
        System.out.println("Length of related tracks " + relatedTracks.length);
        return createNewPlaylist(relatedTracks);
    }


    public List<Map.Entry<String, Integer>> getUsersTopGenres(String code, String timeRange) throws IOException, SpotifyWebApiException {// get the user's top genres
        Track[] topTracks = getUsersTopTracks(code, timeRange);
        setTimeRange(timeRange);
        Map<String, Integer> genreCounts = new HashMap<>();
        for (Track track : topTracks) {
            Artist artist = spotifyApi.getArtist(track.getArtists()[0].getId()).build().execute();
            for (String genre : artist.getGenres()) {
                genreCounts.put(genre, genreCounts.getOrDefault(genre, 0) + 1);
            }
        }
        List<Map.Entry<String, Integer>> sortedGenres = new ArrayList<>(genreCounts.entrySet());
        sortedGenres.sort(Map.Entry.<String, Integer>comparingByValue().reversed());
        return sortedGenres.subList(0, Math.min(sortedGenres.size(), 25));

    }


    public Playlist createNewPlaylist(Track[] listOfTracks) throws IOException, SpotifyWebApiException {// create a new playlist with the given tracks
        String randomPlaylistName = "New playlist"; // set a default name for the playlist
        // get a random playlist name from the database
        System.out.println(playlistNameRepository);
        List<PlaylistName> playlistNames = playlistNameRepository.findAll();
        if (!playlistNames.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(playlistNames.size());
            randomPlaylistName = playlistNames.get(randomIndex).getName();
            System.out.println("Random Playlist Name: " + randomPlaylistName);
        } else {
            System.out.println("No playlist names available in the database.");
        }
        CreatePlaylistRequest createPlaylistRequest = spotifyApi.createPlaylist(getUserId(), randomPlaylistName).public_(false).build();
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

    public double compareTopSongs(String code, String timeRange) {// compare the user's top songs to the database
        // get the user's top songs and fetch them from the database
        setTimeRange(timeRange);
        Track[] userTopSongs = getUsersTopTracks(code, timeRange);
        List<TopSong> databaseSongs = topSongRepository.findAll();
        // calculate the number of matching songs
        int numMatches = 0;
        for (Track userSong : userTopSongs) {
            for (TopSong databaseSong : databaseSongs) {
                if (userSong.getName().equals(databaseSong.getArtistName())) {
                    numMatches++;
                    break; // move to the next user song
                }
            }
        }
        // calculate the percentage of matches
        return (double) numMatches / userTopSongs.length * 100.0;
    }

    public String getUserId() {// get the user's id
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

    public String retrieveAccessToken(String authorizationCode) throws IOException, SpotifyWebApiException {// method to retrieve the access token
        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(authorizationCode).build();
        AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();
        return authorizationCodeCredentials.getAccessToken();
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }
}
