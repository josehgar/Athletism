package controller;

import model.Athlete;

public class EloManager {
    private static final double K_FACTOR = 32;
    private Athlete winner, loser;

    public EloManager(Athlete winner, Athlete loser) {
        this.winner = winner;
        this.loser = loser;
    }

    private double expectedProbability(double ownElo, double eloRival) {
        return 1.0 / (1.0 + Math.pow(10, (eloRival - ownElo) / 400.0));
    }

    public synchronized double[] calculateElo() {
        double probWinner = expectedProbability(winner.getElo(), loser.getElo());
        double probLoser = expectedProbability(loser.getElo(), winner.getElo());

        double deltaWinner = K_FACTOR * (1.0 - probWinner);
        double deltaLoser;
        if (loser.getElo() == 0.0) {
            deltaLoser = 0.0;
        } else {
            deltaLoser = K_FACTOR * (0.0 - probLoser);
        }

        return new double[]{deltaWinner, deltaLoser};
    }
}