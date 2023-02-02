package gameobject;

import core.CollisionBox;
import core.Log;
import game.state.State;
import stats.Stats;
import stats.StatusLiving;

import java.awt.*;

public abstract class LivingObject extends GameObject {

    protected Stats stats;
    protected StatusLiving status;


    public LivingObject(Log log) {
        super(log);
        this.status = new StatusLiving();
    }

    public boolean isHurt(){
        return status.isHurt();
    }

    public boolean isDead(){
        return status.isDead();
    }

    public boolean hasBeenLooted(){
        return status.hasBeenLooted();
    }

    public boolean isAutoAttacking(){
        return status.isAutoAttacking();
    }

    public boolean isInCombat(){
        return status.isInCombat();
    }

    public int getExp() {
        return stats.getLevel().getExp();
    }

    public int getLevel() {
        return stats.getLevel().getLevelValue();
    }

    /** Abstract **/
    public abstract void autoAttacks(LivingObject target);
    public abstract void isHit(LivingObject attackerObject, int damage);
    public abstract void dies() ;
    public abstract void taggedGameObjectIsKilled(LivingObject killedGameObject);

    @Override
    public void update(State state) {

    }

    @Override
    public Image getSprite() {
        return null;
    }

    @Override
    public CollisionBox getCollisionBox() {
        return null;
    }

    @Override
    public CollisionBox getHitBox() {
        return null;
    }

    @Override
    public CollisionBox getDetectionBox() {
        return null;
    }

    public StatusLiving getStatus() {
        return status;
    }

    public Stats getStats() {
        return stats;
    }

    @Override
    public boolean collidesWith(GameObject other) {
        return false;
    }

    @Override
    public boolean meleeRangeCollidesWith(GameObject other) {
        return false;
    }

    @Override
    public boolean mouseCollidesWith(GameObject other) {
        return false;
    }

    @Override
    public boolean detectionCollidesWith(GameObject gameObject) {
        return false;
    }
}
