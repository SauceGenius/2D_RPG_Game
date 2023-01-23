package game;

import audio.AudioPlayer;
import core.Log;
import core.Size;
import display.Display;
import display.ui.InventoryUI;
import display.ui.LogBoxUI;
import display.ui.UI;
import entity.Player;
import entity.character.Character;
import entity.character.GameClassId;
import entity.character.RaceId;
import game.state.GameState;
import game.state.State;
import gfx.SpriteLibrary;
import input.Input;
import settings.Settings;

import java.util.ArrayList;

public class Game {

    public static int SPRITE_SIZE_PLAYER = 96;
    public static int SPRITE_SIZE_TILE = 32;
    private static Settings settings;
    private Display display;
    private State state;

    public Game() {
        settings = new Settings();
        Log log = new Log();
        Input input = new Input();
        SpriteLibrary spriteLibrary = new SpriteLibrary();
        AudioPlayer audioPlayer = new AudioPlayer();
        Character character = new Character(1234, "SauceGenius", RaceId.human, GameClassId.warrior, input, audioPlayer, spriteLibrary, log);
        Player player = (Player) character.getGameObject();
        ArrayList<UI> uis = new ArrayList<UI>();
        uis.add(new LogBoxUI());
        uis.add(new InventoryUI(player.getInventory(), player.getPlayerController()));
        display = new Display(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT, input, player, log, uis);
        state = new GameState(new Size(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT), input, character, player, audioPlayer, spriteLibrary, log, uis);
    }

    public void update(){
        state.update();
    }

    public void render(){
        display.render(state);
    }
}
