package ui.inventoryui;

import audio.AudioPlayer;
import core.Position;
import inventory.Inventory;
import ui.UI;
import ui.tooltip.TooltipGenerator;

import java.awt.*;

public class InventoryUI extends UI {

    private static final int INVENTORY_SIZE = 16;
    private InventorySlot[] inventorySlots;
    private Inventory inventory;
    private TooltipGenerator tooltipGenerator;

    public InventoryUI(Inventory inventory) {
        this.opened = false;
        this.position = new Position(1600,700);
        this.dimension = new Dimension(180,220);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        this.images.add(toolkit.getImage("resources/sprites/ui/Inventory.png"));
        this.inventory = inventory;
        this.tooltipGenerator = new TooltipGenerator();

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
                    tooltipGenerator.generateItemTooltip(inventorySlots[i].getItem()).render(graphics, inventorySlots[i].getPosition());
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
}
