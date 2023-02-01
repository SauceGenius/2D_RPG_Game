package ui;

import audio.AudioPlayer;
import character.Character;
import core.CollisionBox;
import core.Position;

import java.awt.*;

public class ExpBarUI extends UI {

    private Character character;

    public ExpBarUI(Character character){
        this.position = new Position(450, 1023);
        this.dimension = new Dimension(1020, 10);
        this.character = character;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics graphics) {

        /** Draw background color **/
        graphics.setColor(new Color(20,20,40,150));
        graphics.fillRect(position.intX(), position.intY(), dimension.width, dimension.height);

        /** Draw Exp gained **/
        graphics.setColor(new Color(90,0,140));
        graphics.fillRect(position.intX(), position.intY(), (int)((double) character.getStats().getLevel().getExp()/(double) character.getStats().getLevel().getExpToNextLevel() * dimension.width), dimension.height);

        graphics.setColor(Color.gray);
        graphics.drawRect(position.intX(), position.intY(), dimension.width, dimension.height);

        graphics.setColor(new Color(20,20,40,175));
        graphics.drawRect(position.intX() - 1, position.intY() - 1, dimension.width + 2, dimension.height + 2);


        for (int i = 0; i < 20; i++){
            graphics.setColor(Color.lightGray);
            graphics.drawRoundRect(position.intX() + i * 51, position.intY(), 51, dimension.height, 8,8);
        }

        /** Draw Exp Text **/
        String expText = Integer.toString(character.getStats().getLevel().getExp()).concat(" / ").concat(Integer.toString(character.getStats().getLevel().getExpToNextLevel()));
        graphics.setFont(new Font("TimesRoman", Font.BOLD,15));

        graphics.setColor(Color.BLACK);
        graphics.drawString(expText, position.intX() + dimension.width/2 - 15, position.intY() + 11);
        graphics.setColor(Color.lightGray);
        graphics.drawString(expText, position.intX() + dimension.width/2 - 14, position.intY() + 12);
    }

    @Override
    public void toggle(AudioPlayer audioPlayer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
