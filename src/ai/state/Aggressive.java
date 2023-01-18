package ai.state;

import ai.AITransition;
import controller.NPCController;
import entity.NPC;
import entity.Player;
import game.state.State;

public class Aggressive extends AIState{

    private Player target;

    public Aggressive(Player player){
        target = player;
    }


    @Override
    protected AITransition initializeTransition() {
        return null;
    }

    @Override
    public void update(State state, NPC currentCharacter) {
        currentCharacter.getMotion().setSpeed(2);


        NPCController controller = (NPCController) currentCharacter.getController();
        controller.moveToTarget(target.getPosition(), currentCharacter.getPosition());

        if(arrived(currentCharacter)){
            controller.stop();
        }
    }

    private boolean arrived(NPC currentCharacter){
        return currentCharacter.getPosition().isInMeleeRangeOf(target.getPosition());
    }
}
