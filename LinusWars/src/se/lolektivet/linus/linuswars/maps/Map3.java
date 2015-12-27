package se.lolektivet.linus.linuswars.maps;

import se.lolektivet.linus.linuswars.logic.MapMaker;
import se.lolektivet.linus.linuswars.logic.RowMapMaker;
import se.lolektivet.linus.linuswars.logic.WarMapAdapter;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;

import java.util.List;

/**
 * Created by Linus on 2015-12-23.
 */
public class Map3 extends WarMapAdapter {

   @Override
   public int getNrOfFactions() {
      return 2;
   }

   @Override
   public void create(MapMaker mapMaker, List<Faction> factions) {
      RowMapMaker mm = new RowMapMaker();
      mm.init(mapMaker, 10);

      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.PLAINS);

      mm.nextRow();

      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.PLAINS);

      mm.nextRow();

      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.MOUNTAINS);
      mm.addTerrain(TerrainType.MOUNTAINS);
      mm.addTerrain(TerrainType.MOUNTAINS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.PLAINS);

      mm.nextRow();

      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.MOUNTAINS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.PLAINS);

      mm.nextRow();

      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.MOUNTAINS);
      mm.addTerrain(TerrainType.MOUNTAINS);
      mm.addTerrain(TerrainType.MOUNTAINS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.PLAINS);

      mm.nextRow();

      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.PLAINS);

      mm.nextRow();

      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.MOUNTAINS);
      mm.addTerrain(TerrainType.MOUNTAINS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.PLAINS);

      mm.nextRow();

      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.MOUNTAINS);
      mm.addTerrain(TerrainType.MOUNTAINS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.PLAINS);

      mm.nextRow();

      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.MOUNTAINS);
      mm.addTerrain(TerrainType.MOUNTAINS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.ROAD);

      mm.nextRow();

      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.ROAD);
      mm.addTerrain(TerrainType.MOUNTAINS);
      mm.addTerrain(TerrainType.MOUNTAINS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.WOODS);
      mm.addTerrain(TerrainType.MOUNTAINS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.PLAINS);
      mm.addTerrain(TerrainType.PLAINS);

      mm.nextRow();

      Faction factionOne = factions.get(0);
      mm.addBuilding(TerrainType.HQ, factionOne, 0, 0);

      Faction factionTwo = factions.get(1);
      mm.addBuilding(TerrainType.HQ, factionTwo, 14, 9);

      mm.finish();
   }
}
