package ui.unitframes;

import gameobject.Player;
import ui.UI;

import java.awt.image.BufferedImage;

public abstract class UnitFrame extends UI {

    protected BufferedImage unitImage;
    protected Player player;

    public UnitFrame(BufferedImage unitImage, Player player){
        this.unitImage = unitImage;
        this.player = player;
    }
}
