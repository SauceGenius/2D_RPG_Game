package stats;

public class Status {

    private boolean isInvincible;
    private boolean isInCombat;
    private boolean hasTargetInReach;
    private boolean isInReachOfPlayerInteractionBox;
    private boolean isAutoAttacking;
    private boolean isHurt;
    private boolean isDead;
    private boolean hasBeenLooted;

    public Status(){
        isInvincible = false;
        isInCombat = false;
        hasTargetInReach = false;
        isInReachOfPlayerInteractionBox = false;
        isAutoAttacking = false;
        isHurt = false;
        isDead = false;
        hasBeenLooted = false;
    }

    //Setters
    public void setIsInvincible(boolean invincible) {this.isInvincible = invincible;}
    public void setInCombat(boolean inCombat) {isInCombat = inCombat;}
    public void setHasTargetInReach(boolean inReach) {this.hasTargetInReach = inReach;}
    public void setInReachOfPlayerInteractionBox(boolean inReachOfPlayerInteractionBox) {isInReachOfPlayerInteractionBox = inReachOfPlayerInteractionBox;}
    public void setIsAutoAttacking(boolean isAutoAttacking) {this.isAutoAttacking = isAutoAttacking;}
    public void setIsHurt(boolean hurt) {this.isHurt = hurt;}
    public void setIsDead(boolean dead) {this.isDead = dead;}
    public void setHasBeenLooted(boolean looted) {this.hasBeenLooted = looted;}

    //Getters
    public boolean isInvincible() {
        return isInvincible;
    }
    public boolean isInCombat() {return isInCombat;}
    public boolean hasTargetInReach() {return hasTargetInReach;}
    public boolean isInReachOfPlayerInteractionBox() {return isInReachOfPlayerInteractionBox;}
    public boolean isAutoAttacking() {return isAutoAttacking;}
    public boolean isHurt() {return isHurt;}
    public boolean isDead() {
        return isDead;
    }
    public boolean hasBeenLooted() {return hasBeenLooted;}
}
