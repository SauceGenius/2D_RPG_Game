package ai.state;

import ai.AITransition;
import controller.NPCController;
import core.Position;
import core.Vector2D;
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
    public void update(State state, NPC currentNPC) {
        currentNPC.getMotion().setSpeed(currentNPC.getRunningSpeed());

        if(targets.isEmpty()){
            //targets.add(findPositionToFlee(currentNPC));
            targets.add(state.getRandomPosition());
        }

        NPCController controller = (NPCController) currentNPC.getController();
        controller.moveToTarget(targets.get(0), currentNPC.getPosition());

        if(arrived(currentNPC)){
            //targets.remove(0);
        }
    }

    private boolean arrived(NPC currentCharacter){
        if(!targets.isEmpty()){
            return currentCharacter.getPosition().isInRangeOf(targets.get(0), 1);
        }
        return false;
    }

    private Position findPositionToFlee(NPC currentNPC){
        int x = currentNPC.getPosition().intX() - currentNPC.getTarget().getPosition().intX();
        int y = currentNPC.getPosition().intY() - currentNPC.getTarget().getPosition().intY();

        Vector2D vector = new Vector2D(x, y);

        return null;
    }
}
