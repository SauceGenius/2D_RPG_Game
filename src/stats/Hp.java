package stats;

import core.Timer;
import gameobject.LivingObject;
import gameobject.NPC;
import gameobject.Player;
import id.GameObjectID;

public class Hp {

    private double maxHp;
    private double currentHp;
    private Timer hp5Timer;

    public Hp(LivingObject livingObject, int stamina){

        /** Constructor for Player **/
        if(livingObject instanceof Player){
            this.maxHp = stamina * 10;
            this.currentHp = this.maxHp;
            hp5Timer = new Timer(0.1);
        }

        /** Constructor for NPC **/
        if(livingObject instanceof NPC){
            this.maxHp = stamina * 10;
            this.currentHp = this.maxHp;
            hp5Timer = new Timer(0.1);
        }

    }

    public Hp(int level, int stamina){
        if(stamina < 20) {
            this.maxHp = stamina + 20;
        } else {
            this.maxHp = (level - 1 ) * 9 + (stamina - 20) * 10 + 40;
        }
        this.currentHp = this.maxHp;
        hp5Timer = new Timer(0.1);
    }

    public void update(Stats stats, StatusLiving status){
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

        hp5Timer.update();
    }

    private void regenHP(Stats stats, StatusLiving status){
        if(hp5Timer.timeIsUp()){
            if(!status.isInCombat()){
                int hp5 = (int)(stats.getStat(Stats.SPIRIT) * 0.8 + 6);
                currentHp += hp5;
            }
            hp5Timer.startClockSeconds(5);
        }
    }

    public void resetHp5Timer(){
        hp5Timer.startClockSeconds(5);
    }

    /** Setters **/
    public void setMaxHp(double maxHp) {
        this.maxHp = maxHp;
    }

    public void setCurrentHp(double currentHp) {
        this.currentHp = currentHp;
    }

    /** Getters **/
    public double getMaxHp() {
        return maxHp;
    }

    public double getCurrentHp() {
        return currentHp;
    }
}
