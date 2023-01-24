package display.ui;

import audio.AudioPlayer;
import entity.MovingEntity;
import entity.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TargetUnitFrame extends UnitFrame{

    private MovingEntity target;

    public TargetUnitFrame(BufferedImage unitImage, Player player) {
        super(unitImage, player);
        enabled = false;
        target = player.getTarget();
    }

    @Override
    public void update() {
        if (player.getTarget() != null){
            target = player.getTarget();
            unitImage = (BufferedImage)target.getSprite();
        }

        if(target == null){
            enabled = false;
        } else enabled = true;
    }

    @Override
    public void render(Graphics graphics) {
        if(enabled){
            int x = 20; int y = 30; int w = 150; int h = 10;
            graphics.setFont(new Font("TimesRoman", Font.BOLD, 11));

            if(player.getTarget() != null){
                graphics.setColor(new Color(70,70,70,150));
                graphics.fillRect(x + w + 120,y,w,40);
                graphics.setColor(Color.yellow);
                graphics.drawString(player.getTarget().getName(), x + w + 180, y + 14);
                graphics.setColor(Color.green);
                graphics.fillRect(x + w + 120,y + 20,(int)(target.getStats().getCurrentHpValue()/target.getStats().getMaxHpValue() * w), h);
                graphics.setColor(Color.black);
                graphics.drawRect(x + w + 120,y,w,40);
                graphics.drawRect(x + w + 120,y + 20,w,10);
                if(!player.getTarget().isDead()) {
                    graphics.setColor(Color.black);
                    graphics.drawString(Integer.toString((int)target.getCurrentHpValue()).concat(" / ").concat(Integer.toString((int)target.getMaxHpValue())),x + w + 179,y + 29);
                    graphics.drawString(Integer.toString((int)target.getCurrentHpValue()).concat(" / ").concat(Integer.toString((int)target.getMaxHpValue())),x + w + 179,y + 31);
                    graphics.drawString(Integer.toString((int)target.getCurrentHpValue()).concat(" / ").concat(Integer.toString((int)target.getMaxHpValue())),x + w + 181,y + 29);
                    graphics.drawString(Integer.toString((int)target.getCurrentHpValue()).concat(" / ").concat(Integer.toString((int)target.getMaxHpValue())),x + w + 181,y + 31);
                    graphics.setColor(Color.white);
                    graphics.drawString(Integer.toString((int)target.getCurrentHpValue()).concat(" / ").concat(Integer.toString((int)target.getMaxHpValue())),x + w + 180,y + 30);
                } else {
                    graphics.setColor(Color.black);
                    graphics.drawString("Dead",x + w + 179,y + 29);
                    graphics.drawString("Dead",x + w + 179,y + 31);
                    graphics.drawString("Dead",x + w + 181,y + 29);
                    graphics.drawString("Dead",x + w + 181,y + 31);
                    graphics.setColor(Color.yellow);
                    graphics.drawString("Dead",x + w + 180,y + 30);
                }

                graphics.setColor(new Color(70,70,70, 150));
                graphics.fillArc(x + 421,y - 10,60,60,360,360);
                graphics.setColor(Color.black);
                graphics.drawArc(x + 421,y - 10,60,60,360,360);
                graphics.setColor(new Color(70,70,70, 230));
                graphics.fillArc(x + 461,y + 30,20,20,360,360);
                graphics.setColor(Color.black);
                graphics.drawArc(x + 461,y + 30,20,20,360,360);
                graphics.setColor(Color.yellow);
                graphics.drawString(Integer.toString(target.getLevel()), x + 469, y + 45);
            }
        }
    }

    @Override
    public void open(AudioPlayer audioPlayer) {

    }
}
