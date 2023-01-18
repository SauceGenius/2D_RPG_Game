package ai.state;

import ai.AITransition;
import controller.NPCController;
import entity.NPC;
import game.state.State;

public class Dead extends AIState{

    @Override
    protected AITransition initializeTransition() {
        return null;
    }

    @Override
    public void update(State state, NPC currentCharacter) {}
}
