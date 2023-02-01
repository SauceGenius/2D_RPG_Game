package gameobject;

import inventory.Inventory;
import audio.AudioPlayer;
import controller.MovementController;
import controller.PlayerController;
import core.CollisionBox;
import core.Log;
import equipment.Equipment;
import stats.Stats;
import core.Timer;
import game.state.State;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import id.GameObjectID;

import java.awt.*;
import java.util.List;

public class Player extends MovingEntity {

    //Variables
    private MovingEntity target;
    private Equipment equipment;
    private PlayerController playerController;
    private Timer autoAttackTimer;
    private AudioPlayer audioPlayer;

    //TO REMOVE
    private Inventory inventory;

    /** Constructor **/
    public Player(String userName, MovementController controller, AudioPlayer audioPlayer, Stats stats, Inventory inventory, Equipment equipment, SpriteLibrary spriteLibrary, Log log){
        super(controller, audioPlayer, spriteLibrary, log);
        this.playerController = (PlayerController) controller;
        this.gameObjectID = GameObjectID.player;
        this.stats = stats;
        this.inventory = inventory;
        this.name = userName;
        this.animationManager = new AnimationManager(this, stats, status, controller, spriteLibrary.getUnit("player"));
        this.equipment = equipment;
        this.autoAttackTimer = new Timer(0.1);
        this.audioPlayer = audioPlayer;
    }

    /** Methods **/
    @Override
    public void update(State state) {
        manageDirection();
        combatUpdate();
        super.update(state);
        decideAnimation();
        animationManager.update(direction);
        stats.update(equipment, audioPlayer, log);
        status.setHasTargetInReach(false);
        updateTarget();
        status.update();
    }

    @Override
    protected void decideAnimation() {
        if (isDead()) {animationManager.playAnimation("Dying");}
        else if (isAutoAttacking()) {animationManager.playAutoAttackAnimation();}
        else if (motion.isMoving()) {animationManager.playAnimation("Run");}
        else if (status.isHurt()) {animationManager.playAnimation("Hurt");}
        else {animationManager.playAnimation("Idle");}
    }

    @Override
    protected void handleCollisions(GameObject other) {
        if(other instanceof NPC){
            if (controller.isRequestingUp()) {
                position.setY(position.getY() + motion.getSpeed());
                if(controller.isRequestingSprint()){position.setY(position.getY() + motion.getSpeed());}
            }
            if (controller.isRequestingDown()) {
                position.setY(position.getY() - motion.getSpeed());
                if(controller.isRequestingSprint()){position.setY(position.getY() - motion.getSpeed());}
            }
            if (controller.isRequestingLeft()) {
                position.setX(position.getX() + motion.getSpeed());
                if(controller.isRequestingSprint()){position.setY(position.getY() + motion.getSpeed());}
            }
            if (controller.isRequestingRight()) {
                position.setX(position.getX() - motion.getSpeed());
                if(controller.isRequestingSprint()){position.setY(position.getY() - motion.getSpeed());}
            }
        }
    }

    public CollisionBox getInteractionBox() {
        //South
        if(direction.getAnimationRow() == 0) {return new CollisionBox(new Rectangle(position.intX() - 12, position.intY() + 60, size.getWidth() + 20, size.getHeight() + 20));}
        //West
        else if(direction.getAnimationRow() == 1) {return new CollisionBox(new Rectangle(position.intX() - 60, position.intY() + 20, size.getWidth() + 20, size.getHeight() + 20));}
        //North
        else if(direction.getAnimationRow() == 2) {return new CollisionBox(new Rectangle(position.intX() - 12, position.intY() - 26, size.getWidth() + 20, size.getHeight() + 20));}
        //East
        else {return new CollisionBox(new Rectangle(position.intX() + 36, position.intY() + 20, size.getWidth() + 20, size.getHeight() + 20));}
    }

    public boolean attackCollidesWith(GameObject other) {
        return getInteractionBox().collidesWith(other.getHitBox()) ;
    }

    @Override
    public boolean mouseCollidesWith(GameObject other) {
        CollisionBox pointer = new CollisionBox(new Rectangle(playerController.getMousePosition().intX() - 10, playerController.getMousePosition().intY() - 10, 20, 20));
        return pointer.collidesWith(other.getHitBox());
    }

    @Override
    public boolean detectionCollidesWith(GameObject other) {
        return getDetectionBox().collidesWith(other.getHitBox());
    }

    @Override
    protected void handleInteractionCollisions(GameObject other) {}

    @Override
    protected void handleMouseCollisions(GameObject other) {}

    @Override
    protected void handleDetectionCollisions(GameObject otherGameObject) {}

    public void handleClickOnGameObject(List<GameObject> gameObjects) {

        /** Left-clicking on mob **/
        if(playerController.isLeftClicking()){
            for(GameObject other: gameObjects){
                if(other instanceof NPC){
                    if(!other.isDead()) {
                        targets((MovingEntity) other);
                    }
                }
            }
        }

        /** Right-clicking on mob **/
        else if (playerController.isRightClicking()){
            for(GameObject other: gameObjects){
                if(other instanceof NPC){
                    if(!other.isDead()) {
                        targets((MovingEntity) other);
                        status.setInCombat(true);
                    } else if(playerController.isRightClicking()){
                        loots((NPC)other);
                    }
                }
            }
        }
    }

    private void targets(MovingEntity target){
        setTarget(target);
        audioPlayer.playSound("SelectTarget.wav");
        if(playerController.isRightClicking()){
            status.setInCombat(true);
        }
    }

    private void updateTarget(){
        if(target != null && target.hasBeenLooted()) {setTarget(null);}
    }

    public void aggroed(MovingEntity mob){
        status.setInCombat(true);
        if(target == null){
            setTarget(mob);
        }
    }

    private void combatUpdate() {
        autoAttackTimer.update();
        if(target == null){status.setInCombat(false);}
        if (status.isInCombat()) {
            if (status.hasTargetInReach()) {
                if (autoAttackTimer.timeIsUp()) {
                    //Activate auto attack
                    autoAttackTimer.startClockSeconds(stats.getAttackSpeed());
                    setIsAutoAttacking(true);
                }
            }
        }
    }

    public boolean hits(){
        double hitDice = Math.random();
        if(hitDice <= stats.getHitChance()){
            return true;
        } else return false;
    }

    public boolean crits(){
        double critDice = Math.random();
        if(critDice <= stats.getCritChance()) return true;
        else return false;
    }

    public double attackDamage(boolean isCrit){

        double AP = (double)stats.getStat(Stats.STRENGTH) * 2 + (double)stats.getLevelValue() * 3 - 20;
        double dpsFromAP = AP / 14;
        double damageFromAP = dpsFromAP * stats.getAttackSpeed();
        double minDamage = stats.getMinMeleeWeaponDamage() + damageFromAP;
        double maxDamage = stats.getMaxMeleeWeaponDamage() + damageFromAP;
        double damage =  minDamage + Math.random() * (maxDamage - minDamage);

        if (isCrit == true){
            return damage * 1.5;
        } else return damage;
    }

    public void loots(NPC npc) {
        //TO IMPLEMENT: If inventory is not full
        inventory.addItem(npc.getLoot());
        npc.status.setHasBeenLooted(true);
        log.addToGeneral("Loot","You receive loot: " + npc.getLoot().getName() + ".");
        audioPlayer.playSound("LootCreatureEmpty.wav");
    }

    @Override
    public void gainExp(GameObject gameObject) {
        int gainedExp = gameObject.getExp();
        stats.getLevel().gainExp(gainedExp);

        log.addToGeneral("Exp","You gained " + gainedExp + " exp.");
    }

    @Override
    protected void dies() {}

    /** Setters **/
    @Override
    public void isHit(GameObject attackerObject, int damage) {
        status.setIsHurt(true);
        playerController.loseHP(damage);
    }

    public void setTarget(MovingEntity target) {
        this.target = target;
    }

    public void setIsAutoAttacking(boolean isAutoAttacking) {
        status.setIsAutoAttacking(isAutoAttacking);
    }

    public void setAudioPlayer(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }


    /** Getters **/
    @Override
    public boolean isDead() {
        return false;
    }
    @Override
    public boolean isHurt() {
        return false;
    }
    @Override
    public boolean hasBeenLooted() {
        return false;
    }

    @Override
    public boolean isInReach() {
        return false;
    }

    public Stats getStats() {
        return stats;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public PlayerController getPlayerController(){
        return this.playerController;
    }
    public MovingEntity getTarget() {
        return target;
    }

    public boolean isAutoAttacking() {
        return status.isAutoAttacking();
    }

    public Timer getAutoAttackTimer() {
        return autoAttackTimer;
    }
}
