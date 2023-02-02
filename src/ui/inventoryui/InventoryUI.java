package ui.inventoryui;

import audio.AudioLibrary;
import audio.AudioPlayer;
import core.CollisionBox;
import core.Position;
import input.Input;
import inventory.Inventory;
import ui.UI;
import ui.slot.InventorySlot;
import ui.tooltip.TooltipGenerator;

import java.awt.*;

public class InventoryUI extends UI {

    private static final int INVENTORY_SIZE = 16;
    private InventorySlot[] inventorySlots;
    private Inventory inventory;
    private TooltipGenerator tooltipGenerator;
    private CollisionBox exitButton;
    private boolean mouseOverExitButton = false;

    public InventoryUI(Inventory inventory) {
        this.opened = false;
        this.position = new Position(1600,700);
        this.dimension = new Dimension(190,220);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        this.images.add(toolkit.getImage("resources/sprites/ui/Inventory.png"));
        this.inventory = inventory;
        this.tooltipGenerator = new TooltipGenerator();
        this.inventorySlots = new InventorySlot[INVENTORY_SIZE];
        this.exitButton = new CollisionBox(new Rectangle(position.intX() + dimension.width - 16, position.intY() + 7, 20,20));
        for(int i = 0; i < INVENTORY_SIZE; i++) inventorySlots[i] = new InventorySlot(i, position);
    }


    @Override
    public void update() {}

    public void update(Input input) {
        super.update(input);

        for(int i = 0; i < INVENTORY_SIZE; i++){
            inventorySlots[i].update(input, inventory);
        }

        if(exitButton.collidesWith(new CollisionBox(new Rectangle(input.getMousePositionRelativeToScreen().intX() - 2, input.getMousePositionRelativeToScreen().intY() - 2, 4, 4)))){
            mouseOverExitButton = true;
        } else mouseOverExitButton = false;

        /**
        if(opened){
            if(getCollisionBox().collidesWith(new CollisionBox(new Rectangle(
                    input.getMousePositionRelativeToScreen().intX() - 2,
                    input.getMousePositionRelativeToScreen().intY() - 2, 4, 4)))) {

                mouseOverUI = true;

                for(int i = 0; i < inventorySlots.length; i++) {
                    if(inventorySlots[i].getCollisionBox().collidesWith(new CollisionBox(new Rectangle(
                            input.getMousePositionRelativeToScreen().intX() - 2,
                            input.getMousePositionRelativeToScreen().intY() - 2, 4, 4)))) {

                        inventorySlots[i].setMouseOver(true);

                        if(inventorySlots[i].getItem() != null) {
                            System.out.println("Mouse over: " + inventorySlots[i].getItem().getName());
                        }
                    } else inventorySlots[i].setMouseOver(false);
                }
            } //else mouseOverUI = false;
        }
         **/
    }

    @Override
    public void render(Graphics graphics){
        if(opened){
            /** Background Inventory Image **/
            graphics.drawImage(images.get(0), position.intX(), position.intY(), null);

            /** Each Inventory Slot and Item **/
            for(int i = 0; i < INVENTORY_SIZE; i++){
                inventorySlots[i].render(graphics);
            }

            /** LightGray Mask on Exit Button when mouse is over **/
            if(mouseOverExitButton){
                graphics.setColor(new Color(255,255,255,30));
                graphics.fillRect(exitButton.getBounds().x, exitButton.getBounds().y, exitButton.getBounds().width, exitButton.getBounds().height);
            }

            /** Tooltip **/
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
            audioPlayer.playSound(AudioLibrary.INVENTORY_OPENED_SOUND_EFFECT);
        } else {
            opened = false;
            audioPlayer.playSound(AudioLibrary.INVENTORY_CLOSED_SOUND_EFFECT);
            draggingItemOver = false;
            mouseOverUI = false;
            for (int i = 0; i < inventorySlots.length; i++){
                inventorySlots[i].setDraggedOver(false);
            }
        }
    }

    @Override
    public CollisionBox getCollisionBox() {
        return new CollisionBox(new Rectangle(position.intX() + 6, position.intY(), dimension.width + 4, dimension.height + 10));
    }

    /** Getters **/
    public InventorySlot[] getInventorySlots() {
        return inventorySlots;
    }

    public boolean isMouseOverExitButton() {
        return mouseOverExitButton;
    }
}
