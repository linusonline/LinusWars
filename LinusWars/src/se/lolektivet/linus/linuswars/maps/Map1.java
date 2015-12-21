package se.lolektivet.linus.linuswars.maps;

import se.lolektivet.linus.linuswars.logic.MapMaker;
import se.lolektivet.linus.linuswars.logic.RowMapMaker;
import se.lolektivet.linus.linuswars.logic.WarMap;
import se.lolektivet.linus.linuswars.logic.WarMapAdapter;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainTile;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;

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

      mm.validate();
   }
}
