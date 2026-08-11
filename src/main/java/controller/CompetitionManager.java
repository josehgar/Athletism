package controller;

import model.Athlete;
import model.AthleteDatabase;
import model.Competition;
import view.RaceDrawer;

public class CompetitionManager {
    private Competition race;
    private volatile boolean raceEnd = false;

    public CompetitionManager(Competition race) {
        this.race = race;
    }

    public void start() {
        Athlete opponent1 = race.getOpponent1();
        Athlete opponent2 = race.getOpponent2();

        Thread t1 = new Thread(() -> {
            while (!raceEnd) {
                double chanceToAdvance = Math.random() * 100;
                if (chanceToAdvance < opponent1.getChancesToStep()) {
                    race.setProgress1(race.getProgress1() + 5);
                }
                if (race.getProgress1() >= 100) {
                    raceEnd = true;
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(() -> {
            while (!raceEnd) {
                double chanceToAdvance = Math.random() * 100;
                if (chanceToAdvance < opponent2.getChancesToStep()) {
                    race.setProgress2(race.getProgress2() + 5);
                }
                if (race.getProgress2() >= 100) {
                    raceEnd = true;
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread drawer = new Thread(() -> {
            int tInstant = 1;
            while (!raceEnd) {
                RaceDrawer.drawRace(race, tInstant);
                try {
                    Thread.sleep(200);
                    tInstant++;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            RaceDrawer.drawRace(race, tInstant); // último frame
        });

        drawer.start();
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Athlete[] winnerLoser = RaceDrawer.announceWinner(race);
        Athlete winner = winnerLoser[0];
        Athlete loser = winnerLoser[1];
        EloManager eloManager = new EloManager(winner, loser);
        double[] elos = eloManager.calculateElo();
        updateStats(winnerLoser, elos);
    }

    private synchronized void updateStats(Athlete[] athletes, double[] elos) {
        Athlete winner = athletes[0];
        Athlete loser = athletes[1];
        winner.setElo(winner.getElo() + elos[0]);
        loser.setElo(loser.getElo() + elos[1]);
        if (loser.getElo() < 0) {
            loser.setElo(0.0);
        }
        winner.setWonCompetitions(winner.getWonCompetitions() + 1);
        winner.setParticipations(winner.getParticipations() + 1);
        loser.setParticipations(loser.getParticipations() + 1);
        AthleteDatabase db = AthleteDatabase.getInstance();
        db.addAthlete(winner);
        db.addAthlete(loser);
        db.saveDatabase();
    }
}
