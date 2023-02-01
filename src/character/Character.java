package character;

import inventory.Inventory;
import audio.AudioPlayer;
import controller.PlayerController;
import core.Log;
import equipment.Equipment;
import game.state.State;
import gameobject.GameObject;
import gameobject.Player;
import item.*;
import stats.Stats;
import gfx.SpriteLibrary;
import id.GameClassId;
import id.RaceId;
import input.Input;
import quest.QuestLog;

public class Character {

    private int characterId;
    private String userName;
    private RaceId raceId;
    private GameClassId gameClassId;
    private Stats stats;
    private PlayerController playerController;
    private GameObject gameObject;
    private Inventory inventory;
    private Equipment equipment;
    private QuestLog questLog;
    private Log log;
    //private Spells spells;
    //private Talents talents;

    //Constructor for new Character;
    public Character(int characterId, String userName, RaceId raceId, GameClassId gameClassId, Input input, AudioPlayer audioPlayer, SpriteLibrary spriteLibrary){
        this.characterId = characterId;
        this.userName = userName;
        this.raceId = raceId;
        this.gameClassId = gameClassId;
        this.stats = new Stats(raceId, gameClassId);
        this.playerController = new PlayerController(input);
        this.inventory = new Inventory();
        this.equipment = new Equipment();
        this.gameObject = new Player(userName, playerController, audioPlayer, stats, inventory, equipment, spriteLibrary, log);
        playerController.setCharacter(this);
        this.questLog = new QuestLog();
        this.log = new Log();

        /** For testing purposes **/
        Item item1 = new OneHandWeapon(ItemId.wornShortSword, spriteLibrary.getIcon("inv_sword_34"));
        Item item2 = new OneHandWeapon(ItemId.wornShortSword, spriteLibrary.getIcon("high_warlord_greatsword_34"));
        item2.setName("High Warlord's Greatsword");
        item2.setQuality(ItemSettings.EPIC_QUALITY);
        ((EquipableItem)item2).setItemLevel(60);
        ((EquipableItem)item2).setBinding(EquipableItem.BIND_ON_EQUIP);
        item2.getItemStat().setMinMeleeWeaponDamage(235);
        item2.getItemStat().setMaxMeleeWeaponDamage(353);
        ((Weapon)item2).getItemStat().setAttackSpeed(3.80);
        ((Weapon)item2).setDPS(77.37);
        item2.getItemStat().setStrength(26);
        item2.getItemStat().setStamina(41);
        ((EquipableItem)item2).setDurability(120);
        ((EquipableItem)item2).setMaxDurability(120);
        ((EquipableItem)item2).setLevelRequired(60);
        equipment.equip((EquipableItem) item1);
        inventory.addItem(item2);
        /** **/
    }

    public void update(State state){
        //gameObject.update(state); //already updates in GameState GameObjects ArrayList
        stats.getHp().update(stats, gameObject.getStatus());
        inventory.update();
        log.update();
    }

    // Getters
    public Log getLog() {return log;}
    public int getCharacterId() {return characterId;}
    public String getUserName() {return userName;}
    public RaceId getRaceId() {return raceId;}
    public GameClassId getGameClassId() {return gameClassId;}
    public Stats getStats() {return stats;}
    public PlayerController getPlayerController() {return playerController;}
    public GameObject getGameObject() {return gameObject;}
    public Inventory getInventory() {return inventory;}
    public Equipment getEquipment() {return equipment;}
    public QuestLog getQuestLog() {
        return questLog;
    }
}
