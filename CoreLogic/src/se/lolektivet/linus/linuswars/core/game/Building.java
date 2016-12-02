package se.lolektivet.linus.linuswars.core.game;

import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;

/**
 * Created by Linus on 2016-11-29.
 */
public interface Building {
   Faction getFaction();
   TerrainType getBuildingType();
   Position getPosition();
   boolean isCapturing();
   int getCaptureStatus();
}
