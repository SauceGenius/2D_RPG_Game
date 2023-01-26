package quest;

import item.Item;
import item.ItemId;
import item.OneHandWeapon;
import gfx.SpriteLibrary;

public class QuestGenerator {
    public static final int WELCOME_QUEST = 0;

    private SpriteLibrary spriteLibrary;

    public QuestGenerator(SpriteLibrary spriteLibrary){
        this.spriteLibrary = spriteLibrary;
    }

    public Quest generateQuest(int questID){
        if(questID == WELCOME_QUEST){
            String title = "Humble beginning";
            String body = "We have a problem with them goblins. They be eating all our food and terrorizing our women. Is there gonna be somebody capable enough to rid us of them pesky rats.";
            int exp = 400;
            int gold = 30;
            Item itemReward = new OneHandWeapon(ItemId.twoHandWeapon, spriteLibrary.getIcon("inv_sword_34"));
            return new Quest(title,body, new QuestReward(exp, gold, itemReward));
        } else return null;
    }
}
