package inventory;

import item.Item;

public class Inventory {

    private int space = 16;
    Item items[] = new Item[space];

    public Inventory() {

    }

    public void update() {

    }

    public void addItem(Item item){
        for(int i = 0; i < items.length; i++){
            if (items[i] == null){
                items[i] = item;
                break;
            } else if(i == items.length - 1 && items[i] != null){
                System.out.println("Inventory is full");
            }
        }
    }

    public Item[] getItems() {
        return items;
    }
    public int getSize(){
        return space;}

    public void setItem(int i, Item item){
        items[i] = item;
    }
}




