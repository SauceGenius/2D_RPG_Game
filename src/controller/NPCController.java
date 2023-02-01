package controller;

import core.Position;
import gameobject.GameObject;
import gameobject.NPC;
import gameobject.Player;

public class NPCController implements MovementController {

    private NPC npc;
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;

    public NPCController(){

    }

    public void moveToTarget(Position target, Position current) {
        double deltaX = target.getX() - current.getX();
        double deltaY = target.getY() - current.getY();

        up = deltaY < 0 && Math.abs(deltaY) > Position.PROXIMITY_RANGE;
        right = deltaX > 0 && Math.abs(deltaX) > Position.PROXIMITY_RANGE;
        down = deltaY > 0 && Math.abs(deltaY) > Position.PROXIMITY_RANGE;
        left = deltaX < 0 && Math.abs(deltaX) > Position.PROXIMITY_RANGE;
    }

    public void stop() {
        up = false;
        right = false;
        down = false;
        left = false;
    }

    public void attack(GameObject target) {
        npc.getMotion().setAttacking(true);

        /** calculate damamge **/
        int damage = 5;

        /** Hit target **/
        target.isHit(npc, damage);
    }

    @Override
    public boolean isRequestingUp() {
        return up;
    }

    @Override
    public boolean isRequestingDown() {
        return down;
    }

    @Override
    public boolean isRequestingLeft() {
        return left;
    }

    @Override
    public boolean isRequestingRight() {
        return right;
    }

    public boolean isRequestingSprint() {
        return false;
    }

    /** Setters **/
    public void setNpc(NPC npc) {
        this.npc = npc;
    }
}
