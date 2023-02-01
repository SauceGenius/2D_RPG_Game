package core;

import game.GameLoop;

public class Time {

    private int updatesSinceStart;

    public Time() {
        this.updatesSinceStart = 0;
    }

    public void startUpdateClock(){
        this.updatesSinceStart++;
    }

    public void restartClock(){this.updatesSinceStart = 0;}

    /** Setters **/
    public void setUpdatesSinceStart(int updatesSinceStart) {this.updatesSinceStart = updatesSinceStart;}

    /** Getters **/
    public int  getUpdatesFromSeconds(int seconds){
        return seconds * GameLoop.UPDATES_PER_SECOND;
    }

    public double  getUpdatesFromSeconds(double seconds){
        return seconds * ((double)GameLoop.UPDATES_PER_SECOND);
    }

    public int getUpdatesSinceStart() {
        return updatesSinceStart;
    }
}
