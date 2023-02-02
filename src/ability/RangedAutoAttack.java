package ability;

import core.Timer;
import game.state.State;
import gameobject.LivingObject;
import gameobject.NPC;

public class RangedAutoAttack extends Ability{

    private int damage;
    private Timer delayTimer;

    public RangedAutoAttack(LivingObject user, LivingObject target, int damage, double attackSpeed) {
        super(user, target);
        this.damage = damage;
        this.delayTimer = new Timer(attackSpeed);
    }

    @Override
    public void update(State state) {
        delayTimer.update();
        if(delayTimer.timeIsUp()){
            sendProjectile(state);
            ((NPC)user).setCurrentAbility(null);
        }
    }

    private void sendProjectile(State state) {
        Arrow arrow = new Arrow(user, target, damage);
        state.addGameObject(arrow);
    }
}
