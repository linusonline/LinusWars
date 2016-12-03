package se.lolektivet.linus.linuswars.pathfinding.maps;

import se.lolektivet.linus.linuswars.core.map.MapMaker;
import se.lolektivet.linus.linuswars.core.map.RowMapMaker;
import se.lolektivet.linus.linuswars.core.map.WarMapAdapter;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.TerrainTile;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;

import java.util.List;

/**
 * Created by Linus on 2016-11-30.
 */
public class TestMapMovementArrowController extends WarMapAdapter {
   @Override
   public int getNrOfFactions() {
      return 2;
   }

   @Override
   public void create(MapMaker mapMaker, List<Faction> factions) {
      RowMapMaker mm = new RowMapMaker();
      mm.init(mapMaker, 2);

      mm.addTerrain(TerrainTile.ROAD_VERTICAL);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.ROAD_VERTICAL);

      mm.nextRow();

      mm.addTerrain(TerrainTile.ROAD_BEND_NE);
      mm.addTerrain(TerrainTile.ROAD_HORIZONTAL);
      mm.addTerrain(TerrainTile.ROAD_HORIZONTAL);
      mm.addTerrain(TerrainTile.ROAD_HORIZONTAL);
      mm.addTerrain(TerrainTile.ROAD_BEND_NW);

      mm.nextRow();


      mm.addBuilding(TerrainType.HQ, Faction.BLUE_MOON, 0, 0);
      mm.addBuilding(TerrainType.HQ, Faction.ORANGE_STAR, 0, 1);
   }
}
