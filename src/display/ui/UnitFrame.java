package display.ui;

import entity.Player;

import java.awt.image.BufferedImage;

public abstract class UnitFrame extends UI {

    protected BufferedImage unitImage;
    protected Player player;

    public UnitFrame(BufferedImage unitImage, Player player){
        this.unitImage = unitImage;
        this.player = player;
    }
}
