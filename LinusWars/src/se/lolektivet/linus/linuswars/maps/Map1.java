package se.lolektivet.linus.linuswars.maps;

import se.lolektivet.linus.linuswars.logic.InitializationException;
import se.lolektivet.linus.linuswars.logic.RowMapMaker;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainTile;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;

/**
 * Created by Linus on 2014-09-21.
 */
public class Map1 {
   private RowMapMaker _mapMaker;

   public Map1(RowMapMaker mapMaker) {
      _mapMaker = mapMaker;
   }

   private void addTerrain(TerrainTile terrainTile) {
      _mapMaker.addTerrain(terrainTile);
   }

   private void addBuilding(TerrainType buildingType, Faction faction, int x, int y) {
      _mapMaker.addBuilding(buildingType, faction, x, y);
   }

   private static final int NUM_ROWS = 6;
   private int rows = 0;

   private void nextRow() {
      rows++;
      _mapMaker.nextRow();
   }

   public void create() {
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);

      nextRow();

      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.WOODS_SINGLE);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.WOODS_SINGLE);
      addTerrain(TerrainTile.WOODS_SINGLE);
      addTerrain(TerrainTile.WOODS_SINGLE);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.WOODS_SINGLE);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.WOODS_SINGLE);
      addTerrain(TerrainTile.PLAIN);

      nextRow();

      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.WOODS_SINGLE);
      addTerrain(TerrainTile.WOODS_SINGLE);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.WOODS_SINGLE);
      addTerrain(TerrainTile.WOODS_SINGLE);
      addTerrain(TerrainTile.WOODS_SINGLE);
      addTerrain(TerrainTile.WOODS_SINGLE);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.WOODS_SINGLE);
      addTerrain(TerrainTile.WOODS_SINGLE);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.WOODS_SINGLE);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);

      nextRow();

      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.WOODS_SINGLE);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.WOODS_SINGLE);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.WOODS_SINGLE);
      addTerrain(TerrainTile.WOODS_SINGLE);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.WOODS_SINGLE);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);

      nextRow();

      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.WOODS_SINGLE);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);

      nextRow();

      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.MOUNTAIN_SMALL);
      addTerrain(TerrainTile.MOUNTAIN_SMALL);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.MOUNTAIN_SMALL);
      addTerrain(TerrainTile.MOUNTAIN_SMALL);
      addTerrain(TerrainTile.MOUNTAIN_SMALL);
      addTerrain(TerrainTile.PLAIN);

      nextRow();

      validate();

      addBuilding(TerrainType.HQ, Faction.ORANGE_STAR, 0, 1);
      addBuilding(TerrainType.HQ, Faction.BLUE_MOON, 14, 5);
      addBuilding(TerrainType.BASE, Faction.BLUE_MOON, 0, 4);
   }

   private void validate() {
      if (rows != NUM_ROWS) {
         throw new InitializationException("Map had wrong number of rows!");
      }
   }
}
