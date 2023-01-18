package entity;

import audio.AudioPlayer;
import controller.Controller;
import controller.PlayerController;
import core.CollisionBox;
import core.Log;
import entity.stats.Stats;
import core.Timer;
import game.state.State;
import gfx.AnimationManager;
import gfx.SpriteLibrary;

import java.awt.*;

public class Player extends MovingEntity {

    //Variables
    private MovingEntity target;
    private Inventory inventory;
    private Equipment equipment;
    private PlayerController playerController;
    private Timer autoAttackTimer;
    private AudioPlayer audioPlayer;

    //Constructor
    public Player(String userName, Controller controller, AudioPlayer audioPlayer, Stats stats, Inventory inventory, Equipment equipment, SpriteLibrary spriteLibrary, Log log){
        super(controller, audioPlayer, spriteLibrary, log);
        this.playerController = (PlayerController) controller;
        this.gameObjectID = GameObjectID.player;
        this.stats = stats;
        this.name = userName;
        this.animationManager = new AnimationManager(stats, status, controller, spriteLibrary.getUnit("player"));
        this.inventory = inventory;
        this.equipment = equipment;
        this.autoAttackTimer = new Timer(1);
        this.audioPlayer = audioPlayer;
    }

    //Methods
    @Override
    public void update(State state) {
        manageDirection();
        combatUpdate();
        super.update(state);
        decideAnimation();
        animationManager.update(direction);
        stats.update(equipment, audioPlayer, log);
        inventory.update(audioPlayer);
        equipment.update(audioPlayer);

        status.setHasTargetInReach(false);
    }

    private void combatUpdate() {
        autoAttackTimer.update();

        if(target == null){status.setInCombat(false);}

        if (status.isInCombat()) {
            if (status.hasTargetInReach()) {
                if (autoAttackTimer.timeIsUp()) {

                    //Activate auto attack
                    autoAttackTimer.startClock(stats.getAttackSpeed());
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

        double AP = (double)stats.getTotalStrength() * 2 + (double)stats.getLevelValue() * 3 - 20;
        double dpsFromAP = AP / 14;
        double damageFromAP = dpsFromAP * stats.getAttackSpeed();
        double minDamage = stats.getMinMeleeWeaponDamage() + damageFromAP;
        double maxDamage = stats.getMaxMeleeWeaponDamage() + damageFromAP;
        double damage =  minDamage + Math.random() * (maxDamage - minDamage);

        if (isCrit == true){
            return damage * 1.5;
        } else return damage;
    }

    @Override
    protected void decideAnimation() {
        if (isDead()) {animationManager.playAnimation("Dying");}
        else if (isAutoAttacking()) {animationManager.playAutoAttackAnimation();}
        else if (motion.isMoving()) {animationManager.playAnimation("Run");}
        else if (isHurt()) {animationManager.playAnimation("Hurt");}
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
    public boolean clickCollidesWith(GameObject other) {
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
    protected void handleClickCollisions(GameObject other) {}

    @Override
    protected void handleDetectionCollisions(GameObject otherGameObject) {}

    public void loots(NPC npc) {
        this.getInventory().addItem(npc.getLoot());
        npc.status.setHasBeenLooted(true);
        log.addToGeneral("Loot","You receive loot: " + npc.getLoot().getName() + ".");
    }

    @Override
    public void gainExp(GameObject gameObject) {
        int gainedExp = gameObject.getExp();
        stats.getLevel().gainExp(gainedExp);

        log.addToGeneral("Exp","You gained " + gainedExp + " exp.");
    }

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
    public boolean isInReach() {return false;}
    @Override
    protected void dies() {}

    //Setters
    public void setTarget(MovingEntity target) {this.target = target;}
    public void setIsAutoAttacking(boolean isAutoAttacking) {status.setIsAutoAttacking(isAutoAttacking);}
    public void setAudioPlayer(AudioPlayer audioPlayer) {this.audioPlayer = audioPlayer;}

    //Getters
    public Stats getStats() {return stats;}
    public Inventory getInventory() {return inventory;}
    public Equipment getEquipment() {return equipment;}
    public PlayerController getPlayerController(){return this.playerController;}
    public MovingEntity getTarget() {return target;}
    public boolean isAutoAttacking() {return status.isAutoAttacking();}
    public Timer getAutoAttackTimer() {return autoAttackTimer;}
}
