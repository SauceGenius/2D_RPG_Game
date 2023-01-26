package ui.inventoryui;

import audio.AudioPlayer;
import core.Position;
import inventory.Inventory;
import item.Item;
import ui.UI;
import ui.button.ItemIcon;

import java.awt.*;
import java.util.ArrayList;
;

public class InventoryUI extends UI {

    private static final int INVENTORY_SIZE = 16;
    private InventorySlot[] inventorySlots;
    private Inventory inventory;

    public InventoryUI(Inventory inventory) {
        opened = false;
        this.inventory = inventory;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image bagImage = toolkit.getImage("resources/sprites/ui/Inventory.png");
        images.add(bagImage);
        position = new Position(1600,700);
        inventorySlots = new InventorySlot[INVENTORY_SIZE];
        for(int i = 0; i < INVENTORY_SIZE; i++) inventorySlots[i] = new InventorySlot(i, position);
    }

    public void update() {
        for(int i = 0; i < INVENTORY_SIZE; i++){
            inventorySlots[i].update(inventory);
        }
    }

    @Override
    public void render(Graphics graphics){
        if(opened){
            //Background Inventory Image
            graphics.drawImage(images.get(0), position.intX(), position.intY(), null);

            //Each Inventory Slot and Item
            for(int i = 0; i < INVENTORY_SIZE; i++){
                inventorySlots[i].render(graphics);
            }

            //Tooltip
            for(int i = 0; i < INVENTORY_SIZE; i++){
                if(inventorySlots[i].mouseIsOver() && inventorySlots[i].getItem() != null && !inventorySlots[i].getItem().getItemIcon().isDragged()){
                    inventorySlots[i].getItem().getTooltip().render(graphics, inventorySlots[i].getPosition());
                }
            }
        }
    }

    @Override
    public void toggle(AudioPlayer audioPlayer){
        if (!opened) {
            opened = true;
            audioPlayer.playSound("OpenInventory.wav");
        } else {
            opened = false;
            audioPlayer.playSound("CloseInventory.wav");
        }
    }

    public void addItem(Item item){
        for(int i = 0; i < INVENTORY_SIZE; i++){
            if(inventorySlots[i].getItem() == null){
                inventorySlots[i].setItem(item);
            }
        }
    }

    public InventorySlot[] getInventorySlots() {
        return inventorySlots;
    }

    public ArrayList<ItemIcon> getItemIcons(){
        ArrayList<ItemIcon> itemIcons = new ArrayList<>();
        for (int i = 0; i < inventorySlots.length; i++){
            if(inventorySlots[i].getItemIcon() != null){
                itemIcons.add(inventorySlots[i].getItemIcon());
            }
        }
        return itemIcons;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
