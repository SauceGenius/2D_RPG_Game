package game;

import audio.AudioPlayer;
import core.Log;
import core.Size;
import display.CursorManager;
import display.Display;
import display.ui.InventoryUI;
import display.ui.LogBoxUI;
import display.ui.UI;
import display.ui.UIManager;
import entity.Player;
import entity.character.Character;
import entity.character.GameClassId;
import entity.character.RaceId;
import game.state.GameState;
import game.state.State;
import game.state.StateManager;
import gfx.SpriteLibrary;
import input.Input;
import input.InputObserver;
import settings.Settings;

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
        Log log = new Log();
        Input input = new Input();

        SpriteLibrary spriteLibrary = new SpriteLibrary();
        AudioPlayer audioPlayer = new AudioPlayer();

        Character character = new Character(1234, "SauceGenius", RaceId.human, GameClassId.warrior, input, audioPlayer, spriteLibrary, log);
        Player player = (Player) character.getGameObject();

        UIManager uiManager = new UIManager(spriteLibrary, audioPlayer, log, player);
        input.addInputObserver(uiManager);

        display = new Display(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT, input, player, log, uiManager);
        CursorManager cursorManager = new CursorManager(display);
        stateManager = new StateManager(input, character, player, audioPlayer, spriteLibrary, log, cursorManager, uiManager);

    }
}
