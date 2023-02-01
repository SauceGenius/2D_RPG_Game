package gameobject;

import audio.AudioPlayer;
import controller.MovementController;
import core.*;
import stats.Status;
import game.state.State;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import settings.Settings;

import java.awt.*;

public abstract class MovingEntity extends GameObject {

    // Variables
    protected MovementController controller;
    protected AudioPlayer audioPlayer;
    protected Motion motion;
    protected AnimationManager animationManager;
    protected Direction direction;
    protected SpriteLibrary spriteLibrary;

    // Constructor
    public MovingEntity(MovementController controller, AudioPlayer audioPlayer, SpriteLibrary spriteLibrary, Log log){
        super(log);
        this.controller = controller;
        this.audioPlayer = audioPlayer;
        this.motion = new Motion(3);
        this.direction = Direction.S;
        this.spriteLibrary = spriteLibrary;
    }

    // Methods
    @Override
    public void update(State state) {
        updateStatus();
        motion.update(controller);
        position.apply(motion);
        handleCollisions(state);
    }

    private void updateStatus(){
        if(!isDead()){
            if(stats.getCurrentHpValue() <= 0 ) {
                dies();
            }
        }
    }

    protected void manageDirection() {
        if(motion.isMoving()){
            this.direction = Direction.fromMotion(motion);
        }
    }

    protected abstract void decideAnimation();

    protected abstract void dies() ;

    @Override
    public CollisionBox getCollisionBox() {
        return new CollisionBox(new Rectangle(position.intX() + 10, position.intY() + Settings.SPRITE_SIZE_PLAYER/2 - 4, size.getWidth()/2, size.getHeight()/4 + 4));
    }

    public CollisionBox getHitBox(){
        return new CollisionBox(new Rectangle(position.intX(), position.intY(), size.getWidth(), size.getHeight()));
    }

    public CollisionBox getDetectionBox(){
        return new CollisionBox(new Rectangle(position.intX() - 250, position.intY() - 250, size.getWidth() + 500, size.getHeight() + 500));
    }

    @Override
    public boolean collidesWith(GameObject other) {
        return getCollisionBox().collidesWith(other.getCollisionBox());
    }

    private void handleCollisions(State state) {
        state.getCollidingObjects(this).forEach(this::handleCollisions);
        state.getAttackedObjects(this).forEach(this::handleInteractionCollisions);
        state.getClickedObjects(this).forEach(this::handleMouseCollisions);
        state.getDetectedObjects(this).forEach(this::handleDetectionCollisions);
    }

    protected abstract void handleCollisions(GameObject other);
    protected abstract void handleInteractionCollisions(GameObject other);
    protected abstract void handleMouseCollisions(GameObject other);
    protected abstract void handleDetectionCollisions(GameObject gameObject);


    /** Getters **/
    @Override
    public Image getSprite() {
        return animationManager.getSprite();
    }

    public MovementController getController() {
        return controller;
    }

    public Motion getMotion() {
        return motion;
    }

    public AnimationManager getAnimationManager() {
        return animationManager;
    }
}
