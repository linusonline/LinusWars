package se.lolektivet.linus.linuswars.core.maps;

import se.lolektivet.linus.linuswars.core.MapMaker;
import se.lolektivet.linus.linuswars.core.RowMapMaker;
import se.lolektivet.linus.linuswars.core.WarMapAdapter;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.TerrainTile;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;

import java.util.List;

/**
 * Created by Linus on 2014-09-21.
 */
public class Map1 extends WarMapAdapter {

   @Override
   public int getNrOfFactions() {
      return 2;
   }

   public void create(MapMaker mapMaker, List<Faction> factions) {
      RowMapMaker mm = new RowMapMaker();
      mm.init(mapMaker, 6);

      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);

      mm.nextRow();

      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.PLAIN);

      mm.nextRow();

      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);

      mm.nextRow();

      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);

      mm.nextRow();

      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);

      mm.nextRow();

      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.MOUNTAIN_SMALL);
      mm.addTerrain(TerrainTile.MOUNTAIN_SMALL);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.MOUNTAIN_SMALL);
      mm.addTerrain(TerrainTile.MOUNTAIN_SMALL);
      mm.addTerrain(TerrainTile.MOUNTAIN_SMALL);
      mm.addTerrain(TerrainTile.PLAIN);

      mm.nextRow();

      Faction factionOne = factions.get(0);
      mm.addBuilding(TerrainType.HQ, factionOne, 0, 1);

      Faction factionTwo = factions.get(1);
      mm.addBuilding(TerrainType.HQ, factionTwo, 14, 5);
      mm.addBuilding(TerrainType.BASE, factionTwo, 0, 4);

      mm.finish();
   }
}