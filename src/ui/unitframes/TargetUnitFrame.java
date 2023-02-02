package ui.unitframes;

import audio.AudioPlayer;
import core.CollisionBox;
import core.Position;
import gameobject.GameObject;
import gameobject.LivingObject;
import gameobject.MovingEntity;
import gameobject.Player;
import gfx.SpriteLibrary;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TargetUnitFrame extends UnitFrame {

    private LivingObject target;

    public TargetUnitFrame(Player player) {
        super(player);
        this.position = new Position(290, 30);
        opened = false;
        target = player.getTarget();
    }

    @Override
    public void update() {
        if (player.getTarget() != null){
            target = player.getTarget();
            unitImage = (BufferedImage)target.getSprite();
        } else if(player.getTarget() == null){
            target = null;
        }

        if(target == null){
            opened = false;
        } else opened = true;
    }

    public void update(SpriteLibrary spriteLibrary){
        if (player.getTarget() != null){
            target = player.getTarget();
            String unitName = target.getClass().getSimpleName().toLowerCase();
            unitImage = (BufferedImage)spriteLibrary.getUnit(unitName).get("Idle");
        } else if(player.getTarget() == null){
            target = null;
        }
    }

    @Override
    public void render(Graphics graphics) {
        if(opened){
            int x = 140; int y = 30; int w = 150; int h = 10;
            Font unitFramesFont = new Font("TimesRoman", Font.BOLD, 11);
            graphics.setFont(unitFramesFont);

            if(player.getTarget() != null){
                graphics.setColor(new Color(70,70,70,150));
                graphics.fillRect(position.intX(), y,w,40);
                if(player.getTarget().getStatus().isAggressiveTowardTarget()){
                    graphics.setColor(Color.red);
                } else graphics.setColor(Color.yellow);
                FontMetrics metrics = graphics.getFontMetrics(unitFramesFont);
                int nameTextWidth = metrics.stringWidth(player.getTarget().getName());
                int nameXPosition = position.intX() + (int)(((double)w - (double)nameTextWidth) / 2);
                graphics.drawString(player.getTarget().getName(), nameXPosition, y + 14);
                graphics.setColor(Color.green);
                graphics.fillRect(position.intX(), y + 20,(int)(target.getStats().getCurrentHpValue()/target.getStats().getMaxHpValue() * w), h);
                graphics.setColor(Color.black);
                graphics.drawRect(position.intX(), y, w,40);
                graphics.drawRect(position.intX(), y + 20,w,10);
                if(!player.getTarget().isDead()) {
                    graphics.setColor(Color.black);
                    graphics.drawString(Integer.toString((int)target.getStats().getCurrentHpValue()).concat(" / ").concat(Integer.toString((int)target.getStats().getMaxHpValue())),position.intX() + 59,y + 29);
                    graphics.drawString(Integer.toString((int)target.getStats().getCurrentHpValue()).concat(" / ").concat(Integer.toString((int)target.getStats().getMaxHpValue())),position.intX() + 59,y + 31);
                    graphics.drawString(Integer.toString((int)target.getStats().getCurrentHpValue()).concat(" / ").concat(Integer.toString((int)target.getStats().getMaxHpValue())),position.intX() + 61,y + 29);
                    graphics.drawString(Integer.toString((int)target.getStats().getCurrentHpValue()).concat(" / ").concat(Integer.toString((int)target.getStats().getMaxHpValue())),position.intX() + 61,y + 31);
                    graphics.setColor(Color.white);
                    graphics.drawString(Integer.toString((int)target.getStats().getCurrentHpValue()).concat(" / ").concat(Integer.toString((int)target.getStats().getMaxHpValue())),position.intX() + 60,y + 30);
                } else {
                    graphics.setColor(Color.black);
                    graphics.drawString("Dead",position.intX() + 59,y + 29);
                    graphics.drawString("Dead",position.intX() + 59,y + 31);
                    graphics.drawString("Dead",position.intX() + 61,y + 29);
                    graphics.drawString("Dead",position.intX() + 61,y + 31);
                    graphics.setColor(Color.yellow);
                    graphics.drawString("Dead",position.intX() + 60,y + 30);
                }

                // Target Sprite //
                graphics.setColor(new Color(70,70,70, 150));
                graphics.fillArc(position.intX() + 151,y - 10,60,60,360,360);
                graphics.setColor(Color.black);
                graphics.drawArc(position.intX() + 151,y - 10,60,60,360,360);

                graphics.drawImage(unitImage.getSubimage(32,96,54,58).getScaledInstance(70,75,1),position.intX() + w + 13,position.intY() - 30,null);

                // Target Level //
                graphics.setColor(new Color(70,70,70, 230));
                graphics.fillArc(position.intX() + 191,y + 30,20,20,360,360);
                graphics.setColor(Color.black);
                graphics.drawArc(position.intX() + 191,y + 30,20,20,360,360);
                graphics.setColor(Color.yellow);
                graphics.drawString(Integer.toString(target.getLevel()), position.intX() + 199, y + 45);
            }
        }
    }

    @Override
    public void toggle(AudioPlayer audioPlayer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
