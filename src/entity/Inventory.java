package entity;

import audio.AudioPlayer;
import controller.PlayerController;
import core.Log;
import entity.item.Item;
import entity.item.ItemId;
import entity.item.OneHandWeapon;
import gfx.SpriteLibrary;

public class Inventory {

    private boolean isOpen = false;
    private int space = 16;
    private PlayerController playerController;
    private SpriteLibrary spriteLibrary;
    Item items[] = new Item[space];

    public Inventory(PlayerController playerController, SpriteLibrary spriteLibrary) {
        this.playerController = playerController;
        this.spriteLibrary = spriteLibrary;
    }

    public void update(AudioPlayer audioPlayer) {
        /*if (!isOpen && playerController.isInventoryOpen()) {
                isOpen = true;
                audioPlayer.playSound("OpenBag.wav");
            } else if (playerController.isInventoryOpen()) {
                isOpen = true;
            } else isOpen = false;*/


    }

    public void render(){
        //ui.render();
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

    public boolean isOpen() {
        return isOpen;
    }
}




