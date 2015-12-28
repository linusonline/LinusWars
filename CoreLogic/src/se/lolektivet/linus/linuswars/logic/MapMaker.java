package se.lolektivet.linus.linuswars.logic;

import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainTile;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;

/**
 * Created by Linus on 2014-09-24.
 */
public interface MapMaker {
   void addTerrain(TerrainTile terrainTile, int x, int y);
   void addTerrain(TerrainType terrainType, int x, int y);

   void addBuilding(TerrainType buildingType, Faction faction, int x, int y);

   void finish();
}
