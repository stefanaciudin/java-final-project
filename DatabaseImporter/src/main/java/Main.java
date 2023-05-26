import com.opencsv.exceptions.CsvValidationException;
import database.DataImporter;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        String filepath = "Spotify_final_dataset.csv";
        try {
            DataImporter.importAlbumsFromCSV(filepath);
        } catch (IOException | SQLException | CsvValidationException e) {
            e.printStackTrace();
        }

    }
}
