package database;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import dao.TopSongDAO;
import model.TopSong;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class DataImporter {
    public static void importAlbumsFromCSV(String filepath) throws IOException, SQLException, CsvValidationException {
        try (CSVReader reader = new CSVReader(new FileReader(filepath))) {
            String[] line;
            //int idValueTopSongs = 0;
            while ((line = reader.readNext()) != null) {
                String artistName = line[1];
                String songName = line[2];
                TopSong topSong = new TopSong(artistName, songName);
                TopSongDAO topSongDAO = new TopSongDAO();
                topSongDAO.addTopSong(topSong);
            }
        }

    }
}