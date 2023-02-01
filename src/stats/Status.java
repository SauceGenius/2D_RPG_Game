package stats;

import core.Timer;

public class Status {

    private boolean isInvincible;
    private boolean isInCombat;
    private boolean hasTargetInReach;
    private boolean isInReachOfPlayerInteractionBox;
    private boolean isAutoAttacking;
    private boolean isHurt;
    private boolean isDead;
    private boolean hasBeenLooted;
    private boolean aggressive;
    private Timer hurtTimer;

    public Status(){
        isInvincible = false;
        isInCombat = false;
        hasTargetInReach = false;
        isInReachOfPlayerInteractionBox = false;
        isAutoAttacking = false;
        isHurt = false;
        isDead = false;
        hasBeenLooted = false;
        hurtTimer = new Timer();
    }

    public void update(){
        if(isHurt){hurtTimer.getUpdatesCountDown();}
        hurtTimer.update();
        if (hurtTimer.timeIsUp()){
            setIsHurt(false);
        }
    }

    /** Setters **/
    public void setIsInvincible(boolean invincible) {
        this.isInvincible = invincible;
    }

    public void setInCombat(boolean inCombat) {
        isInCombat = inCombat;
    }

    public void setHasTargetInReach(boolean inReach) {
        this.hasTargetInReach = inReach;
    }

    public void setInReachOfPlayerInteractionBox(boolean inReachOfPlayerInteractionBox) {
        isInReachOfPlayerInteractionBox = inReachOfPlayerInteractionBox;
    }

    public void setIsAutoAttacking(boolean isAutoAttacking) {
        this.isAutoAttacking = isAutoAttacking;
    }

    public void setIsHurt(boolean hurt) {
        if(!isHurt){
            if(hurt){
                this.isHurt = true;
                hurtTimer.startClockUpdates(40);
            }
        }
        if(hurt == false){
            isHurt = false;
        }
    }

    public void setIsDead(boolean dead) {
        this.isDead = dead;
    }

    public void setHasBeenLooted(boolean looted) {
        this.hasBeenLooted = looted;
    }

    public void setAggressive(boolean aggressive) {
        this.aggressive = aggressive;
    }

    /** Getters **/
    public boolean isInvincible() {
        return isInvincible;
    }

    public boolean isInCombat() {
        return isInCombat;
    }

    public boolean hasTargetInReach() {
        return hasTargetInReach;
    }

    public boolean isInReachOfPlayerInteractionBox() {
        return isInReachOfPlayerInteractionBox;
    }

    public boolean isAutoAttacking() {
        return isAutoAttacking;
    }

    public boolean isHurt() {
        return isHurt;
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean hasBeenLooted() {
        return hasBeenLooted;
    }

    public boolean isAggressive() {
        return aggressive;
    }
}
