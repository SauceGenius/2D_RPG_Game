package gfx;

import controller.MovementController;
import core.Direction;
import gameobject.MovingEntity;
import gameobject.NPC;
import gameobject.Player;
import stats.Stats;
import stats.StatusLiving;
import core.Time;
import settings.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimationManager {

    private MovingEntity movingEntity;
    private MovementController controller;
    private SpriteSet spriteSet;
    private Stats stats;
    private StatusLiving status;
    private BufferedImage currentAnimationSheet;
    private Time autoAttackAnimationTimer;
    private int updatesPerFrame;
    private int currentFrameTime;
    private int frameIndex;
    private int directionIndex;
    private Time deathTime;
    private boolean isPlayingAutoAttackAnimation;
    private boolean isDying;

    public AnimationManager(MovingEntity movingEntity, Stats stats, StatusLiving status, MovementController controller, SpriteSet spriteSet){
        this.movingEntity = movingEntity;
        this.isDying = false;
        this.stats = stats;
        this.status = status;
        this.controller = controller;
        this.spriteSet = spriteSet;
        this.updatesPerFrame = 5;
        this.frameIndex = 0;
        this.currentFrameTime = 0;
        this.directionIndex = 0;
        this.deathTime = new Time();
        this.autoAttackAnimationTimer = new Time();
        this.isPlayingAutoAttackAnimation = false;
    }

    public Image getSprite(){
        if(frameIndex >= currentAnimationSheet.getWidth() / Settings.SPRITE_SIZE_PLAYER) {
            frameIndex = 0;
        }

        return currentAnimationSheet.getSubimage(
                frameIndex * Settings.SPRITE_SIZE_PLAYER,
                directionIndex * Settings.SPRITE_SIZE_PLAYER,
                Settings.SPRITE_SIZE_PLAYER,
                Settings.SPRITE_SIZE_PLAYER);
    }

    public void update(Direction direction){
        /** Auto Attack Timer **/
        if(autoAttackAnimationTimer.getUpdatesFromSeconds(3) == autoAttackAnimationTimer.getUpdatesSinceStart()){
            isPlayingAutoAttackAnimation = false;
        }

        if(isDying){
            deathTime.startUpdateClock();
            if(deathTime.getUpdatesSinceStart() < 60){
                currentFrameTime++;
                directionIndex = direction.getAnimationRow();

                if(currentFrameTime >= updatesPerFrame) {
                    currentFrameTime = 0;
                    frameIndex++;

                    if (frameIndex >= currentAnimationSheet.getWidth() / Settings.SPRITE_SIZE_PLAYER) {
                        frameIndex = 0;
                    }
                }
            }
        } else if (isPlayingAutoAttackAnimation) {
            autoAttackAnimationTimer.startUpdateClock();

            int attackUpdatesNeeded = 0;
            if(movingEntity instanceof Player) {
                attackUpdatesNeeded = 40;
            } else if (movingEntity instanceof NPC){
                attackUpdatesNeeded = 60;
            }
            if (autoAttackAnimationTimer.getUpdatesSinceStart() <= attackUpdatesNeeded) {
                currentFrameTime++;
                directionIndex = direction.getAnimationRow();
                playAnimation("Attack1");

                if (currentFrameTime >= updatesPerFrame) {
                    currentFrameTime = 0;
                    frameIndex++;

                    if (frameIndex >= currentAnimationSheet.getWidth() / Settings.SPRITE_SIZE_PLAYER) {
                        frameIndex = 0;
                    }
                }
            } else {
                isPlayingAutoAttackAnimation = false;
                status.setIsAutoAttacking(false);
            }
        } else {
            currentFrameTime++;
            directionIndex = direction.getAnimationRow();

            if(currentFrameTime >= updatesPerFrame) {
                currentFrameTime = 0;
                frameIndex++;

                if (frameIndex >= currentAnimationSheet.getWidth() / Settings.SPRITE_SIZE_PLAYER) {
                    frameIndex = 0;
                }
            }
        }
    }

    public void playAnimation(String name) {
        if(!isPlayingAutoAttackAnimation) {
            if (name == "Idle") updatesPerFrame = 30;
            if (name == "Run") updatesPerFrame = 5;
            if(name == "Attack1") updatesPerFrame = 7;
            if (name == "Dying") updatesPerFrame = 8;
            if (name == "Hurt") updatesPerFrame = 4;
        }

        this.currentAnimationSheet = (BufferedImage) spriteSet.get(name);

    }

    public void playAutoAttackAnimation(){
        if(!isPlayingAutoAttackAnimation){
            frameIndex = 0;
            updatesPerFrame = 7;
            autoAttackAnimationTimer.restartClock();
            playAnimation("Attack1");
            isPlayingAutoAttackAnimation = true;
        }
    }

    public void setIsDying(boolean isDying) {
        frameIndex = 0;
        this.isDying = isDying;
    }

    public void setSpriteSet(SpriteSet spriteSet) {
        this.spriteSet = spriteSet;
    }

    /*public void attackAnimation() {
        frameIndex = 0;
        attackTime.restartClock();
        this.isAttacking = true;
    }*/

    public boolean isPlayingAutoAttackAnimation() {return isPlayingAutoAttackAnimation;}
}
