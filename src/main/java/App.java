import model.AthleteDatabase;
import view.MenuDrawer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        File database = new File(AthleteDatabase.DATABASE);
        if (!database.exists()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(AthleteDatabase.DATABASE))) {
                database.createNewFile();
                bw.write("name,number,wonCompetitions,participations,chancesToStep,elo");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        MenuDrawer.mainMenu();
    }
}
