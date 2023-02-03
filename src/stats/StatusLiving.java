package stats;

import core.Timer;
import gameobject.GameObject;
import gameobject.LivingObject;
import gameobject.Player;

import java.util.ArrayList;

public class StatusLiving {

    private boolean isInvincible;
    private boolean isInCombat;
    private boolean hasTargetInReach;
    private boolean isAutoAttacking;
    private boolean isHurt;
    private boolean isDead;
    private boolean hasBeenLooted;
    private boolean aggressive;
    private boolean ranged;
    private boolean canFlee;
    private Timer hurtTimer;
    private Timer deathTimer;
    private ArrayList<LivingObject> attackers;
    private ArrayList<LivingObject> attackedObjects;

    public StatusLiving(){
        isInvincible = false;
        isInCombat = false;
        hasTargetInReach = false;
        isAutoAttacking = false;
        isHurt = false;
        isDead = false;
        hasBeenLooted = false;
        ranged = false;
        canFlee = false;
        hurtTimer = new Timer();
        deathTimer = new Timer();
        attackers = new ArrayList<>();
        attackedObjects = new ArrayList<>();
    }

    public void update(LivingObject thisLivingObject){

        if(!isDead){
            if(thisLivingObject.getStats().getCurrentHpValue() <= 0 ) {
                thisLivingObject.dies();
                deathTimer.startClockSeconds(1);

            }
        } else {
            if(thisLivingObject instanceof Player){
                deathTimer.update();
                if(deathTimer.timeIsUp()){
                    ((Player)thisLivingObject).respawn();
                }
            }
        }


        hasTargetInReach = false;
        if(isHurt){
            hurtTimer.getUpdatesCountDown();
        }
        hurtTimer.update();
        if (hurtTimer.timeIsUp()){
            setIsHurt(false);
        }
    }

    public void addAttacker(LivingObject attackerObject){
        boolean newAttacker = true;
        if(attackers.size() > 0){
            for(GameObject attacker: attackers){
                if (attacker == attackerObject){
                    newAttacker = false;
                }
            }
            if(newAttacker){
                attackers.add(attackerObject);
            }
        } else attackers.add(attackerObject);
    }

    public void removeAttacker(LivingObject attackerObject){
        attackers.remove(attackerObject);
    }

    public void addAttackedObject(LivingObject taggedObject){
        boolean newTag = true;
        if(attackedObjects.size() > 0){
            for(GameObject tagged: attackedObjects){
                if (tagged == taggedObject){
                    newTag = false;
                }
            }
            if(newTag){
                attackedObjects.add(taggedObject);
            }
        }
        attackedObjects.add(taggedObject);
    }

    public void removeAttackedObject(GameObject gameObject){
        attackedObjects.remove(gameObject);
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

    public void setIsAutoAttacking(boolean isAutoAttacking) {
        this.isAutoAttacking = isAutoAttacking;
    }

    public void setIsHurt(boolean hurt) {
        if(!isHurt){
            if(hurt){
                this.isHurt = true;
                hurtTimer.startClockUpdates(20);
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

    public void setAggressiveOnDetection(boolean aggressive) {
        this.aggressive = aggressive;
    }

    public void setRanged(boolean ranged) {
        this.ranged = ranged;
    }

    public void setCanFlee(boolean canFlee) {
        this.canFlee = canFlee;
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

    public boolean isAggressiveOnDectection() {
        return aggressive;
    }

    public boolean isRanged() {
        return ranged;
    }

    public ArrayList<LivingObject> getAttackers() {
        return attackers;
    }

    public ArrayList<LivingObject> getAttackedObjects() {
        return attackedObjects;
    }

    public boolean canFlee() {
        return canFlee;
    }
}
