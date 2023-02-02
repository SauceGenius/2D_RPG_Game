package gameobject.mob;

import audio.AudioLibrary;
import audio.AudioPlayer;
import core.Log;
import core.Timer;
import game.state.State;
import gameobject.NPC;
import stats.Stats;

public class GoblinBerserker extends NPC {

    private Timer soundTimer;

    /** Constructor **/
    public GoblinBerserker(int level, AudioPlayer audioPlayer, Log log) {
        super(audioPlayer, log);
        this.name = "Goblin Berserker";
        this.stats = new Stats(this, level);
        this.stats.getLevel().setExp(45);
        this.runningSpeed = 2.5;
        this.motion.setSpeed(walkingSpeed);
        this.status.setAggressiveOnDetection(true);
        this.animationAttackSpeed = 0.8;
        this.soundTimer = new Timer(0);
    }

    public void update(State state){
        super.update(state);
        soundTimer.update();
        if(soundTimer.timeIsUp() && soundTimer.isOn()) playHitSoundEffect();

    }

    @Override
    protected void playAutoAttackSound() {
        double dice = Math.random() * 6;
        if(dice > 5) audioPlayer.playSound(AudioLibrary.GOBLIN_ATTACK1);
        else if(dice > 4) audioPlayer.playSound(AudioLibrary.GOBLIN_ATTACK2);
        else if(dice > 3) audioPlayer.playSound(AudioLibrary.GOBLIN_ATTACK3);

        soundTimer.startClockSeconds(animationAttackSpeed);
    }

    private void playHitSoundEffect(){
        double dice = Math.random() * 3;
        if(dice > 2) audioPlayer.playSound(AudioLibrary.AXE_CRIT_HIT1);
        else if(dice > 1) audioPlayer.playSound(AudioLibrary.AXE_CRIT_HIT2);
        else audioPlayer.playSound(AudioLibrary.AXE_CRIT_HIT3);

        soundTimer.setOn(false);
    }
}
