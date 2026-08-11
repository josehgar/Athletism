package model;

public class Athlete {
    private String name;
    private int number;
    private int wonCompetitions;
    private int participations;
    private double chancesToStep;
    private double elo;

    public Athlete(String name, int number, double chancesToWin) {
        this.name = name;
        this.number = number;
        this.wonCompetitions = 0;
        this.participations = 0;
        this.chancesToStep = chancesToWin;
        this.elo = 0.0;
    }

    public Athlete(String name, int number, int wonCompetitions, int participations, double chancesToStep, double elo) {
        this.name = name;
        this.number = number;
        this.wonCompetitions = wonCompetitions;
        this.participations = participations;
        this.chancesToStep = chancesToStep;
        this.elo = elo;
    }

    public synchronized double getElo() {
        return elo;
    }

    public void setElo(double elo) {
        this.elo = elo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setWonCompetitions(int wonCompetitions) {
        this.wonCompetitions = wonCompetitions;
    }

    public void setParticipations(int participations) {
        this.participations = participations;
    }

    public void setChancesToStep(double chancesToStep) {
        this.chancesToStep = chancesToStep;
    }

    public double getChancesToStep() {
        return chancesToStep;
    }

    public int getParticipations() {
        return participations;
    }

    public int getWonCompetitions() {
        return wonCompetitions;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    private double calculateWinRate() {
        if (participations == 0) {
            return 0.0;
        } else {
            return (double) wonCompetitions / participations * 100;
        }
    }

    @Override
    public String toString() {
        return name + " | nº" + number + " | Wins: " + wonCompetitions +
                " | Participations: " + participations + String.format(" | Win rate: %.2f", calculateWinRate())
                + "%" + String.format(" | Elo: %.2f", elo);
    }
}
