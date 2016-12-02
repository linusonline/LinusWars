package se.lolektivet.linus.linuswars.core.game.maps;

import se.lolektivet.linus.linuswars.core.MapMaker;
import se.lolektivet.linus.linuswars.core.RowMapMaker;
import se.lolektivet.linus.linuswars.core.WarMapAdapter;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.TerrainTile;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;

import java.util.List;

/**
 * Created by Linus on 2016-11-30.
 */
public class TestMapPathfinder extends WarMapAdapter {

   @Override
   public int getNrOfFactions() {
      return 2;
   }

   @Override
   public void create(MapMaker mapMaker, List<Faction> factions) {
      RowMapMaker mm = new RowMapMaker();
      mm.init(mapMaker, 8);

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
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
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
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);

      mm.nextRow();

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
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);

      mm.nextRow();

      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.MOUNTAIN_SMALL);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);

      mm.nextRow();

      mm.addTerrain(TerrainTile.ROAD_VERTICAL);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.WOODS_SINGLE);
      mm.addTerrain(TerrainTile.ROAD_VERTICAL);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);

      mm.nextRow();

      mm.addTerrain(TerrainTile.ROAD_BEND_NE);
      mm.addTerrain(TerrainTile.ROAD_HORIZONTAL);
      mm.addTerrain(TerrainTile.ROAD_HORIZONTAL);
      mm.addTerrain(TerrainTile.ROAD_HORIZONTAL);
      mm.addTerrain(TerrainTile.ROAD_BEND_NW);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);

      mm.nextRow();


      mm.addBuilding(TerrainType.HQ, Faction.BLUE_MOON, 0, 0);

      mm.addBuilding(TerrainType.HQ, Faction.ORANGE_STAR, 7, 7);
   }
}
