package display.ui;

import audio.AudioPlayer;
import controller.PlayerController;
import core.Position;
import entity.Inventory;

import java.awt.*;

public class InventoryUI extends UI {

    private Inventory inventory;
    private PlayerController playerController;
    public InventoryUI(Inventory inventory, PlayerController playerController) {
        enabled = false;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image bagImage = toolkit.getImage("resources/sprites/ui/Bag.png");
        images.add(bagImage);
        position = new Position(1600,700);
        this.inventory = inventory;
        this.playerController = playerController;
    }

    public void update(AudioPlayer audioPlayer) {
        if (!enabled && playerController.isInventoryOpen()) {
            enabled = true;
            audioPlayer.playSound("OpenBag.wav");
        } else if (playerController.isInventoryOpen()) {
            enabled = true;
        } else enabled = false;
    }

    public void render(Graphics graphics){
        if(enabled){
            graphics.drawImage(images.get(0), position.intX(), position.intY(), null);

            // Each space (items)
            int iconWidth = 36;
            int iconHeight = 36;
            int itemPosX = position.intX() + 20;
            int itemPosY = position.intY() + 56;
            int marginX = 8;
            int marginY = 8;

            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {

                    /*
                    graphics.setColor(new Color(70, 70, 70));
                    graphics.fillRect(x * (iconWidth + marginX) + itemPosX, y * (iconHeight + marginY) + itemPosY, iconWidth, iconHeight);
                    graphics.setColor(new Color(0, 0, 0));
                    graphics.drawRect(x * (iconWidth + marginX) + itemPosX, y * (iconHeight + marginY) + itemPosY, iconWidth, iconHeight);
                    */

                    // Render item icons
                    if (inventory.getItems()[(x + y * 4)] != null) {
                        graphics.drawImage(inventory.getItems()[(x + y * 4)].getIconSprite(), x * (iconWidth + marginX) + itemPosX, y * (iconHeight + marginY) + itemPosY, null);
                    }
                }
            }
        }
    }
}
