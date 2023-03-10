package ai.state;

import ai.AITransition;
import gameobject.NPC;
import game.state.State;

public abstract class AIState {

    protected AITransition transition;

    public AIState() {
        this.transition = initializeTransition();
    }

    protected abstract AITransition initializeTransition();
    public abstract void update(State state, NPC currentCharacter);

    public boolean shouldTransition(State state, NPC currentCharacter){
        if(transition == null){return false;}
        return transition.shouldTransition(state, currentCharacter);
    }

    /** Getters **/
    public String getNextState(){
        return transition.getNextState();
    }
}
