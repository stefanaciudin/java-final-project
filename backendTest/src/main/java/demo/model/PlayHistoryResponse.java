package demo.model;

import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.PlayHistory;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PlayHistoryResponse {
    private String name;
    private String artist;
    private String date;


    public static List<PlayHistoryResponse> buildResponse(PlayHistory[] playHistory) {
        List<PlayHistoryResponse> responseList = new ArrayList<>();
        for (PlayHistory history : playHistory) {
            TrackSimplified track = history.getTrack();
            ArtistSimplified[] artists = track.getArtists();
            List<String> artistNames = Arrays.stream(artists)
                    .map(ArtistSimplified::getName)
                    .collect(Collectors.toList());
            PlayHistoryResponse response = new PlayHistoryResponse();
            response.setName(track.getName());
            response.setArtist(String.join(", ", artistNames));
            response.setDate(history.getPlayedAt().toString());


            responseList.add(response);
        }

        return responseList;
    }

    public PlayHistoryResponse() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getArtist() {
        return artist;
    }

    public String getDate() {
        return date;
    }

    public PlayHistoryResponse(String name, String artist, String date) {
        this.name = name;
        this.artist = artist;
        this.date = date;
    }
}
