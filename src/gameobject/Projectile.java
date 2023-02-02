package gameobject;

import controller.ProjectileController;
import core.*;
import game.state.State;
import gameobject.GameObject;
import gameobject.LivingObject;

import java.awt.*;

public class Projectile extends GameObject {

    private Image image;
    private ProjectileController controller;
    private LivingObject user;
    private LivingObject target;
    private Motion motion;
    private Direction direction;
    private int damage;

    public Projectile(LivingObject user, LivingObject target, int damage){
        super(log);
        this.user = user;
        this.target = target;
        this.position = new Position(user.getPosition().intX(), user.getPosition().intY());
        this.size = new Size(14,14);
        this.controller = new ProjectileController();
        this.motion = new Motion(15);
        this.direction = Direction.S;
        this.damage = damage;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        this.image = toolkit.getImage("resources/sprites/RockProjectile.png");
    }

    @Override
    public void update(State state){
        if(target.isDead()){
            state.removeGameObject(this);
        }

        motion.update(controller);
        position.apply(motion);
        manageDirection();

        controller.moveToTarget(target.getPosition(), this.position);
        if (arrived()){
            target.isHit(user, damage);
            state.removeGameObject(this);
        }

    }

    protected void manageDirection() {
        if(motion.isMoving()){
            this.direction = Direction.fromMotion(motion);
        }
    }

    private boolean arrived(){
        return position.isInRangeOf(target.getPosition(), 5);
    }

    @Override
    public Image getSprite() {
        return image;
    }

    @Override
    public CollisionBox getCollisionBox() {
        return new CollisionBox(new Rectangle(position.intX() - size.getWidth()/2, position.intY() - size.getHeight()/2, size.getWidth(), size.getHeight()));
    }

    @Override
    public CollisionBox getHitBox() {
        return null;
    }

    @Override
    public CollisionBox getDetectionBox() {
        return null;
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
