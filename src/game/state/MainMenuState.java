package game.state;

import audio.AudioPlayer;
import character.Character;
import mainFrame.cursormanager.CursorManager;
import gfx.SpriteLibrary;
import input.Input;
import input.InputObserver;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MainMenuState extends State implements InputObserver {
    public MainMenuState(Input input, AudioPlayer audioPlayer, SpriteLibrary spriteLibrary, CursorManager cursorManager) {
        super(input, audioPlayer, spriteLibrary, cursorManager);
    }

    public void notifyCharacterEntersGame(Character character){
        observers.forEach(observer -> observer.notifyCharacterEntersGame(character));
    }

    @Override
    protected void handleMouseInput() {

    }

    @Override
    public void notifyMouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void notifyMousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void notifyMouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void notifyMouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void notifyMouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void notifyKeyPressed(KeyEvent keyPressed) {

    }
}
