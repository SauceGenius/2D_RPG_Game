package game;

import audio.AudioPlayer;
import core.Log;
import core.Size;
import display.Display;
import entity.Player;
import entity.character.Character;
import entity.character.GameClassId;
import entity.character.RaceId;
import game.state.GameState;
import game.state.State;
import gfx.SpriteLibrary;
import input.Input;
import settings.Settings;

import java.io.IOException;

public class Game {

    public static int SPRITE_SIZE_PLAYER = 96;
    public static int SPRITE_SIZE_TILE = 32;
    private static Settings settings;
    private static Log log;
    private Display display;
    private SpriteLibrary spriteLibrary;
    private Input input;
    private State state;
    private Character character;
    private Player player;
    private AudioPlayer audioPlayer;


    public Game() {
        settings = new Settings();
        log = new Log();
        input = new Input();
        audioPlayer = new AudioPlayer();
        spriteLibrary = new SpriteLibrary();
        character = new Character(1234, "SauceGenius", RaceId.human, GameClassId.warrior, input, audioPlayer, spriteLibrary, log);
        player = (Player) character.getGameObject();
        display = new Display(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT, input, player, log);
        state = new GameState(new Size(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT), input, character, player, audioPlayer, spriteLibrary, log);
    }

    public void update(){
        state.update();
    }

    public void render(){
        display.render(state);
    }
}
