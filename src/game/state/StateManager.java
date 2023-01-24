package game.state;

import audio.AudioPlayer;
import core.Log;
import core.Size;
import display.CursorManager;
import display.ui.UIManager;
import entity.Player;
import entity.character.Character;
import gfx.SpriteLibrary;
import input.Input;
import settings.Settings;

public class StateManager {

    private State currentState;
    private GameState gameState;

    public StateManager(Input input, Character character, Player player, AudioPlayer audioPlayer, SpriteLibrary spriteLibrary,Log log, CursorManager cursorManager, UIManager uiManager){
        initStates(input, character, player, audioPlayer, spriteLibrary,log, cursorManager, uiManager);
    }

    public void update(){
        currentState.update();
    }

    public void initStates(Input input, Character character, Player player, AudioPlayer audioPlayer, SpriteLibrary spriteLibrary, Log log, CursorManager cursorManager, UIManager uiManager){
        gameState = new GameState(new Size(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT), input, character, player, audioPlayer, spriteLibrary, log, cursorManager, uiManager);
        changeToGameState();
    }

    public void changeToGameState(){
        currentState = gameState;
    }

    public State getCurrentState() {return currentState;}
}