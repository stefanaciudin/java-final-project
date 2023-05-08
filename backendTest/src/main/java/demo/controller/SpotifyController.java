package demo.controller;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.Track;
import demo.model.ArtistResponse;
import demo.model.PlaylistResponse;
import demo.model.TrackResponse;
import demo.service.AuthService;
import demo.service.SpotifyService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
public class SpotifyController {
    private final SpotifyService spotifyService = new SpotifyService();
    private String authorizationCode;

    @GetMapping("/login")
    @ResponseBody
    public RedirectView login() {
        String authorizationUri = String.valueOf(AuthService.getAuthorizationCodeUri());
        return new RedirectView(authorizationUri);
    }

    @GetMapping("/callback")
    public RedirectView callback(@RequestParam("code") String code) {
        setAuthorizationCode(code);
        System.out.println("Code: " + code);
        return new RedirectView("/artists");
    }

    @GetMapping("/playlists")
    public List<PlaylistResponse> getPlaylists() throws IOException, SpotifyWebApiException {
        PlaylistSimplified[] playlists = spotifyService.getCurrentUsersPlaylists(authorizationCode);
        return PlaylistResponse.buildResponse(Arrays.asList(playlists));
    }

    @GetMapping("/artists")
    public List<ArtistResponse> getTopArtists() throws IOException, SpotifyWebApiException {
        Artist[] artists = spotifyService.getUsersTopArtists(authorizationCode);
        return ArtistResponse.buildResponse(artists);
    }

    @GetMapping("/followed-artists")
    public List<ArtistResponse> getFollowedArtists() throws IOException, SpotifyWebApiException {
        Artist[] artists = spotifyService.getFollowedArtists(authorizationCode);
        return ArtistResponse.buildResponse(artists);
    }

    @GetMapping("/top-tracks")
    public List<TrackResponse> getTopTracks() throws IOException, SpotifyWebApiException {
        Track[] tracks = spotifyService.getUsersTopTracks(authorizationCode);
        return TrackResponse.buildResponse(tracks);
    }

    @GetMapping("/top-genres")
    public List<String> getTopGenres() throws IOException, SpotifyWebApiException {
        return spotifyService.getUsersTopGenres(authorizationCode);
    }

    @GetMapping("/create-playlist")
    public String getPlaylistWithTopTracks() throws IOException, SpotifyWebApiException {
        String playlistId = String.valueOf(spotifyService.createPlaylistWithTopTracks(authorizationCode));
        return "Created playlist with ID: " + playlistId;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }
}
