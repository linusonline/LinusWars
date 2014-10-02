package se.lolektivet.linus.linuswars.logic;

import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainTile;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;

/**
 * Created by Linus on 2014-09-21.
 */
public class Map1 {
   private MapMaker _mapMaker;

   public Map1(MapMaker mapMaker) {
      _mapMaker = mapMaker;
   }

   private void addTerrain(TerrainTile terrainTile, int x, int y) {
      _mapMaker.addTerrain(terrainTile, x, y);
   }

   private void addBuilding(TerrainType buildingType, Faction faction, int x, int y) {
      _mapMaker.addBuilding(buildingType, faction, x, y);
   }

   public void create() {
      addTerrain(TerrainTile.PLAIN, 0, 0);
      addTerrain(TerrainTile.PLAIN, 1, 0);
      addTerrain(TerrainTile.PLAIN, 2, 0);
      addTerrain(TerrainTile.PLAIN, 3, 0);
      addTerrain(TerrainTile.PLAIN, 4, 0);
      addTerrain(TerrainTile.PLAIN, 5, 0);
      addTerrain(TerrainTile.PLAIN, 6, 0);
      addTerrain(TerrainTile.PLAIN, 7, 0);
      addTerrain(TerrainTile.PLAIN, 8, 0);
      addTerrain(TerrainTile.PLAIN, 9, 0);
      addTerrain(TerrainTile.PLAIN, 10, 0);
      addTerrain(TerrainTile.PLAIN, 11, 0);
      addTerrain(TerrainTile.PLAIN, 12, 0);
      addTerrain(TerrainTile.PLAIN, 13, 0);
      addTerrain(TerrainTile.PLAIN, 14, 0);
      addTerrain(TerrainTile.PLAIN, 15, 0);

      addTerrain(TerrainTile.PLAIN, 0, 1);
      addTerrain(TerrainTile.PLAIN, 1, 1);
      addTerrain(TerrainTile.WOODS_SINGLE, 2, 1);
      addTerrain(TerrainTile.PLAIN, 3, 1);
      addTerrain(TerrainTile.PLAIN, 4, 1);
      addTerrain(TerrainTile.WOODS_SINGLE, 5, 1);
      addTerrain(TerrainTile.WOODS_SINGLE, 6, 1);
      addTerrain(TerrainTile.WOODS_SINGLE, 7, 1);
      addTerrain(TerrainTile.PLAIN, 8, 1);
      addTerrain(TerrainTile.PLAIN, 9, 1);
      addTerrain(TerrainTile.PLAIN, 10, 1);
      addTerrain(TerrainTile.WOODS_SINGLE, 11, 1);
      addTerrain(TerrainTile.PLAIN, 12, 1);
      addTerrain(TerrainTile.PLAIN, 13, 1);
      addTerrain(TerrainTile.WOODS_SINGLE, 14, 1);
      addTerrain(TerrainTile.PLAIN, 15, 1);

      addTerrain(TerrainTile.PLAIN, 0, 2);
      addTerrain(TerrainTile.WOODS_SINGLE, 1, 2);
      addTerrain(TerrainTile.WOODS_SINGLE, 2, 2);
      addTerrain(TerrainTile.PLAIN, 3, 2);
      addTerrain(TerrainTile.WOODS_SINGLE, 4, 2);
      addTerrain(TerrainTile.WOODS_SINGLE, 5, 2);
      addTerrain(TerrainTile.WOODS_SINGLE, 6, 2);
      addTerrain(TerrainTile.WOODS_SINGLE, 7, 2);
      addTerrain(TerrainTile.PLAIN, 8, 2);
      addTerrain(TerrainTile.PLAIN, 9, 2);
      addTerrain(TerrainTile.WOODS_SINGLE, 10, 2);
      addTerrain(TerrainTile.WOODS_SINGLE, 11, 2);
      addTerrain(TerrainTile.PLAIN, 12, 2);
      addTerrain(TerrainTile.WOODS_SINGLE, 13, 2);
      addTerrain(TerrainTile.PLAIN, 14, 2);
      addTerrain(TerrainTile.PLAIN, 15, 2);

      addTerrain(TerrainTile.PLAIN, 0, 3);
      addTerrain(TerrainTile.WOODS_SINGLE, 1, 3);
      addTerrain(TerrainTile.PLAIN, 2, 3);
      addTerrain(TerrainTile.PLAIN, 3, 3);
      addTerrain(TerrainTile.WOODS_SINGLE, 4, 3);
      addTerrain(TerrainTile.PLAIN, 5, 3);
      addTerrain(TerrainTile.WOODS_SINGLE, 6, 3);
      addTerrain(TerrainTile.WOODS_SINGLE, 7, 3);
      addTerrain(TerrainTile.PLAIN, 8, 3);
      addTerrain(TerrainTile.PLAIN, 9, 3);
      addTerrain(TerrainTile.PLAIN, 10, 3);
      addTerrain(TerrainTile.WOODS_SINGLE, 11, 3);
      addTerrain(TerrainTile.PLAIN, 12, 3);
      addTerrain(TerrainTile.PLAIN, 13, 3);
      addTerrain(TerrainTile.PLAIN, 14, 3);
      addTerrain(TerrainTile.PLAIN, 15, 3);

      addTerrain(TerrainTile.PLAIN, 0, 4);
      addTerrain(TerrainTile.PLAIN, 1, 4);
      addTerrain(TerrainTile.PLAIN, 2, 4);
      addTerrain(TerrainTile.PLAIN, 3, 4);
      addTerrain(TerrainTile.PLAIN, 4, 4);
      addTerrain(TerrainTile.PLAIN, 5, 4);
      addTerrain(TerrainTile.WOODS_SINGLE, 6, 4);
      addTerrain(TerrainTile.PLAIN, 7, 4);
      addTerrain(TerrainTile.PLAIN, 8, 4);
      addTerrain(TerrainTile.PLAIN, 9, 4);
      addTerrain(TerrainTile.PLAIN, 10, 4);
      addTerrain(TerrainTile.PLAIN, 11, 4);
      addTerrain(TerrainTile.PLAIN, 12, 4);
      addTerrain(TerrainTile.PLAIN, 13, 4);
      addTerrain(TerrainTile.PLAIN, 14, 4);
      addTerrain(TerrainTile.PLAIN, 15, 4);

      addTerrain(TerrainTile.PLAIN, 0, 5);
      addTerrain(TerrainTile.PLAIN, 1, 5);
      addTerrain(TerrainTile.MOUNTAIN_SMALL, 2, 5);
      addTerrain(TerrainTile.MOUNTAIN_SMALL, 3, 5);
      addTerrain(TerrainTile.PLAIN, 4, 5);
      addTerrain(TerrainTile.PLAIN, 5, 5);
      addTerrain(TerrainTile.PLAIN, 6, 5);
      addTerrain(TerrainTile.PLAIN, 7, 5);
      addTerrain(TerrainTile.PLAIN, 8, 5);
      addTerrain(TerrainTile.PLAIN, 9, 5);
      addTerrain(TerrainTile.PLAIN, 10, 5);
      addTerrain(TerrainTile.PLAIN, 11, 5);
      addTerrain(TerrainTile.MOUNTAIN_SMALL, 12, 5);
      addTerrain(TerrainTile.MOUNTAIN_SMALL, 13, 5);
      addTerrain(TerrainTile.MOUNTAIN_SMALL, 14, 5);
      addTerrain(TerrainTile.PLAIN, 15, 5);

      addBuilding(TerrainType.HQ, Faction.BLUE_MOON, 15, 5);
      addBuilding(TerrainType.HQ, Faction.ORANGE_STAR, 0, 1);
      addBuilding(TerrainType.BASE, Faction.BLUE_MOON, 0, 4);
   }
}
