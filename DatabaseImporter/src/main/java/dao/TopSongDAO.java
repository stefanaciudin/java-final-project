package dao;

import database.DatabaseManager;
import model.TopSong;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TopSongDAO {

    private final Connection connection;

    public TopSongDAO() throws SQLException {
        connection = DatabaseManager.getConnection();
    }

    public void addTopSong(TopSong topSong) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO top_songs (artist_name, song_name) VALUES (?,?)");
        preparedStatement.setString(1, TopSong.getArtistName());
        preparedStatement.setString(2, TopSong.getSongName());
        preparedStatement.executeUpdate();
    }
}
