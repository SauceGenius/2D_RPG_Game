package gameobject;

import audio.AudioPlayer;
import core.Log;
import gameobject.mob.GoblinBerserker;
import gameobject.mob.GoblinSlinger;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import item.ItemId;
import item.OneHandWeapon;

public class NPCGenerator {

    public static final int GOBLIN_BERSERKER = 0;
    public static final int GOBLIN_SLINGER = 1;

    private SpriteLibrary spriteLibrary;
    private AudioPlayer audioPlayer;
    private Log log;

    public NPCGenerator(SpriteLibrary spriteLibrary, AudioPlayer audioPlayer, Log log){
        this.spriteLibrary = spriteLibrary;
        this.audioPlayer = audioPlayer;
        this.log = log;
    }

    public NPC generateNPC(int npcIndex, int level){

        NPC npc = null;

        switch (npcIndex){
            case GOBLIN_BERSERKER -> npc = generateGoblinBerserker(level);
            case GOBLIN_SLINGER -> npc = generateGoblinSlinger(level);
        }

        return npc;
    }

    private NPC generateGoblinBerserker(int level){
        NPC npc = new GoblinBerserker(level, audioPlayer, log);
        npc.setAnimationManager(new AnimationManager(npc, npc.stats, npc.status, npc.controller, spriteLibrary.getUnit("goblinberserker")));
        npc.getAnimationManager().setSpriteSet(spriteLibrary.getUnit("goblinberserker"));
        npc.getLootTable().addPossibleDrop(new OneHandWeapon(ItemId.wornShortSword, spriteLibrary.getIcon("inv_sword_34")), 1);
        return npc;
    }

    private NPC generateGoblinSlinger(int level) {
        NPC npc = new GoblinSlinger(level, audioPlayer, log);
        npc.setAnimationManager(new AnimationManager(npc, npc.stats, npc.status, npc.controller, spriteLibrary.getUnit("goblinslinger")));
        npc.getAnimationManager().setSpriteSet(spriteLibrary.getUnit("goblinslinger"));
        npc.getLootTable().addPossibleDrop(new OneHandWeapon(ItemId.wornShortSword, spriteLibrary.getIcon("inv_sword_34")), 1);
        return npc;
    }
}
