package quest;

import item.Item;

public class QuestReward {

    int exp;
    int gold;

    Item[] itemReward;

    public QuestReward(int exp, int gold){
        this.exp = exp;
        this.gold = gold;
    }

    public QuestReward(int exp, int gold, Item itemReward){
        this.exp = exp;
        this.gold = gold;
        this.itemReward = new Item[4];
        this.itemReward[0] = itemReward;
    }

    public QuestReward(int exp, int gold, Item itemReward1, Item itemReward2){
        this.exp = exp;
        this.gold = gold;
        this.itemReward = new Item[4];
        this.itemReward[0] = itemReward1;
        this.itemReward[1] = itemReward2;
    }

    public QuestReward(int exp, int gold, Item itemReward1, Item itemReward2, Item itemReward3){
        this.exp = exp;
        this.gold = gold;
        this.itemReward = new Item[4];
        this.itemReward[0] = itemReward1;
        this.itemReward[1] = itemReward2;
        this.itemReward[2] = itemReward3;
    }

    public QuestReward(int exp, int gold, Item itemReward1, Item itemReward2, Item itemReward3, Item itemReward4){
        this.exp = exp;
        this.gold = gold;
        this.itemReward = new Item[4];
        this.itemReward[0] = itemReward1;
        this.itemReward[1] = itemReward2;
        this.itemReward[2] = itemReward3;
        this.itemReward[3] = itemReward4;
    }

    public int getExp() {
        return exp;
    }

    public int getGold() {
        return gold;
    }

    public Item[] getItemReward() {
        return itemReward;
    }
}
