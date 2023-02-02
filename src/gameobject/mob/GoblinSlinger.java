package gameobject.mob;

import audio.AudioPlayer;
import core.Log;
import gameobject.NPC;
import stats.Stats;

public class GoblinSlinger extends NPC {
    /**
     * Constructor
     *
     * @param audioPlayer
     * @param log
     **/
    public GoblinSlinger(int level, AudioPlayer audioPlayer, Log log) {
        super(audioPlayer, log);
        this.name = "Goblin Slinger";
        this.stats = new Stats(this, level);
        this.stats.getLevel().setExp(30);
        this.motion.setSpeed(2);
        this.status.setAggressiveTowardTarget(false);
        this.status.setRanged(true);
        this.animationAttackSpeed = 0.8;
    }

    @Override
    protected void playAutoAttackSound() {

    }
}
