package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.MapMaker;
import se.lolektivet.linus.linuswars.logic.RowMapMaker;
import se.lolektivet.linus.linuswars.logic.WarMap;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainTile;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;

/**
 * Created by Linus on 2014-09-21.
 */
public class TestMap1 extends RowMapMaker implements WarMap {

   @Override
   public void create(MapMaker mapMaker) {
      super.init(mapMaker, 4);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);

      nextRow();

      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);

      nextRow();

      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);

      nextRow();

      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);

      nextRow();


      addBuilding(TerrainType.HQ, Faction.BLUE_MOON, 0, 0);
      addBuilding(TerrainType.BASE, Faction.BLUE_MOON, 0, 1);

      addBuilding(TerrainType.HQ, Faction.ORANGE_STAR, 0, 3);
   }
}
