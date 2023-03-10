package ui.button;

import core.CollisionBox;
import core.Position;
import input.InputObserver;
import ui.Border;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CButton implements InputObserver {

    protected String text;
    protected Color textColor;
    protected Font textFont;
    protected Color backgroundColor;
    protected Position position;
    protected Dimension dimension;
    protected ArrayList<ButtonObserver> buttonObservers;
    protected boolean selected = false;
    protected Border border;

    public CButton(){
        text = "cButton";
        textColor = Color.BLACK;
        textFont = new Font("Arial Narrow", Font.BOLD, 20);
        backgroundColor = new Color(245,245,245);
        position = new Position(0,0);
        dimension = new Dimension(80,40);
        buttonObservers = new ArrayList<>();
        this.border = new Border();
    }

    public void render(Graphics graphics){



        graphics.setFont(textFont);
        graphics.setColor(backgroundColor);
        graphics.fillRect(position.intX(), position.intY(), dimension.width, dimension.height);
        graphics.setColor(textColor);
        graphics.drawString(text, position.intX() + 20, position.intY() + 20);

        border.render(graphics, position, dimension);

        if(selected){
            graphics.setColor(new Color(255,255,255,30));
            graphics.fillRoundRect(position.intX(), position.intY(), dimension.width, dimension.height, 8, 8);
        } else {
            //graphics.setColor(new Color(255,255,255,20));
            //graphics.drawRoundRect(position.intX(), position.intY(), dimension.width, dimension.height, 8, 8);
        }
    }

    public void addObserver(ButtonObserver buttonObserver){
        buttonObservers.add(buttonObserver);
    }

    public CollisionBox getCollisionBox() {
        return new CollisionBox(new Rectangle(position.intX(), position.intY(), dimension.width, dimension.width));
    }

    public void setText(String text){
        this.text = text;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public void setTextFont(Font textFont) {
        this.textFont = textFont;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setPosition(int x, int y){
        position.setX(x);
        position.setY(y);
    }

    public void setDimension(int width, int height){
        this.dimension = new Dimension(width,height);
    }

    public void setSelected(boolean bool){
        selected = bool;
    }

    @Override
    public void notifyKeyPressed(KeyEvent keyPressed) {

    }

    @Override
    public void notifyMouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void notifyMousePressed(MouseEvent mouseEvent) {
        if(getCollisionBox().collidesWith(new CollisionBox(new Rectangle(mouseEvent.getX(), mouseEvent.getY(), 10, 10)))){
            for(ButtonObserver buttonObserver: buttonObservers){
                buttonObserver.notifyButtonClicked(this);
            }
        }
    }

    @Override
    public void notifyMouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void notifyMouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void notifyMouseMoved(MouseEvent mouseEvent) {

    }
}
