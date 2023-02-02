package ui.unitframes;

import gameobject.Player;
import ui.UI;

import java.awt.image.BufferedImage;

public abstract class UnitFrame extends UI {

    protected BufferedImage unitImage;
    protected Player player;

    public UnitFrame(Player player){
        this.player = player;
    }
}
