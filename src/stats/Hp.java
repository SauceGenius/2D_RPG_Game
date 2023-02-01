package stats;

import core.Timer;
import id.GameObjectID;

public class Hp {

    private double maxHp;
    private double currentHp;
    private Timer timer;

    public Hp(GameObjectID gameObjectID, int stamina){

        /** Constructor for Player **/
        if(gameObjectID == GameObjectID.player){
            this.maxHp = stamina * 10;
            this.currentHp = this.maxHp;
            timer = new Timer(1);
        }

        /** Constructor for NPC **/
        if(gameObjectID == GameObjectID.NPC){
            this.maxHp = stamina * 10;
            this.currentHp = this.maxHp;
            timer = new Timer(1);
        }

    }

    public Hp(int level, int stamina){
        if(stamina < 20) {
            this.maxHp = stamina + 20;
        } else {
            this.maxHp = (level - 1 ) * 9 + (stamina - 20) * 10 + 40;
        }
        this.currentHp = this.maxHp;
        timer = new Timer(1);
    }

    public void update(Stats stats, Status status){
        int level = stats.getLevelValue();
        int stamina = stats.getStat(Stats.STAMINA);

        if(currentHp <= 0) {
            currentHp = 0;
        }

        if(currentHp > maxHp){
            currentHp = maxHp;
        }

        if(stamina < 20) {
            this.maxHp = stamina + 20;
        } else {
            this.maxHp = (level - 1 ) * 9 + (stamina - 20) * 10 + 40;
        }

        if(currentHp < maxHp){
            regenHP(stats, status);
        }

        timer.update();
    }

    private void regenHP(Stats stats, Status status){
        if(timer.timeIsUp()){
            if(!status.isInCombat()){
                int hp5 = (int)(stats.getStat(Stats.SPIRIT) * 0.8 + 6);
                currentHp += hp5;
            }
            timer.startClockSeconds(5);
        }
    }

    //Setters
    public void setMaxHp(double maxHp) {this.maxHp = maxHp;}
    public void setCurrentHp(double currentHp) {this.currentHp = currentHp;}

    //Getters
    public double getMaxHp() {return maxHp;}
    public double getCurrentHp() {return currentHp;}
}
