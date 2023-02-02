package ability;

import core.Timer;
import game.state.State;
import gameobject.LivingObject;
import gameobject.NPC;

public class MeleeAutoAttack extends Ability {

    private int damage;
    private Timer delayTimer;

    public MeleeAutoAttack(LivingObject user, LivingObject target, int damage, double attackSpeed){
        super(user, target);
        this.damage = damage;
        this.delayTimer = new Timer(attackSpeed);
    }

    @Override
    public void update(State state){
        delayTimer.update();
        if(delayTimer.timeIsUp()){
            hit();
        }
    }

    private void hit(){;
        target.isHit(user, damage);
        ((NPC)user).setCurrentAbility(null);
    }
}
