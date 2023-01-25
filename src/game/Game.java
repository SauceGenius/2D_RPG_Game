package game;

import audio.AudioPlayer;
import core.Log;
import display.CursorManager;
import display.Display;
import ui.*;
import gameobject.Player;
import character.Character;
import id.GameClassId;
import id.RaceId;
import game.state.StateManager;
import gfx.SpriteLibrary;
import input.Input;
import settings.Settings;
import ui.button.CButton;

import java.util.ArrayList;

public class Game {

    private Display display;
    private StateManager stateManager;

    public Game() {
        initComponents();
    }

    public void update(){stateManager.update();}

    public void render(){
        display.render(stateManager.getCurrentState());
    }

    public void initComponents(){
        Input input = new Input();

        SpriteLibrary spriteLibrary = new SpriteLibrary();
        AudioPlayer audioPlayer = new AudioPlayer();

        Character character = new Character(1234, "SauceGenius", RaceId.human, GameClassId.Paladin, input, audioPlayer, spriteLibrary);
        Player player = (Player) character.getGameObject();
        Log log = character.getLog();

        UIController uiController = new UIController(character, spriteLibrary, audioPlayer);
        input.addInputObserver(uiController);
        ArrayList<CButton> uiButtons = uiController.getButtons();
        for(CButton button: uiButtons){
            input.addInputObserver(button);
        }

        display = new Display(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT, input, player, log, uiController);
        CursorManager cursorManager = new CursorManager(display);
        stateManager = new StateManager(input, character, player, audioPlayer, spriteLibrary, log, cursorManager, uiController);

    }
}
