package gameobject;

import audio.AudioPlayer;
import controller.MovementController;
import core.*;
import game.state.State;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import settings.Settings;

import java.awt.*;

public abstract class MovingEntity extends LivingObject {

    /** Variables **/
    protected MovementController controller;
    protected AudioPlayer audioPlayer;
    protected Motion motion;
    protected double walkingSpeed = 1;
    protected double runningSpeed = 3;
    protected Direction direction;

    /** Constructor **/
    public MovingEntity(AudioPlayer audioPlayer, Log log){
        super(log);
        this.audioPlayer = audioPlayer;
        this.motion = new Motion(runningSpeed);
        this.direction = Direction.S;
    }

    /** Methods **/
    @Override
    public void update(State state) {
        motion.update(controller);
        position.apply(motion);
        handleCollisions(state);
    }

    protected void manageDirection() {
        if(motion.isMoving()){
            this.direction = Direction.fromMotion(motion);
        }
    }

    /** Abstract **/
    protected abstract void decideAnimation();

    /** Collision **/

    private void handleCollisions(State state) {
        state.getCollidingObjects(this).forEach(this::handleCollisions);
        state.getInMeleeRangeObjects(this).forEach(this::handleIsInMeleeRangeOfCollisions);
        state.getClickedObjects(this).forEach(this::handleMouseCollisions);
        state.getDetectedObjects(this).forEach(this::handleDetectionCollisions);
    }

    protected abstract void handleCollisions(GameObject other);
    protected abstract void handleIsInMeleeRangeOfCollisions(GameObject other);
    protected abstract void handleMouseCollisions(GameObject other);
    protected abstract void handleDetectionCollisions(GameObject gameObject);

    /** Setters **/
    public void setAnimationManager(AnimationManager animationManager) {
        this.animationManager = animationManager;
    }

    public void setWalkingSpeed(double walkingSpeed) {
        this.walkingSpeed = walkingSpeed;
    }

    public void setRunningSpeed(double runningSpeed) {
        this.runningSpeed = runningSpeed;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /** Getters **/
    public MovementController getController() {
        return controller;
    }

    public Motion getMotion() {
        return motion;
    }

    public double getWalkingSpeed() {
        return walkingSpeed;
    }

    public double getRunningSpeed() {
        return runningSpeed;
    }

    public Direction getDirection() {
        return direction;
    }
}
