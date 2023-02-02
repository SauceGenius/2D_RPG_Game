package core;

import game.GameLoop;

public class Timer {

    private boolean on;
    private double seconds;
    private int updatesCountDown;
    private boolean timesUp;

    public Timer() {
        this.on = true;
        this.updatesCountDown = 0;
    }

    public Timer(double seconds) {
        this.on = true;
        this.seconds = seconds;
        this.updatesCountDown = (int) (seconds * (double) GameLoop.UPDATES_PER_SECOND);
    }

    public void update(){
        if(on){
            if(updatesCountDown > 0){
                updatesCountDown--;
                if (updatesCountDown <= 0) {
                    timesUp = true;
                    updatesCountDown = 0;
                } else timesUp = false;
            }

            seconds = (double) updatesCountDown / (double) GameLoop.UPDATES_PER_SECOND;
        }
    }

    public void startClockSeconds(double seconds){
        this.on = true;
        this.seconds = seconds;
        updatesCountDown = (int) (seconds * GameLoop.UPDATES_PER_SECOND);
        if(updatesCountDown > 0 ) timesUp = false;
    }

    public void startClockUpdates(int updates){
        this.on = true;
        updatesCountDown = updates;
        seconds = (int) ((double)updatesCountDown / (double)GameLoop.UPDATES_PER_SECOND);
        if(updatesCountDown > 0) timesUp = false;
    }

    public void setOn(boolean on) {
        this.on = on;
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

    public boolean isOn() {
        return on;
    }
}

