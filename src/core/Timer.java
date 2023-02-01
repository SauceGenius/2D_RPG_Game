package core;

import game.GameLoop;

public class Timer {

    private double seconds;
    private int updatesCountDown;
    private boolean timesUp;

    public Timer() {
        this.updatesCountDown = 0;
    }

    public Timer(double seconds) {
        this.seconds = seconds;
        this.updatesCountDown = (int) (seconds * (double) GameLoop.UPDATES_PER_SECOND);
    }
    
    public void update(){
        if(updatesCountDown > 0){
            updatesCountDown--;
            if (updatesCountDown <= 0) {
                timesUp = true;
                updatesCountDown = 0;
            } else timesUp = false;
        }

        seconds = (double) updatesCountDown / (double) GameLoop.UPDATES_PER_SECOND;
    }

    public void startClockSeconds(double seconds){
        this.seconds = seconds;
        updatesCountDown = (int) (seconds * GameLoop.UPDATES_PER_SECOND);
    }

    public void startClockUpdates(int updates){
        updatesCountDown = updates;
        seconds = (int) ((double)updatesCountDown / (double)GameLoop.UPDATES_PER_SECOND);
    }

    /** Getters **/
    public boolean timeIsUp() {
        return timesUp;
    }

    public int getUpdatesCountDown() {
        return updatesCountDown;
    }

    public double getSecondsCountDown() {
        return seconds;
    }
}

