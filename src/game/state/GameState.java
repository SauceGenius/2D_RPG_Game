package game.state;

import audio.AudioPlayer;
import controller.NPCController;
import core.Log;
import core.Position;
import core.Size;
import display.ui.UI;
import entity.NPC;
import entity.Player;
import entity.character.Character;
import game.Game;
import core.Time;
import gfx.SpriteLibrary;
import input.Input;
import map.GameMap;

import java.util.ArrayList;

public class GameState extends State{

    public GameState(Size windowSize, Input input, Character character, Player player, AudioPlayer audioPlayer, SpriteLibrary spriteLibrary, Log log, ArrayList<UI> uis) {
        super(windowSize, input, character, player, audioPlayer, spriteLibrary, log);
        input.setCamera(camera);
        gameMap = new GameMap(new Size(200,200),spriteLibrary);
        initializeCharacters();
        this.uis = uis;

        audioPlayer.playMusic("ElwynnForest.wav");
    }

    private void initializeCharacters() {
        player.setPosition(new Position(5 * Game.SPRITE_SIZE_TILE, 5 * Game.SPRITE_SIZE_TILE));
        gameObjects.add(player);
        camera.focusOn(player);

        initializeNPCs(5);
    }

    private void initializeNPCs(int numberOfNPCs) {
        for(int i = 0; i < numberOfNPCs; i++){
            NPC npc = new NPC(new NPCController(), audioPlayer, spriteLibrary, log);
            npc.setPosition(getGameMap().getRandomPosition());
            gameObjects.add(npc);
        }
    }

    @Override
    public void update() {
        statusUpdate();
        if(player.getTarget() != null && player.getTarget().hasBeenLooted())player.setTarget(null);
        respawn();
        super.update();
        character.update();
        for(UI ui: uis){
            ui.update(audioPlayer);
        }
    }

    @Override
    protected void handleMouseInput() {
        /*if(input.isMouseClicked()){
            System.out.println(String.format("Mouse clicked at position x:%d y:%d", (int)(input.getMousePosition().getX()+camera.getPosition ().intX()), (int)(input.getMousePosition().getY() + camera.getPosition().intY())));
        }*/

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
}
