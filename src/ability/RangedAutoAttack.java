package ability;

import core.Timer;
import game.state.State;
import gameobject.LivingObject;
import gameobject.MovingEntity;
import gameobject.NPC;
import gameobject.Projectile;

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
        if(!((MovingEntity)user).getDirection().isFacingTarget(target, user)){
            ((NPC)user).setCurrentAbility(null);
        }
        delayTimer.update();
        if(delayTimer.timeIsUp()){
            sendProjectile(state);
            ((NPC)user).setCurrentAbility(null);
        }
    }

    private void sendProjectile(State state) {
        Projectile arrow = new Projectile(user, target, damage);
        state.addGameObject(arrow);
    }
}
