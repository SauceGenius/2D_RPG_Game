package ability;

import game.state.State;
import gameobject.GameObject;
import gameobject.LivingObject;

public abstract class Ability {

    protected LivingObject user;
    protected LivingObject target;


    public Ability(LivingObject user, LivingObject target){
        this.user = user;
        this.target = target;
    }

    public abstract void update (State state);

    /** Setters **/
    public void setUser(LivingObject user) {
        this.user = user;
    }

    public void setTarget(LivingObject target) {
        this.target = target;
    }

    /** Getters **/
    public LivingObject getUser() {
        return user;
    }

    public LivingObject getTarget() {
        return target;
    }
}
