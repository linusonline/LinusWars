package se.lolektivet.linus.linuswars.core.game;

import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;

/**
 * Created by Linus on 2016-12-03.
 */
public interface LogicalWarMapSetup extends LogicalWarMap {
   void setTerrain(int x, int y, TerrainType terrainType);
   void setBuilding(int x, int y, TerrainType terrainType, Faction faction);
   void validate();
}
