package se.lolektivet.linus.linuswars.logic;

/**
 * Created by Linus on 2014-09-24.
 */
public interface MapMaker {
   void addTerrain(TerrainTile terrainTile, int x, int y);

   void addBuilding(TerrainType buildingType, Faction faction, int x, int y);
}
