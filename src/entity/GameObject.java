package entity;

import core.CollisionBox;
import core.Log;
import core.Position;
import core.Size;
import entity.stats.Stats;
import entity.stats.Status;
import game.state.State;

import java.awt.*;
public abstract class GameObject {

    //Variables
    protected static Log log;
    protected GameObjectID gameObjectID;
    protected String name;
    protected Position position;
    protected Size size;
    protected Stats stats;
    protected Status status;

    //Constructor
    public GameObject(Log log) {
        this.log = log;
        position = new Position(50,50);
        size = new Size(50,50);
        this.status = new Status();
    }

    //Setters
    public void setPosition(Position position) {
        this.position = position;
    }

    //Getters
    public GameObjectID getGameObjectID() {
        return gameObjectID;
    }
    public String getName() {
        return name;
    }
    public Position getPosition() {
        return position;
    }
    public Size getSize() {
        return size;
    }
    public Stats getStats() {return stats;}
    public int getLevel() {return stats.getLevel().getLevelValue();}
    public int getExp() {return stats.getLevel().getExp();}
    public int getExpToNextLevel(){return stats.getLevel().getExpToNextLevel();}
    public double getMaxHpValue() {return stats.getMaxHpValue();}
    public double getCurrentHpValue() {
        return stats.getCurrentHpValue();
    }
    public Status getStatus() {return status;}

    //Abstract
    public abstract void update(State state);
    public abstract Image getSprite();
    public abstract void gainExp(GameObject gameObject);

    //Abstract status
    public abstract boolean isInReach();
    public abstract boolean isHurt();
    public abstract boolean isDead();
    public abstract boolean hasBeenLooted();

    //Collision
    public abstract CollisionBox getCollisionBox();
    public abstract CollisionBox getHitBox();
    public abstract CollisionBox getDetectionBox();
    public abstract boolean collidesWith(GameObject other);
    public abstract boolean attackCollidesWith(GameObject other);
    public abstract boolean clickCollidesWith(GameObject other);
    public abstract boolean detectionCollidesWith(GameObject gameObject);
}
