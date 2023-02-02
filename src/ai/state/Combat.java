package ai.state;

import ai.AITransition;
import controller.NPCController;
import core.Timer;
import gameobject.NPC;
import gameobject.Player;
import game.state.State;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Combat extends AIState{

    private Player target;
    private Timer autoAttackTimer;

    public Combat(Player player){
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

        if(currentNPC.getStatus().isRanged()){
            if(!currentNPC.isAutoAttacking()){
                controller.moveToTarget(target.getPosition(), currentNPC.getPosition());
            }
        } else if (!currentNPC.getStatus().isRanged()){
            controller.moveToTarget(target.getPosition(), currentNPC.getPosition());
        }

        if(arrived(currentNPC)){
            controller.stop();
        }
        combatUpdate(controller, currentNPC);
    }

    private boolean arrived(NPC currentNPC){
        if(currentNPC.getStatus().isRanged()){
            return currentNPC.getPosition().isInRangedRangeOf(target.getPosition());
        } else {
            return currentNPC.getPosition().isInMeleeRangeOf(target.getPosition());
        }
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
                        currentNPC.autoAttacks(target);
                        autoAttackTimer.startClockSeconds(3);
                    }
                } else {
                    currentNPC.getStatus().setHasTargetInReach(false);
                    currentNPC.getStatus().setIsAutoAttacking(false);
                }
            }
        }
    }
}
