package gameobject;

import ability.Ability;
import ability.MeleeAutoAttack;
import ability.RangedAutoAttack;
import ai.AIManager;
import ai.state.Combat;
import ai.state.Stand;
import audio.AudioLibrary;
import audio.AudioPlayer;
import controller.NPCController;
import core.Direction;
import core.Log;
import core.Size;
import core.Timer;
import item.Item;
import game.state.State;
import item.LootTable;

import java.util.ArrayList;

public abstract class NPC extends MovingEntity {

    /** Variables **/
    protected AIManager aiManager;
    protected LootTable lootTable;
    protected NPCController npcController;
    protected LivingObject target;
    protected double animationAttackSpeed;
    protected Timer autoAttackSpeedTimer;
    protected Ability currentAbility;


    /** Constructor **/
    public NPC(AudioPlayer audioPlayer, Log log) {
        super(audioPlayer, log);
        this.size = new Size(96,96);
        this.autoAttackSpeedTimer = new Timer();
        this.npcController = new NPCController();
        this.controller = npcController;
        this.aiManager = new AIManager();
        this.lootTable = new LootTable();
    }

    /** Methods **/
    @Override
    public void update(State state) {
        manageDirection();
        status.update(this);
        super.update(state);
        decideAnimation();
        animationManager.update(direction);
        aiManager.update(state, this);

        if(currentAbility != null){
            currentAbility.update(state);
        }
    }

    @Override
    protected void manageDirection() {
        if(motion.isMoving()){
            this.direction = Direction.fromMotion(motion);
        } else if(target != null && status.isRanged() && status.hasTargetInReach() && !status.isAutoAttacking()){
            this.direction = direction.fromTarget(target, this);
        }
    }

    @Override
    protected void decideAnimation() {
        if (isDead()) {animationManager.playAnimation("Dying");}
        else if (isAutoAttacking()) {animationManager.playAutoAttackAnimation();}
        else if (isHurt()) {animationManager.playAnimation("Hurt");}
        else if (motion.isMoving()) {animationManager.playAnimation("Run");}
        else {animationManager.playAnimation("Idle");}
    }

    public void aggroes(LivingObject aggroedGameObject){
        status.addAttackedObject(aggroedGameObject);

        if(aggroedGameObject instanceof Player){
            target = aggroedGameObject;
            Player player = (Player) aggroedGameObject;
            if(!status.isInCombat()){
                status.setInCombat(true);
                getAiManager().setCurrentAIState(new Combat());
                audioPlayer.playSound(AudioLibrary.GOBLIN_AGGRO);
                player.aggroed(this);
            }
        }

        else if(aggroedGameObject instanceof NPC){
            System.out.println("NPC aggroing another npc is not implemented yet");
        }
    }

    @Override
    public void autoAttacks(LivingObject target) {
        motion.setAttacking(true);

        playAutoAttackSound();

        /** calculate damamge **/
        int damage = 5;

        if(status.isRanged()){
            currentAbility = new RangedAutoAttack(this, target, damage, animationAttackSpeed);
        } else {
            currentAbility = new MeleeAutoAttack(this, target, damage, animationAttackSpeed);
        }
    }

    protected abstract void playAutoAttackSound();

    @Override
    public void isHit(LivingObject attackerObject, int damage) {
        status.addAttacker(attackerObject);
        if(target == null){
            aggroes(attackerObject);
        }
        if(status.isHurt() == false) {
            status.setIsHurt(true);
        }
        double newHP = stats.getCurrentHpValue() - damage;
        stats.setCurrentHpValue(newHP);

        if (stats.getCurrentHpValue() <= 0) {
            this.dies();
            log.addToDamageLog(name, target.name, Integer.toString(target.position.intX()), Integer.toString(target.position.intY()), "Slain", "null", "null");
        }
    }

    @Override
    public void attackerObjectDied(LivingObject attackerObjectDied) {
        if(attackerObjectDied == target) {
            target = null;
        }
        status.removeAttacker(attackerObjectDied);
    }

    @Override
    public void attackedObjectDied(LivingObject attackedObjectDied) {
        if(attackedObjectDied == target) {
            target = null;
        }
        status.removeAttackedObject(attackedObjectDied);
        if(status.getAttackedObjects().isEmpty()) {
            currentAbility = null;
            aiManager.setCurrentAIState(new Stand());
            status.setInCombat(false);
        }

    }

    @Override
    public void dies(){
        stats.getHp().setCurrentHp(0);
        status.setIsDead(true);
        aiManager.setOn(false);
        animationManager.setIsDying(true);
        currentAbility = null;
        audioPlayer.playSound(AudioLibrary.GOBLIN_DEATH);
        for(LivingObject attacker: status.getAttackers()){
            attacker.attackedObjectDied(this);
        }

    }

    /** Setters **/
    public void setCurrentAbility(Ability currentAbility) {
        this.currentAbility = currentAbility;
    }

    public void setTarget(LivingObject target) {
        this.target = target;
    }

    /** Getters **/
    public ArrayList<Item> getLoot() {
        return lootTable.generateLoot();
    }

    public LootTable getLootTable() {
        return lootTable;
    }

    public AIManager getAiManager() {
        return aiManager;
    }

    public LivingObject getTarget() {
        return target;
    }

    /** Collision **/
    @Override
    protected void handleCollisions(GameObject other) {
        if(other instanceof Player){
            if (controller.isRequestingUp()) {position.setY(position.getY() + motion.getSpeed());}
            if (controller.isRequestingDown()) {position.setY(position.getY() - motion.getSpeed());}
            if (controller.isRequestingLeft()) {position.setX(position.getX() + motion.getSpeed());}
            if (controller.isRequestingRight()) {position.setX(position.getX() - motion.getSpeed());}
        }
    }

    @Override
    protected void handleIsInMeleeRangeOfCollisions(GameObject other) {
        if(other instanceof Player) {
            Player player = (Player) other;
            if(player.getTarget() == this){
                player.getStatus().setHasTargetInReach(true);
            }
        }
    }

    @Override
    protected void handleDetectionCollisions(GameObject otherGameObject) {
        if(!((LivingObject)otherGameObject).isDead()){
            if(otherGameObject instanceof Player){

                /** NPC SEES PLAYER **/
                if(status.isAggressiveOnDectection()){
                    if(!status.getAttackedObjects().contains(otherGameObject)){
                        this.aggroes((LivingObject) otherGameObject);
                    }
                }
            }
        }
    }

    @Override
    protected void handleMouseCollisions(GameObject other){}

    @Override
    public boolean meleeRangeCollidesWith(GameObject other) {
        return false;
    }

    @Override
    public boolean mouseCollidesWith(GameObject other) {
        return false;
    }

    @Override
    public boolean detectionCollidesWith(GameObject other) {
        return false;
    }
}


