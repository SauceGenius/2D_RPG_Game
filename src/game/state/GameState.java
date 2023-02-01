package game.state;

import audio.AudioPlayer;
import character.Character;
import controller.NPCController;
import core.Log;
import core.Size;
import mainFrame.Camera;
import mainFrame.cursormanager.CursorManager;
import mainFrame.cursormanager.CursorManagerGameState;
import ui.UIController;
import gameobject.NPC;
import gameobject.Player;
import core.Time;
import gfx.SpriteLibrary;
import input.Input;
import map.GameMap;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GameState extends State {

    private Log log;
    private Camera camera;
    private UIController uiController;
    private ArrayList<Character> characters;

    public GameState(Input input, AudioPlayer audioPlayer, SpriteLibrary spriteLibrary, Log log, CursorManager cursorManager, Size windowSize) {
        super(input, audioPlayer, spriteLibrary, cursorManager);
        this.log = log;
        this.camera = new Camera(windowSize);
        this.characters = new ArrayList<>();
        input.setCamera(camera);
        gameMap = new GameMap(new Size(200,200),spriteLibrary);
        initializeCharacters();
        audioPlayer.playMusic("ElwynnForest.wav");
    }

    @Override
    public void update() {
        statusUpdate();
        respawn();
        super.update();

        camera.update(this);
        uiController.update();
        uiController.update(input);

        if(characters.size() > 0){
            ((CursorManagerGameState)cursorManager).update(gameObjects.stream().filter(other -> characters.get(0).getGameObject().mouseCollidesWith(other)).collect(Collectors.toList()));
            ((Player)characters.get(0).getGameObject()).handleClickOnGameObject(gameObjects.stream().filter(other -> characters.get(0).getGameObject().mouseCollidesWith(other)).collect(Collectors.toList()));
            characters.forEach(character -> character.update(this));
        }

        log.update();

        handleMouseInput();
    }

    @Override
    protected void handleMouseInput() {

        /** always last **/
        input.clearMouse();
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
        /**if(characters.size() > 0) {
            characters.get(0).getGameObject().setPosition(new Position(5 * Settings.SPRITE_SIZE_TILE, 5 * Settings.SPRITE_SIZE_TILE));
            gameObjects.add(characters.get(0).getGameObject());
            characters.add(character);
            camera.focusOn(character.getGameObject());
        }**/

        initializeNPCs(20);
    }

    private void initializeNPCs(int numberOfNPCs) {
        for(int i = 0; i < numberOfNPCs; i++){
            NPC npc = new NPC(new NPCController(), audioPlayer, spriteLibrary, log);
            npc.setPosition(getGameMap().getRandomPosition());
            gameObjects.add(npc);
        }
    }

    public void playerEntersGame(Character character){
        this.uiController = new UIController(character, spriteLibrary, audioPlayer);
        this.uiController.addObserver(character.getPlayerController());
        addCharacter(character);
    }

    public void addCharacter(Character character){
        characters.add(character);
        gameObjects.add(character.getGameObject());
    }

    public Camera getCamera() {
        return camera;
    }

    public ArrayList<Character> getCharacters(){
        return characters;
    }

    public UIController getUiController(){
        return uiController;
    }
}
