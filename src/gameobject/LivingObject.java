package gameobject;

import core.CollisionBox;
import core.Log;
import game.state.State;
import gfx.AnimationManager;
import settings.Settings;
import stats.Stats;
import stats.StatusLiving;

import java.awt.*;

public abstract class LivingObject extends GameObject {

    protected Stats stats;
    protected StatusLiving status;
    protected AnimationManager animationManager;


    public LivingObject(Log log) {
        super(log);
        this.status = new StatusLiving();
    }

    @Override
    public void update(State state) {

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

    /** Collision **/
    @Override
    public CollisionBox getCollisionBox() {
        return new CollisionBox(new Rectangle(position.intX() + 8 - size.getWidth() / 4, position.intY() + size.getHeight() / 4 - 10, size.getWidth()/3, size.getHeight()/4));
    }

    @Override
    public CollisionBox getHitBox() {
        return new CollisionBox(new Rectangle(position.intX() - size.getWidth() / 2, position.intY() - size.getHeight() / 2, size.getWidth(), size.getHeight()));
    }

    public CollisionBox getDetectionBox(){
        int detectionSizeBox = 600;
        return new CollisionBox(new Rectangle(position.intX() - detectionSizeBox / 2, position.intY() - detectionSizeBox / 2, detectionSizeBox, detectionSizeBox));
    }

    public CollisionBox getRangedRangeBox(){
        int range = 400;
        return new CollisionBox(new Rectangle(position.intX() - range / 2, position.intY() - range / 2, range, range));
    }

    @Override
    public boolean collidesWith(GameObject other) {
        return getCollisionBox().collidesWith(other.getCollisionBox());
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

    /** Getters **/
    @Override
    public Image getSprite() {
        return animationManager.getSprite();
    }

    public StatusLiving getStatus() {
        return status;
    }

    public Stats getStats() {
        return stats;
    }

    public AnimationManager getAnimationManager() {
        return animationManager;
    }
}
