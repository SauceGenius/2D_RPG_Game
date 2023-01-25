package ui.inventory;

import audio.AudioPlayer;
import core.Position;
import Inventory.Inventory;
import ui.UI;
import ui.button.ItemIcon;

import java.awt.*;
import java.util.ArrayList;
;

public class InventoryUI extends UI {

    private static final int INVENTORY_SIZE = 16;
    private InventorySlot[] inventorySlots;

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
    private Inventory inventory;

    public void update() {}

    @Override
    public void render(Graphics graphics){
        if(opened){
            graphics.drawImage(images.get(0), position.intX(), position.intY(), null);

            // Each space (items)
            int iconWidth = 36;
            int iconHeight = 36;
            int itemPosX = position.intX() + 20;
            int itemPosY = position.intY() + 56;
            int marginX = 8;
            int marginY = 8;

            for(int i = 0; i < INVENTORY_SIZE; i++){
                inventorySlots[i].render(graphics);
            }

            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    // Render item icons
                    if (inventory.getItems()[(x + y * 4)] != null) {
                        graphics.drawImage(inventory.getItems()[(x + y * 4)].getIconSprite(), x * (iconWidth + marginX) + itemPosX, y * (iconHeight + marginY) + itemPosY, null);
                    }
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

}