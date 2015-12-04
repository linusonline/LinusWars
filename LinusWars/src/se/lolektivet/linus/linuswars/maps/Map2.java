package se.lolektivet.linus.linuswars.maps;

import se.lolektivet.linus.linuswars.logic.InitializationException;
import se.lolektivet.linus.linuswars.logic.MapMaker;
import se.lolektivet.linus.linuswars.logic.RowMapMaker;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainTile;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;

/**
 * Created by Linus on 2014-09-21.
 */
public class Map2 extends RowMapMaker {
   public Map2(MapMaker mapMaker) {
      super(mapMaker, 12);
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
      addTerrain(TerrainTile.PLAIN);
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
      addTerrain(TerrainTile.PLAIN);
      addTerrain(TerrainTile.PLAIN);

      nextRow();

      validate();

      addBuilding(TerrainType.HQ, Faction.ORANGE_STAR, 0, 1);
      addBuilding(TerrainType.HQ, Faction.BLUE_MOON, 14, 5);
      addBuilding(TerrainType.BASE, Faction.BLUE_MOON, 0, 4);
   }
}
