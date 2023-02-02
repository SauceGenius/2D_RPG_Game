package game.state;

import audio.AudioLibrary;
import audio.AudioPlayer;
import character.Character;
import controller.NPCController;
import core.Log;
import core.Size;
import gameobject.LivingObject;
import gameobject.NPCGenerator;
import gameobject.mob.GoblinBerserker;
import gameobject.mob.GoblinSlinger;
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
    private NPCGenerator npcGenerator;

    public GameState(Input input, AudioPlayer audioPlayer, SpriteLibrary spriteLibrary, Log log, CursorManager cursorManager, Size windowSize) {
        super(input, audioPlayer, spriteLibrary, cursorManager);
        this.log = log;
        this.camera = new Camera(windowSize);
        this.characters = new ArrayList<>();
        this.npcGenerator = new NPCGenerator(spriteLibrary, audioPlayer, log);
        input.setCamera(camera);
        gameMap = new GameMap(new Size(200,200),spriteLibrary);
        initializeCharacters();
        audioPlayer.playMusic(AudioLibrary.ELWYNN_FOREST_THEME);
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
        //System.out.println("Mouse: " + input.getMousePositionRelativeToCamera().intX() + "," + input.getMousePositionRelativeToCamera().intY());

        /** always last **/
        input.clearMouse();
    }

    public void statusUpdate(){
        for(int i = 0; i < gameObjects.size(); i++){
            if(gameObjects.get(i) instanceof LivingObject){
                if(((LivingObject)gameObjects.get(i)).hasBeenLooted()){
                    gameObjects.remove(i);
                    respawnTimer.add(new Time());
                }
            }
        }
    }

    private void respawn() {
        for(int i = 0; i < respawnTimer.size(); i++){
            respawnTimer.get(i).startUpdateClock();
            if(respawnTimer.get(i).getUpdatesSinceStart() == time.getUpdatesFromSeconds(5)) {
                double dice = Math.random() * 2;
                if (dice > 1){
                    NPC goblinBerserker = npcGenerator.generateNPC(NPCGenerator.GOBLIN_BERSERKER, 2);
                    goblinBerserker.setPosition(getGameMap().getRandomPosition());
                    gameObjects.add(goblinBerserker);
                }
                else{
                    NPC goblinSlinger = npcGenerator.generateNPC(NPCGenerator.GOBLIN_SLINGER,1);
                    goblinSlinger.setPosition(getGameMap().getRandomPosition());
                    gameObjects.add(goblinSlinger); 
                }
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

        initializeNPCs(10);
    }

    private void initializeNPCs(int numberOfNPCs) {
        for(int i = 0; i < numberOfNPCs; i++){

            NPC goblinBerserker = npcGenerator.generateNPC(NPCGenerator.GOBLIN_BERSERKER, 2);
            goblinBerserker.setPosition(getGameMap().getRandomPosition());
            gameObjects.add(goblinBerserker);

            NPC goblinSlinger = npcGenerator.generateNPC(NPCGenerator.GOBLIN_SLINGER, 1);
            goblinSlinger.setPosition(getGameMap().getRandomPosition());
            gameObjects.add(goblinSlinger);
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
