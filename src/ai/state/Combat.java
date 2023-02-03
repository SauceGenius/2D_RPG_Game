package ai.state;

import ai.AITransition;
import controller.NPCController;
import core.Timer;
import gameobject.LivingObject;
import gameobject.NPC;
import gameobject.Player;
import game.state.State;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Combat extends AIState{

    private LivingObject target;
    private Timer autoAttackTimer;

    public Combat(){
        this.autoAttackTimer = new Timer(0.1);
    }

    @Override
    protected AITransition initializeTransition() {
        return new AITransition("flee", ((state, currentCharacter) ->
                currentCharacter.getStatus().canFlee()
                        && currentCharacter.getStats().getHp().getCurrentHp() / currentCharacter.getStats().getHp().getMaxHp() < 0.5
                        && !currentCharacter.isAutoAttacking()
                        && ((Player)currentCharacter.getTarget()).getInteractionBox().collidesWith(currentCharacter.getHitBox())));
    }

    @Override
    public void update(State state, NPC currentNPC) {
        target = currentNPC.getTarget();

        /**
        if(target != null && target.isDead()){
            target = null;
            System.out.println("CHANGE TO STAND BECAUSE TARGET DIED");
            transition = new AITransition("stand", ((state, currentCharacter) -> target.isDead()));
        }**/

        currentNPC.getMotion().setSpeed(currentNPC.getRunningSpeed());

        NPCController controller = (NPCController) currentNPC.getController();

        if(currentNPC.getStatus().isRanged()){
            if(currentNPC.isAutoAttacking()){

            }
            if(!currentNPC.isAutoAttacking()){
                controller.moveToTarget(target.getPosition(), currentNPC.getPosition());
            } else if (!currentNPC.getDirection().isFacingTarget(target, currentNPC)){
                /** cancels npc ranged auto attack **/
                currentNPC.getStatus().setIsAutoAttacking(false);
                currentNPC.getAnimationManager().stopCurrentAnimation();
            }


        } else {
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
