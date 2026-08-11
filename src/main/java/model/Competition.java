package model;

public class Competition {
    private Athlete opponent1;
    private volatile double progress1;
    private Athlete opponent2;
    private volatile double progress2;

    public Competition(Athlete opponent1, Athlete opponent2) {
        this.opponent1 = opponent1;
        this.opponent2 = opponent2;
    }

    public synchronized double getProgress1() {return progress1;}
    public synchronized double getProgress2() {return progress2;}

    public Athlete getOpponent1() {
        return opponent1;
    }

    public void setProgress1(double progress1) {
        this.progress1 = progress1;
    }

    public Athlete getOpponent2() {
        return opponent2;
    }

    public void setProgress2(double progress2) {
        this.progress2 = progress2;
    }
}
