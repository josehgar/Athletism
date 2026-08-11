package model;

import java.io.*;
import java.util.*;

public class AthleteDatabase {
    public static final String DATABASE = "dbase.txt";
    private static volatile AthleteDatabase instance;
    private Map<Integer, Athlete> database;

    private AthleteDatabase() {
        database = prepareDatabase();
    }

    public static AthleteDatabase getInstance() {
        if (instance == null) {
            instance = new AthleteDatabase();
        }
        return instance;
    }

    private Map<Integer, Athlete> prepareDatabase() {
        Map<Integer, Athlete> result = new HashMap<>();
        // name,number,wonCompetitions,participations,chancesToStep,elo
        try (BufferedReader br = new BufferedReader(new FileReader(DATABASE))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                result.put(Integer.parseInt(parts[1]), new Athlete(parts[0], Integer.parseInt(parts[1])
                        ,Integer.parseInt(parts[2]), Integer.parseInt(parts[3]),
                        Double.parseDouble(parts[4]), Double.parseDouble(parts[5])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void addAthlete(Athlete a) {
        database.put(a.getNumber(), a);
        saveDatabase();
    }

    public void deleteAthlete(Athlete a) {
        database.remove(a.getNumber());
        saveDatabase();
    }

    public Map<Integer, Athlete> getDatabase() {
        return Collections.unmodifiableMap(database);
    }

    public Athlete getAthlete(Integer key) {
        return database.get(key);
    }

    public synchronized void saveDatabase() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DATABASE))) {
            bw.write("name,number,wonCompetitions,participations,chancesToStep,elo");
            bw.newLine();
            for (Athlete a : database.values()) {
                bw.write(a.getName() + "," + a.getNumber() + "," + a.getWonCompetitions() + ","
                        + a.getParticipations() + "," + a.getChancesToStep() + "," + a.getElo());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
