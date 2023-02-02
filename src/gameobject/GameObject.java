package gameobject;

import core.CollisionBox;
import core.Log;
import core.Position;
import core.Size;
import stats.Stats;
import stats.StatusLiving;
import game.state.State;

import java.awt.*;
public abstract class GameObject {

    /** Variables **/
    protected static Log log; // Will have to remove Static

    protected String name;
    protected Position position;
    protected Size size;

    /** Constructor **/
    public GameObject(Log log) {
        this.log = log;
        this.position = new Position(50,50);
        this.size = new Size(50,50);
    }

    /** Setters **/
    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    /** Getters **/
    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    public Size getSize() {
        return size;
    }


    /** Abstract **/
    public abstract void update(State state);
    public abstract Image getSprite();

    /** Collision **/
    public abstract CollisionBox getCollisionBox();
    public abstract CollisionBox getHitBox();
    public abstract CollisionBox getDetectionBox();
    public abstract boolean collidesWith(GameObject other);
    public abstract boolean meleeRangeCollidesWith(GameObject other);
    public abstract boolean mouseCollidesWith(GameObject other);
    public abstract boolean detectionCollidesWith(GameObject gameObject);
}
