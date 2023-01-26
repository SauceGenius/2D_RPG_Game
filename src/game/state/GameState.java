package game.state;

import audio.AudioPlayer;
import controller.NPCController;
import core.Log;
import core.Position;
import core.Size;
import display.CursorManager;
import ui.UI;
import ui.UIController;
import gameobject.NPC;
import gameobject.Player;
import character.Character;
import core.Time;
import gfx.SpriteLibrary;
import input.Input;
import map.GameMap;
import settings.Settings;
import world.World;

import java.util.stream.Collectors;

public class GameState extends State {

    public GameState(Size windowSize, Input input, Character character, AudioPlayer audioPlayer, SpriteLibrary spriteLibrary, Log log, CursorManager cursorManager, UIController uiManager) {
        super(windowSize, input, character, audioPlayer, spriteLibrary, log, cursorManager);
        input.setCamera(camera);
        gameMap = new GameMap(new Size(200,200),spriteLibrary);
        initializeCharacters();
        audioPlayer.playMusic("ElwynnForest.wav");
        this.uiManager = uiManager;
    }

    @Override
    public void update() {
        statusUpdate();
        respawn();
        super.update();

        for(UI ui: uiManager.getUIList()){ ui.update();}
        cursorManager.update(gameObjects.stream().filter(other -> character.getGameObject().mouseCollidesWith(other)).collect(Collectors.toList()));
        ((Player)character.getGameObject()).handleClickOnGameObject(gameObjects.stream().filter(other -> character.getGameObject().mouseCollidesWith(other)).collect(Collectors.toList()));
        handleMouseInput();

    }

    @Override
    protected void handleMouseInput() {
        //always last
        input.clearMouseClicked();
    }

    public void statusUpdate(){
        for(int i = 0; i < gameObjects.size(); i++){
            if(gameObjects.get(i).hasBeenLooted()){
                gameObjects.remove(i);
                respawnTimer.add(new Time());
            }
        }
    }

    private void respawn() {
        for(int i = 0; i < respawnTimer.size(); i++){
            respawnTimer.get(i).startUpdateClock();
            if(respawnTimer.get(i).getUpdatesSinceStart() == time.getUpdatesFromSeconds(5)) {
                NPC npc = new NPC(new NPCController(), audioPlayer, spriteLibrary, log);
                npc.setPosition(getGameMap().getRandomPosition());
                gameObjects.add(npc);
            }
        }
    }

    private void initializeCharacters() {
        character.getGameObject().setPosition(new Position(5 * Settings.SPRITE_SIZE_TILE, 5 * Settings.SPRITE_SIZE_TILE));
        gameObjects.add(character.getGameObject());
        characters.add(character);
        camera.focusOn(character.getGameObject());

        initializeNPCs(5);
    }

    private void initializeNPCs(int numberOfNPCs) {
        for(int i = 0; i < numberOfNPCs; i++){
            NPC npc = new NPC(new NPCController(), audioPlayer, spriteLibrary, log);
            npc.setPosition(getGameMap().getRandomPosition());
            gameObjects.add(npc);
        }
    }
}
