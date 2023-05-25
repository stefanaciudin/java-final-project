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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class SpotifyController {
    @Autowired
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
    public List<PlaylistResponse> getPlaylists() {
        PlaylistSimplified[] playlists = spotifyService.getCurrentUsersPlaylists(accessToken);
        return PlaylistResponse.buildResponse(Arrays.asList(playlists));
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/match-percentage")
    public Double getPercentage(@RequestParam String timeRange) {
        return spotifyService.compareTopSongs(accessToken, timeRange);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/top-artists")
    public List<ArtistResponse> getTopArtists(@RequestParam String timeRange) {
        Artist[] artists = spotifyService.getUsersTopArtists(accessToken, timeRange);
        return ArtistResponse.buildResponse(artists);
    }

    @GetMapping("/related-artists")
    public List<ArtistResponse> getRelatedArtists() {
        Artist[] artists = spotifyService.getTopArtistsRelatedArtists(accessToken);
        return ArtistResponse.buildResponse(artists);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/followed-artists")
    public List<ArtistResponse> getFollowedArtists() {
        Artist[] artists = spotifyService.getFollowedArtists(accessToken);
        return ArtistResponse.buildResponse(artists);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/track-history")
    public List<PlayHistoryResponse> getTrackHistory() {
        PlayHistory[] history = spotifyService.getTrackHistory(accessToken);
        return PlayHistoryResponse.buildResponse(history);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/top-tracks")
    public List<TrackResponse> getTopTracks(@RequestParam String timeRange) {
        Track[] tracks = spotifyService.getUsersTopTracks(accessToken, timeRange);
        return TrackResponse.buildResponse(tracks);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/top-genres")
    public List<Map.Entry<String, Integer>> getTopGenres(@RequestParam String timeRange) throws IOException, SpotifyWebApiException {
        return spotifyService.getUsersTopGenres(accessToken, timeRange);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/create-playlist-with-top-tracks")
    public String getPlaylistWithTopTracks() throws IOException, SpotifyWebApiException {
        String playlistId = String.valueOf(spotifyService.createPlaylistWithTopTracks(accessToken));
        return "Created playlist with ID: " + playlistId;
    }

    @CrossOrigin(origins = "http://localhost:3001")
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