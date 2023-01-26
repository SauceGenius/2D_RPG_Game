package world;

import id.ZoneId;

public class ZoneGenerator {

    public Zone generate(ZoneId zoneId){
        switch (zoneId){
            case ElwynnForest: return createElwynnForest();
        }
        return null;
    }

    private Zone createElwynnForest() {
        Zone zone = new Zone(ZoneId.ElwynnForest);
        return zone;
    }
}
