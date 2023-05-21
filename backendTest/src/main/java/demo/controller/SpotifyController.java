package demo.controller;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.PlayHistory;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.Track;
import demo.model.ArtistResponse;
import demo.model.PlayHistoryResponse;
import demo.model.PlaylistResponse;
import demo.model.TrackResponse;
import demo.service.AuthService;
import demo.service.SpotifyService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class SpotifyController {
    private final SpotifyService spotifyService = new SpotifyService();
    private String authorizationCode;
    private String accessToken;

    @GetMapping("/login")
    @ResponseBody
    public RedirectView login() {
        String authorizationUri = String.valueOf(AuthService.getAuthorizationCodeUri());
        return new RedirectView(authorizationUri);
    }

    @GetMapping("/callback")
    public RedirectView callback(@RequestParam("code") String code) throws IOException, SpotifyWebApiException {
        setAuthorizationCode(code);
        accessToken = spotifyService.retrieveAccessToken(authorizationCode);
        setAccessToken(accessToken);
        return new RedirectView("http://localhost:3001/stats");
    }

    @GetMapping("/playlists")
    public List<PlaylistResponse> getPlaylists() throws IOException, SpotifyWebApiException {
        PlaylistSimplified[] playlists = spotifyService.getCurrentUsersPlaylists(accessToken);
        return PlaylistResponse.buildResponse(Arrays.asList(playlists));
    }

    @GetMapping("/artists")
    public List<ArtistResponse> getTopArtists() throws IOException, SpotifyWebApiException {
        String timeRange = "medium_term";
        Artist[] artists = spotifyService.getUsersTopArtists(accessToken,timeRange);
        return ArtistResponse.buildResponse(artists);
    }

    @GetMapping("/related-artists")
    public List<ArtistResponse> getRelatedArtists() throws IOException, SpotifyWebApiException {
        Artist[] artists = spotifyService.getTopArtistsRelatedArtists(accessToken);
        return ArtistResponse.buildResponse(artists);
    }

    @GetMapping("/followed-artists")
    public List<ArtistResponse> getFollowedArtists() throws IOException, SpotifyWebApiException {
        Artist[] artists = spotifyService.getFollowedArtists(accessToken);
        return ArtistResponse.buildResponse(artists);
    }

    @GetMapping("/track-history")
    public List<PlayHistoryResponse> getTrackHistory() throws IOException, SpotifyWebApiException {
        PlayHistory[] history = spotifyService.getTrackHistory(accessToken);
        return PlayHistoryResponse.buildResponse(history);
    }

    @GetMapping("/top-tracks")
    public List<TrackResponse> getTopTracks() throws IOException, SpotifyWebApiException {
        Track[] tracks = spotifyService.getUsersTopTracks(accessToken);
        return TrackResponse.buildResponse(tracks);
    }

    @GetMapping("/top-genres")
    public List<Map.Entry<String, Integer>> getTopGenres() throws IOException, SpotifyWebApiException {
        return spotifyService.getUsersTopGenres(accessToken);
    }

    @GetMapping("/create-playlist-with-top-tracks")
    public String getPlaylistWithTopTracks() throws IOException, SpotifyWebApiException {
        String playlistId = String.valueOf(spotifyService.createPlaylistWithTopTracks(accessToken));
        return "Created playlist with ID: " + playlistId;
    }

    @GetMapping("/create-playlist-with-related-artists")
    public String getPlaylistWithTopArtists() throws IOException, SpotifyWebApiException {
        String playlistId = String.valueOf(spotifyService.createPlaylistFromRelatedArtists(accessToken));
        return "Created playlist with ID: " + playlistId;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public void setAccessToken(String accessToken) {
        System.out.println("Access token: " + accessToken);
        this.accessToken = accessToken;
    }
}