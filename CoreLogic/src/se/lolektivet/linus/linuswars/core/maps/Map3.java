package se.lolektivet.linus.linuswars.core.maps;

import se.lolektivet.linus.linuswars.core.MapMaker;
import se.lolektivet.linus.linuswars.core.RowMapMaker;
import se.lolektivet.linus.linuswars.core.WarMapAdapter;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;

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
      mm.addBuilding(TerrainType.BASE, factionOne, 0, 1);

      Faction factionTwo = factions.get(1);
      mm.addBuilding(TerrainType.HQ, factionTwo, 14, 9);
      mm.addBuilding(TerrainType.BASE, factionTwo, 14, 7);

      mm.finish();
   }
}
