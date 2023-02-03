package gameobject;

import audio.AudioLibrary;
import core.*;
import inventory.Inventory;
import audio.AudioPlayer;
import controller.MovementController;
import controller.PlayerController;
import equipment.Equipment;
import item.Item;
import stats.Stats;
import game.state.State;
import gfx.AnimationManager;
import gfx.SpriteLibrary;

import java.awt.*;
import java.util.List;

public class Player extends MovingEntity {

    /** Variables **/
    private PlayerController playerController;
    private Equipment equipment;
    private Timer autoAttackTimer;
    private LivingObject target;

    /** TO REMOVE **/
    private Inventory inventory;

    /** Constructor **/
    public Player(String userName, MovementController controller, AudioPlayer audioPlayer, Stats stats, Inventory inventory, Equipment equipment, SpriteLibrary spriteLibrary, Log log){
        super(audioPlayer, log);
        this.size = new Size(96,96);
        this.runningSpeed = 3;
        this.motion.setSpeed(runningSpeed);
        this.controller = controller;
        this.playerController = (PlayerController) controller;
        this.stats = stats;
        this.inventory = inventory;
        this.name = userName;
        this.animationManager = new AnimationManager(this, stats, status, controller, spriteLibrary.getUnit("player"));
        this.equipment = equipment;
        this.autoAttackTimer = new Timer(0.1);
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
        updateTarget();
        status.update(this);
    }

    @Override
    protected void decideAnimation() {
        //if (isDead()) {animationManager.playAnimation("Dying");}
        /*else*/ if (isAutoAttacking()) {animationManager.playAutoAttackAnimation();}
        else if (isHurt()) {animationManager.playAnimation("Hurt");}
        else if (motion.isMoving()) {animationManager.playAnimation("Run");}
        else {animationManager.playAnimation("Idle");}
    }

    /** Target and Aggro **/
    private void targets(LivingObject target){
        setTarget(target);
        audioPlayer.playSound(AudioLibrary.SELECT_TARGET);
        if(playerController.isRightClicking()){
            status.setAggressiveOnDetection(true);
        }
    }

    public void aggroed(LivingObject aggroedGameObject){
        if(!isInCombat()){
           enteringCombat();
        }
        if(target == null){
            targets(aggroedGameObject);
        }
        status.addAttacker(aggroedGameObject);
    }

    private void updateTarget(){
        if(target != null && target.hasBeenLooted()) {
            setTarget(null);
        }
    }

    /** Attacks **/
    private void combatUpdate() {
        autoAttackTimer.update();
        if(status.getAttackers().size() == 0 && status.isInCombat()){
            /** Will have to find a better way to leave combat **/
            leavingCombat();
        }
        if (status.isAggressiveOnDectection()) {
            if (status.hasTargetInReach() && !target.isDead()) {
                if (autoAttackTimer.timeIsUp()) {
                    /** Activate Auto Attack **/
                    autoAttackTimer.startClockSeconds(stats.getAttackSpeed());
                    setIsAutoAttacking(true);
                    autoAttacks(target);
                }
            }
        }
    }

    public void autoAttacks(LivingObject target){
        if(!isInCombat()) enteringCombat();
        if(attackHits()) {
            int damage;
            if(attackCrits()) {
                damage = (int)attackDamage(true);
                audioPlayer.playMeleeAttackSound(true);
                log.addToDamageLog(name, target.name, Integer.toString(target.position.intX()), Integer.toString(target.position.intY()),"Critical",Integer.toString(damage), "Physical");
            } else {
                damage = (int)attackDamage(false);
                audioPlayer.playMeleeAttackSound(false);
                log.addToDamageLog(name, target.name, Integer.toString(target.position.intX()), Integer.toString(target.position.intY()),"Hit",Integer.toString(damage), "Physical");
            }

            target.isHit(this, damage);
        } else {
            /** Miss **/
            audioPlayer.playSound(AudioLibrary.MISS_2H);
            log.addToDamageLog(name,target.name, Integer.toString(target.position.intX() - 20), Integer.toString(target.position.intY()),"Miss","Miss", "Miss");
        }
        status.addAttackedObject(target);
    }

    public boolean attackHits(){
        double hitDice = Math.random();
        if(hitDice <= stats.getHitChance()){
            return true;
        } else return false;
    }

    public boolean attackCrits(){
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

    @Override
    public void isHit(LivingObject attackerObject, int damage) {
        if(!isInCombat()){
            enteringCombat();
        }
        if(target == null){
            target = attackerObject;
        }
        status.setAggressiveOnDetection(true);
        status.setIsHurt(true);
        playerController.loseHP(damage);
    }

    @Override
    public void attackerObjectDied(LivingObject killedGameObject) {
        if(killedGameObject == target){
            target = null;
        }
        status.removeAttacker(killedGameObject);
    }

    @Override
    public void attackedObjectDied(LivingObject killedGameObject) {
        if(killedGameObject == target){
            target = null;
        }
        status.removeAttackedObject(killedGameObject);

        gainExp(killedGameObject);
    }

    public void gainExp(LivingObject gameObject) {
        int gainedExp = gameObject.getExp();
        stats.getLevel().gainExp(gainedExp);

        log.addToGeneral("Exp","You gained " + gainedExp + " exp.");
    }

    public void loots(NPC npc) {
        //TO IMPLEMENT: If inventory is not full
        for(Item item: npc.getLoot()){
            inventory.addItem(item);
            log.addToGeneral("Loot","You receive loot: " + item.getName() + ".");
        }
        npc.status.setHasBeenLooted(true);
        audioPlayer.playSound(AudioLibrary.LOOT_SOUND_EFFECT);
    }

    @Override
    public void dies() {
        status.setIsDead(true);
        System.out.println("You died");
        target = null;
        status.setInCombat(false);

        for(int i = 0; i < status.getAttackers().size(); i++){
            status.getAttackers().get(i).attackedObjectDied(this);
            status.removeAttacker(status.getAttackers().get(i));
            i--;
        }

        for(int i = 0; i < status.getAttackedObjects().size(); i++){
                status.getAttackedObjects().get(i).attackerObjectDied(this);
                status.removeAttackedObject(status.getAttackedObjects().get(i));
                i--;
        }
    }

    public void respawn(){
        setPosition(new Position(50,50));
        status.setIsDead(false);
        target = null;
        status.setInCombat(false);
        stats.getHp().setCurrentHp(stats.getMaxHpValue());
    }

    public void enteringCombat(){
        System.out.println(this.name + " is entering combat.");
        status.setInCombat(true);
    }

    public void leavingCombat(){
        System.out.println(this.name + " is leaving combat.");
        status.setInCombat(false);
        stats.getHp().resetHp5Timer();
    }

    /** Setters **/
    public void setTarget(LivingObject target) {
        this.target = target;
    }

    public void setIsAutoAttacking(boolean isAutoAttacking) {
        status.setIsAutoAttacking(isAutoAttacking);
    }

    /** Getters **/
    public Stats getStats() {
        return stats;
    }

    public Equipment getEquipment() {
        return equipment;
    }
    public PlayerController getPlayerController(){
        return this.playerController;
    }

    public LivingObject getTarget() {
        return target;
    }

    public Timer getAutoAttackTimer() {
        return autoAttackTimer;
    }

    /** Collision **/
    public CollisionBox getInteractionBox() {
        int interactionWidth = 136;
        int interactionHeight = 116;
        //South
        if(direction.getAnimationRow() == 0) {return new CollisionBox(new Rectangle(position.intX() - interactionWidth / 2, position.intY() + 20, interactionWidth, interactionHeight));}
        //West
        else if(direction.getAnimationRow() == 1) {return new CollisionBox(new Rectangle(position.intX() - interactionHeight, position.intY() - interactionHeight / 2, interactionHeight, interactionWidth));}
        //North
        else if(direction.getAnimationRow() == 2) {return new CollisionBox(new Rectangle(position.intX() - interactionWidth / 2, position.intY() - interactionWidth + 40, interactionWidth, interactionHeight));}
        //East
        else {return new CollisionBox(new Rectangle(position.intX(), position.intY() - interactionHeight / 2, interactionHeight, interactionWidth));}
    }

    public boolean meleeRangeCollidesWith(GameObject other) {
        return getInteractionBox().collidesWith(other.getHitBox()) ;
    }

    @Override
    public boolean mouseCollidesWith(GameObject other) {
        if(other instanceof LivingObject){
            CollisionBox pointer = new CollisionBox(new Rectangle(playerController.getMousePosition().intX() - 10, playerController.getMousePosition().intY() - 10, 20, 20));
            return pointer.collidesWith(other.getHitBox());
        }
        else return false;
    }

    @Override
    public boolean detectionCollidesWith(GameObject other) {
        return getDetectionBox().collidesWith(other.getHitBox());
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

    public void handleClickOnGameObject(List<GameObject> gameObjects) {
        /** Left-clicking on mob **/
        if(playerController.isLeftClicking()){
            for(GameObject other: gameObjects){
                if(other instanceof NPC){
                    targets((LivingObject) other);
                    if(!((LivingObject)other).isDead()) {
                    }
                }
            }
        }

        /** Right-clicking on mob **/
        else if (playerController.isRightClicking()){
            for(GameObject other: gameObjects){
                if(other instanceof NPC){
                    if(!((LivingObject)other).isDead()) {
                        targets(((LivingObject)other));
                        status.setAggressiveOnDetection(true);
                    } else if(playerController.isRightClicking()){
                        if (meleeRangeCollidesWith(other)){
                            loots((NPC)other);
                        } else {
                            System.out.println("Target is too far away");
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void handleIsInMeleeRangeOfCollisions(GameObject other) {}

    @Override
    protected void handleMouseCollisions(GameObject other) {}

    @Override
    protected void handleDetectionCollisions(GameObject otherGameObject) {}
}
