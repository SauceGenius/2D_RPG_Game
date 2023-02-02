package item;

import java.util.ArrayList;

public class LootTable {

    private ArrayList<Loot> lootList;

    public LootTable(){
        lootList = new ArrayList<>();
    }

    public void addPossibleDrop(Item item, double dropChance){
        this.lootList.add(new Loot(item, dropChance));
    }

    public ArrayList<Item> generateLoot(){
        ArrayList<Item> lootItems = new ArrayList<>();
        double dice = Math.random();
        for(Loot loot: lootList){
            if (loot.dropChance >= dice){
                lootItems.add(loot.getItem());
            }
        }
        return lootItems;
    }
}
