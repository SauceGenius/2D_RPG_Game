package ui.unitframes;

import audio.AudioPlayer;
import gameobject.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerUnitFrame extends UnitFrame {

    public PlayerUnitFrame(BufferedImage unitImage, Player player){
        super(unitImage, player);
        opened = true;
    }

    @Override
    public void update() {}

    @Override
    public void render(Graphics graphics) {
        int x = 20; int y = 30; int w = 150; int h = 10;
        graphics.setFont(new Font("TimesRoman", Font.BOLD, 11));

        //Player
        graphics.setColor(new Color(70,70,70, 150));
        graphics.fillArc(x,y - 10,60,60,360,360);
        graphics.setColor(Color.black);
        graphics.drawArc(x,y - 10,60,60,360,360);
        graphics.setColor(new Color(70,70,70,150));
        graphics.fillRect(x + 60,y,w,40);
        graphics.setColor(Color.yellow);
        graphics.drawString(player.getName(), x + 100, y + 14);
        graphics.setColor(Color.green);
        graphics.fillRect(x + 60,y + 20,(int)(player.getStats().getCurrentHpValue()/player.getStats().getMaxHpValue() * w), h);
        graphics.setColor(Color.black);
        graphics.drawRect(x + 60,y,w,40);
        graphics.drawRect(x + 60,y + 20,w,10);
        graphics.setColor(Color.black);
        graphics.drawString(Integer.toString((int)player.getCurrentHpValue()).concat(" / ").concat(Integer.toString((int)player.getMaxHpValue())),60 + 79,y + 29);
        graphics.drawString(Integer.toString((int)player.getCurrentHpValue()).concat(" / ").concat(Integer.toString((int)player.getMaxHpValue())),60 + 81,y + 29);
        graphics.drawString(Integer.toString((int)player.getCurrentHpValue()).concat(" / ").concat(Integer.toString((int)player.getMaxHpValue())),60 + 79,y + 31);
        graphics.drawString(Integer.toString((int)player.getCurrentHpValue()).concat(" / ").concat(Integer.toString((int)player.getMaxHpValue())),60 + 81,y + 31);
        graphics.setColor(Color.white);
        graphics.drawString(Integer.toString((int)player.getCurrentHpValue()).concat(" / ").concat(Integer.toString((int)player.getMaxHpValue())),60 + 80,y + 30);

        graphics.drawImage(unitImage.getSubimage(15,298,96,48).getScaledInstance(127,64,1),x - 10,y - 14,null);

        graphics.setColor(new Color(70,70,70, 230));
        graphics.fillArc(x,y + 30,20,20,360,360);
        graphics.setColor(Color.black);
        graphics.drawArc(x,y + 30,20,20,360,360);
        if(player.getStatus().isInCombat()){
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            graphics.drawImage(toolkit.getImage("resources/sprites/ui/InCombat.png"), x + 2,y + 33, null );
        } else {
            graphics.setColor(Color.yellow);
            graphics.drawString(Integer.toString(player.getLevel()), x + 8, y + 45);;
        }
    }

    @Override
    public void toggle(AudioPlayer audioPlayer) {}
}
