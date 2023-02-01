package ai;

import ai.state.AIState;
import ai.state.Dead;
import ai.state.Stand;
import ai.state.Wander;
import controller.NPCController;
import gameobject.NPC;
import game.state.State;

public class AIManager {

    private boolean on = true;
    private AIState currentAIState;

    public AIManager() {
        transitionTo("stand");
    }

    public void update(State state, NPC currentCharacter) {
        if(on == true){
            currentAIState.update(state, currentCharacter);

            if (currentAIState.shouldTransition(state, currentCharacter)) {
                transitionTo(currentAIState.getNextState());
            }
        } else {
            currentAIState = new Dead();
            NPCController npcController = (NPCController) currentCharacter.getController();
            npcController.stop();
        }
    }

    private void transitionTo(String nextState) {
        //System.out.println("Goblin is transitioning to " + nextState);
        switch (nextState) {
            case "wander":
                currentAIState = new Wander();
                return;
            case "stand":
            default:
                currentAIState = new Stand();
        }
    }

    public void setOn(boolean on){this.on = on;}

    public void setCurrentAIState(AIState currentAIState) {
        this.currentAIState = currentAIState;
    }

    public AIState getCurrentAIState() {
        return currentAIState;
    }
}
