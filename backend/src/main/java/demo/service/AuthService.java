package demo.service;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.net.URI;

/**
 * AuthService - handles authorization with Spotify API
 */
public class AuthService {
    private static final String clientId = "30059395ac9147cd8380f8d0161cec39";
    private static final String clientSecret = "34277e77fef845ff9b4b1fac6803ca0a";
    private static final String redirectUri = "http://localhost:8080/callback";

    static SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(URI.create(redirectUri))
            .build();

    public static URI getAuthorizationCodeUri() {
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                .scope("playlist-read-private,user-read-email," +
                        "user-follow-read,user-top-read," +
                        "playlist-modify-public," +
                        "user-read-recently-played," +
                        "playlist-modify-private")
                .build();

        return authorizationCodeUriRequest.execute();
    }

//    public static void setToken(String code) throws IOException, SpotifyWebApiException {
//        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
//        spotifyApi.setAccessToken(authorizationCodeRequest.execute().getAccessToken());
//    }

}