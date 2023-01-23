package gfx;

import controller.Controller;
import core.Direction;
import entity.stats.Stats;
import entity.stats.Status;
import game.Game;
import core.Time;
import settings.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimationManager {

    private Controller controller;
    private SpriteSet spriteSet;
    private Stats stats;
    private Status status;
    private BufferedImage currentAnimationSheet;
    private Time autoAttackAnimationTimer;
    private int updatesPerFrame;
    private int currentFrameTime;
    private int frameIndex;
    private int directionIndex;
    private Time deathTime;
    private boolean isPlayingAutoAttackAnimation;
    private boolean isDying;

    public AnimationManager(Stats stats, Status status, Controller controller, SpriteSet spriteSet){
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
        //Auto attack timer
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

            if (autoAttackAnimationTimer.getUpdatesSinceStart() <= 40) {
                currentFrameTime++;
                directionIndex = direction.getAnimationRow();
                playAnimation("playerAttack1");

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
            //if(name == "playerAttack1") updatesPerFrame = 7;
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
            playAnimation("playerAttack1");
            isPlayingAutoAttackAnimation = true;
        }
    }

    public void setIsDying(boolean isDying) {
        frameIndex = 0;
        this.isDying = isDying;
    }

    /*public void attackAnimation() {
        frameIndex = 0;
        attackTime.restartClock();
        this.isAttacking = true;
    }*/

    public boolean isPlayingAutoAttackAnimation() {return isPlayingAutoAttackAnimation;}
}
