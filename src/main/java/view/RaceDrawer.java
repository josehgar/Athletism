package view;

import model.Athlete;
import model.Competition;

public class RaceDrawer {
    private static final int LANE_LENGTH = 30;

    public static synchronized void drawRace(Competition competition, int tInstant) {
        System.out.println("t = " + tInstant);

        String name1 = competition.getOpponent1().getName();
        String name2 = competition.getOpponent2().getName();
        int maxNameLength = Math.max(name1.length(), name2.length());

        System.out.printf("%-" + maxNameLength + "s: ", name1);
        System.out.print(buildLane(competition.getProgress1()));
        System.out.println(" " + competition.getProgress1() + "%");

        System.out.printf("%-" + maxNameLength + "s: ", name2);
        System.out.print(buildLane(competition.getProgress2()));
        System.out.println(" " + competition.getProgress2() + "%");

        if (competition.getProgress1() <= 95.0 && competition.getProgress2() <= 95.0) {
            for (int i=1; i<=4; i++) {
                System.out.println();
            }
        }
    }

    private static String buildLane(double progress) {
        int position = (int) ((progress / 100.0) * (LANE_LENGTH - 1));

        StringBuilder lane = new StringBuilder();
        lane.append("|");
        for (int i = 0; i < LANE_LENGTH; i++) {
            if (i == position) {
                lane.append("●");
            } else {
                lane.append("-");
            }
        }
        lane.append("·");

        return lane.toString();
    }

    public static synchronized Athlete[] announceWinner(Competition c) {
        Athlete[] winnerLoser = new Athlete[2];
        System.out.println();
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (c.getProgress1() == 100.0) {
            System.out.println(c.getOpponent1().getName() + " won the race!");
            winnerLoser[0] = c.getOpponent1();
            winnerLoser[1] = c.getOpponent2();
        } else if (c.getProgress2() == 100.0) {
            System.out.println(c.getOpponent2().getName() + " won the race!");
            winnerLoser[0] = c.getOpponent2();
            winnerLoser[1] = c.getOpponent1();
        }
        System.out.println("Returning main menu...");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return winnerLoser;
    }
}