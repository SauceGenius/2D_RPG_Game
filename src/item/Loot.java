package item;

public class Loot {

    Item item;
    double dropChance;

    public Loot(){
        item = null;
    }

    public Loot(Item item, double dropChance){
        this.item = item;
        this.dropChance = dropChance;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setDropChance(double dropChance) {
        this.dropChance = dropChance;
    }

    public Item getItem() {
        return item;
    }

    public double getDropChance() {
        return dropChance;
    }
}
