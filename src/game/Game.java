package game;

import audio.AudioPlayer;
import core.Log;
import core.Size;
import mainFrame.cursormanager.CursorManager;
import mainFrame.MainFrame;
import game.state.GameState;
import game.state.MainMenuState;
import game.state.State;
import game.state.StateObserver;
import mainFrame.cursormanager.CursorManagerGameState;
import character.Character;
import id.GameClassId;
import id.RaceId;
import gfx.SpriteLibrary;
import input.Input;
import settings.Settings;
import ui.button.CButton;

import java.util.ArrayList;

public class Game implements StateObserver {

    private Input input;
    private SpriteLibrary spriteLibrary;
    private AudioPlayer audioPlayer;
    private MainFrame MainFrame;
    private State currentState;


    public Game() {
        this.input = new Input();
        this.spriteLibrary = new SpriteLibrary();
        this.audioPlayer = new AudioPlayer();

        Character character = new Character(1234, "SauceGenius", RaceId.human, GameClassId.Paladin, input, audioPlayer, spriteLibrary);

        Log log = character.getLog();

        MainFrame = new MainFrame(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT, input, log);

        //openMainMenu();

        changeToGameState(character);
    }

    public void update(){
        currentState.update();
        MainFrame.update(currentState);
    }

    public void render(){
        MainFrame.render(currentState);
    }

    private void openMainMenu(){
        currentState = new MainMenuState(input, audioPlayer, spriteLibrary, new CursorManager(MainFrame));
    }

    public void changeToGameState(Character character){
        //Character character = new Character(1234, "SauceGenius", RaceId.human, GameClassId.Paladin, input, audioPlayer, spriteLibrary);
        currentState = new GameState(input, audioPlayer, spriteLibrary, character.getLog(), new CursorManagerGameState(MainFrame, character), new Size(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT));
        ((GameState)currentState).getCamera().focusOn(character.getGameObject());
        ((GameState)currentState).playerEntersGame(character);

        input.addInputObserver(((GameState) currentState).getUiController());
        currentState.addObserver(this);


        ArrayList<CButton> uiButtons = ((GameState) currentState).getUiController().getButtons();
        for(CButton button: uiButtons){
            input.addInputObserver(button);
        }
    }

    @Override
    public void notifyCharacterEntersGame(Character character) {
        changeToGameState(character);
    }
}
