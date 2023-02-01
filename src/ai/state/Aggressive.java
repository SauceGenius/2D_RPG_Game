package ai.state;

import ai.AITransition;
import controller.NPCController;
import core.Timer;
import gameobject.NPC;
import gameobject.Player;
import game.state.State;

public class Aggressive extends AIState{

    private Player target;
    private Timer autoAttackTimer;

    public Aggressive(Player player){
        target = player;
        this.autoAttackTimer = new Timer(0.1);
    }


    @Override
    protected AITransition initializeTransition() {
        return null;
    }

    @Override
    public void update(State state, NPC currentNPC) {
        currentNPC.getMotion().setSpeed(2);

        NPCController controller = (NPCController) currentNPC.getController();
        if(!currentNPC.getStatus().isAutoAttacking()){
            controller.moveToTarget(target.getPosition(), currentNPC.getPosition());
        }

        if(arrived(currentNPC)){
            controller.stop();
        }
        combatUpdate(controller, currentNPC);
    }

    private boolean arrived(NPC currentCharacter){
        return currentCharacter.getPosition().isInMeleeRangeOf(target.getPosition());
    }

    private void combatUpdate(NPCController controller, NPC currentNPC) {
        autoAttackTimer.update();;

        if(target == null) {
            currentNPC.getStatus().setInCombat(false);
        } else {
            if (currentNPC.getStatus().isInCombat()) {
                if(arrived(currentNPC)){
                    currentNPC.getStatus().setHasTargetInReach(true);
                    if (autoAttackTimer.timeIsUp()) {
                        currentNPC.getStatus().setIsAutoAttacking(true);
                        controller.attack(target);
                        autoAttackTimer.startClockSeconds(3);
                    }
                } else {
                    currentNPC.getStatus().setHasTargetInReach(false);
                }
            }
        }
    }
}
