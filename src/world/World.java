package world;

import character.Character;
import id.ZoneId;

import java.util.ArrayList;

public class World {

    private ArrayList<Zone> zones;

    public World(){
        this.zones = new ArrayList<>();
        initZones();
    }

    public void update(){
        zones.forEach(Zone::update);
    }

    public void characterEnteringWorld(Character character, ZoneId zoneId){
        zones.forEach(zone -> {
            if(zone.getZoneId() == zoneId){
                zone.characterEntersZone(character);
            }
        });
    }

    public void characterLeavingWorld(Character character, ZoneId zoneId){
        zones.forEach(zone -> {
            if(zone.getZoneId() == zoneId){
                zone.characterLeavesZone(character);
            }
        });
    }

    private void initZones(){
        ZoneGenerator zoneGenerator = new ZoneGenerator();
        zones.add(zoneGenerator.generate(ZoneId.ElwynnForest));
    }

}
