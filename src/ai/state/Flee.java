package ai.state;

import ai.AITransition;
import controller.NPCController;
import core.Position;
import game.state.State;
import gameobject.NPC;
import gameobject.Player;

import java.util.ArrayList;
import java.util.List;

public class Flee extends AIState{

    private List<Position> targets;

    public Flee(){
        super();
        targets = new ArrayList<>();
    }

    @Override
    protected AITransition initializeTransition() {
        return new AITransition("combat", ((state, currentCharacter) ->
                !currentCharacter.getRangedRangeBox().collidesWith(currentCharacter.getTarget().getHitBox())));
                //!((Player)currentCharacter.getTarget()).getInteractionBox().collidesWith(currentCharacter.getHitBox())));
    }

    @Override
    public void update(State state, NPC currentCharacter) {
        currentCharacter.getMotion().setSpeed(currentCharacter.getRunningSpeed());

        if(targets.isEmpty()){
            targets.add(state.getRandomPosition());
        }

        NPCController controller = (NPCController) currentCharacter.getController();
        controller.moveToTarget(targets.get(0), currentCharacter.getPosition());

        if(arrived(currentCharacter)){
            //targets.remove(0);
        }
    }

    private boolean arrived(NPC currentCharacter){
        return currentCharacter.getPosition().isInRangeOf(targets.get(0), 1);
    }
}
