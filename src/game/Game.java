package game;

import audio.AudioPlayer;
import core.Log;
import display.CursorManager;
import display.MainFrame;
import id.ZoneId;
import login.AccountController;
import login.LogInPanel;
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
import world.World;

import java.util.ArrayList;

public class Game implements PlayerObserver {

    private World world;
    private MainFrame display;
    private StateManager stateManager;
    private UIController uiController;

    public Game() {
        this.world = new World();

        Input input = new Input();
        SpriteLibrary spriteLibrary = new SpriteLibrary();
        AudioPlayer audioPlayer = new AudioPlayer();

        LogInPanel logInPanel = new LogInPanel();
        AccountController accountController = new AccountController();

        Character character = new Character(1234, "SauceGenius", RaceId.human, GameClassId.Paladin, input, audioPlayer, spriteLibrary);
        Player player = (Player) character.getGameObject();
        Log log = character.getLog();

        uiController = new UIController(character, spriteLibrary, audioPlayer);
        input.addInputObserver(uiController);
        ArrayList<CButton> uiButtons = uiController.getButtons();
        for(CButton button: uiButtons){
            input.addInputObserver(button);
        }

        display = new MainFrame(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT, accountController, input, player, log, uiController);
        CursorManager cursorManager = new CursorManager(display);
        stateManager = new StateManager(input, character, audioPlayer, spriteLibrary, log, cursorManager, uiController);
    }

    public void update(){
        world.update();
        stateManager.update();
        uiController.update();
    }

    public void render(){
        display.render(stateManager.getCurrentState());
    }

    @Override
    public void notifyCharacterEnteringWorld(Character character) {
        world.characterEnteringWorld(character, ZoneId.ElwynnForest);
    }

    @Override
    public void notifyCharacterLeavingWorld(Character character) {
        world.characterLeavingWorld(character, ZoneId.ElwynnForest);
    }
}
