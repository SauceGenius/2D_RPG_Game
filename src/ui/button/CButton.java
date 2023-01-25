package ui.button;

import core.CollisionBox;
import core.Position;
import input.InputObserver;

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

    public CButton(){
        text = "cButton";
        textColor = Color.BLACK;
        textFont = new Font("Arial Narrow", Font.BOLD, 20);
        backgroundColor = new Color(245,245,245);
        position = new Position(0,0);
        dimension = new Dimension(80,40);
        buttonObservers = new ArrayList<>();
    }

    public void render(Graphics graphics){
        graphics.setFont(textFont);
        if(selected){
            graphics.setColor(Color.BLACK);
            graphics.drawRect(position.intX(), position.intY(), dimension.width, dimension.height);
        }
        graphics.setColor(backgroundColor);
        graphics.fillRect(position.intX(), position.intY(), dimension.width, dimension.height);
        graphics.setColor(textColor);
        graphics.drawString(text, position.intX() + 20, position.intY() + 20);
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
    public void notifyMouseClicked(MouseEvent mouseClicked) {

    }

    @Override
    public void notifyMousePressed(MouseEvent mousePressed) {
        if(getCollisionBox().collidesWith(new CollisionBox(new Rectangle(mousePressed.getX(), mousePressed.getY(), 10, 10)))){
            for(ButtonObserver buttonObserver: buttonObservers){
                buttonObserver.notifyButtonClicked(this);
            }
        }
    }

    @Override
    public void notifyMouseDragged(MouseEvent mouseDragged) {

    }

    @Override
    public void notifyMouseMoved(MouseEvent mouseEvent) {

    }
}
