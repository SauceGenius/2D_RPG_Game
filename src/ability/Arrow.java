package ability;

import controller.NPCController;
import core.CollisionBox;
import core.Direction;
import core.Motion;
import core.Position;
import game.state.State;
import gameobject.GameObject;
import gameobject.LivingObject;
import settings.Settings;

import java.awt.*;

public class Arrow extends GameObject {

    private Image image;
    private NPCController controller;
    private LivingObject user;
    private LivingObject target;
    private Motion motion;
    private Direction direction;
    private int damage;

    public Arrow(LivingObject user, LivingObject target, int damage){
        super(log);
        this.user = user;
        this.target = target;
        this.position = new Position(user.getPosition().intX(), user.getPosition().intY());
        this.controller = new NPCController();
        this.motion = new Motion(15);
        this.direction = Direction.S;
        this.damage = damage;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        this.image = toolkit.getImage("resources/sprites/Arrow.png");
    }

    @Override
    public void update(State state){
        motion.update(controller);
        position.apply(motion);
        manageDirection();

        controller.moveToTarget(target.getPosition(), this.position);
        if (arrived()){
            System.out.println("arrow arrived");
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
        return position.isInMeleeRangeOf(target.getPosition());
    }

    @Override
    public Image getSprite() {
        //System.out.println("Arrow: " + position.intX() + "," + position.intY() + " Target: " + target.getPosition().intX() + "," + target.getPosition().intY());
        return image;
    }

    @Override
    public CollisionBox getCollisionBox() {
        return new CollisionBox(new Rectangle(position.intX() + 10, position.intY() + Settings.SPRITE_SIZE_PLAYER/2 - 4, size.getWidth()/2, size.getHeight()/4 + 4));
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
