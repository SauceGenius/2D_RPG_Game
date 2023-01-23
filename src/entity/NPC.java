package entity;

import ai.AIManager;
import ai.state.Aggressive;
import audio.AudioPlayer;
import controller.Controller;
import controller.NPCController;
import controller.PlayerController;
import core.Log;
import display.CursorManager;
import entity.item.Item;
import entity.item.ItemId;
import entity.item.OneHandWeapon;
import entity.stats.Stats;
import core.Time;
import game.state.State;
import gfx.AnimationManager;
import gfx.SpriteLibrary;

public class NPC extends MovingEntity {

    // Variables
    private AIManager aiManager;
    private Item loot;
    private Time timer;
    private NPCController npcController;

    // Constructor
    public NPC(Controller controller, AudioPlayer audioPlayer, SpriteLibrary spriteLibrary, Log log) {
        super(controller, audioPlayer, spriteLibrary, log);
        this.npcController = (NPCController) controller;
        this.gameObjectID = GameObjectID.NPC;
        this.stats = new Stats(gameObjectID, 1);
        this.stats.getLevel().setExp(30);
        animationManager = new AnimationManager(stats, status, controller, spriteLibrary.getUnit("goblin"));
        aiManager = new AIManager();
        this.loot = new OneHandWeapon(ItemId.wornShortSword, spriteLibrary);
        this.name = "Goblin";
        timer = new Time();
        this.motion.setSpeed(2);
    }

    // Methods
    @Override
    public void update(State state) {
        timer.startUpdateClock();
        if(status.isHurt()) {
            npcController.stop();
            if (timer.getUpdatesSinceStart() >= 40) {
                status.setIsHurt(false);
            }
        }
        status.setHasTargetInReach(false);
        status.setInReachOfPlayerInteractionBox(false);
        super.update(state);
        manageDirection();
        decideAnimation();
        animationManager.update(direction);
        aiManager.update(state, this);
    }

    @Override
    protected void decideAnimation() {
        if (isDead()) {animationManager.playAnimation("Dying");}
        else if (isHurt()) {animationManager.playAnimation("Hurt");}
        else if (motion.isAttacking()) {animationManager.playAnimation("Attack1");}
        else if (motion.isMoving()) {animationManager.playAnimation("Run");}
        else {animationManager.playAnimation("Idle");}
    }

    @Override
    public boolean attackCollidesWith(GameObject other) {return false;}
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
    protected void handleInteractionCollisions(GameObject other) {
        if(other instanceof Player){
            Player player = (Player) other;
            status.setInReachOfPlayerInteractionBox(true);

            //Attacking
            if(!this.isDead()) {
                if (this == player.getTarget()) {
                    player.status.setHasTargetInReach(true);

                    if (player.isAutoAttacking()){
                        if (status.isHurt() == false) {
                            status.setIsHurt(true);

                            if(player.hits())
                            {
                                NPCController controller = (NPCController) getController();
                                controller.stop();

                                int damage;

                                if(player.crits())
                                {
                                    damage = (int)player.attackDamage(true);
                                    audioPlayer.playMeleeAttackSound(true);
                                    log.addToDamageLog(other.name, this.name, Integer.toString(this.position.intX()), Integer.toString(this.position.intY()),"Critical",Integer.toString(damage), "Physical");

                                } else {
                                    damage = (int)player.attackDamage(false);
                                    audioPlayer.playMeleeAttackSound(false);
                                    log.addToDamageLog(other.name, this.name, Integer.toString(this.position.intX()), Integer.toString(this.position.intY()),"Hit",Integer.toString(damage), "Physical");
                                }

                                double newHP = stats.getCurrentHpValue() - damage;
                                stats.setCurrentHpValue(newHP);

                                if (stats.getCurrentHpValue() <= 0) {
                                    this.dies();
                                    log.addToDamageLog(player.getName(), this.getName(), Integer.toString(this.position.intX()), Integer.toString(this.position.intY()), "Slain", "null", "null");
                                    player.setTarget(null);
                                    other.gainExp(this);
                                }
                            } else {
                                // Miss
                                audioPlayer.playSound("MissWhoosh2Handed.wav");
                                log.addToDamageLog(other.name, this.name, Integer.toString(this.position.intX() - 20), Integer.toString(this.position.intY()),"Miss","Miss", "Miss");
                            }
                            timer = new Time();
                        }
                    }
                }
            }

            //Looting
            if(status.isDead() && status.hasBeenLooted() == false) {
                PlayerController playerController = player.getPlayerController();
                if(playerController.isRequestingLoot()) {
                    player.loots(this);
                }
            }
        }
    }

    @Override
    protected void handleMouseCollisions(GameObject other) {
        if(other instanceof Player){

            Player player = (Player) other;
            if(player.getPlayerController().isClicking() && this != player.getTarget()){
                player.setTarget((MovingEntity) this);
                audioPlayer.playSound("SelectTarget.wav");
                player.status.setInCombat(true);
            }
        }
    }

    @Override
    protected void handleDetectionCollisions(GameObject otherGameObject) {
        if(!isDead()){
            if (otherGameObject instanceof Player){
                if (((Player) otherGameObject).getTarget() == null){
                    ((Player) otherGameObject).setTarget(this);
                     otherGameObject.status.setInCombat(true);
                }
                getAiManager().setCurrentAIState(new Aggressive((Player)otherGameObject));
            }
        }
    }

    @Override
    protected void dies(){
        status.setIsDead(true);
        aiManager.setOn(false);
        animationManager.setIsDying(true);
    }

    //Setters
    public void setLoot(Item loot) {this.loot = loot;}

    //Getters
    @Override
    public boolean isInReach() {return status.isInReachOfPlayerInteractionBox();}
    @Override
    public boolean isHurt(){return status.isHurt();}
    @Override
    public boolean isDead(){return status.isDead();}
    public Item getLoot() {return loot;}
    @Override
    public boolean hasBeenLooted(){return status.hasBeenLooted();}
    public AIManager getAiManager() {return aiManager;}

    //Always false
    @Override
    public boolean mouseCollidesWith(GameObject other) {return false;}
    @Override
    public boolean detectionCollidesWith(GameObject other) {return false;}
    @Override
    public void gainExp(GameObject gameObject) {}
}
