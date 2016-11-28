package se.lolektivet.linus.linuswars.core.game.maps;

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
public class TestMap3 extends WarMapAdapter {

   @Override
   public int getNrOfFactions() {
      return 2;
   }

   @Override
   public void create(MapMaker mapMaker, List<Faction> factions) {
      RowMapMaker mm = new RowMapMaker();
      mm.init(mapMaker, 4);

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

      mm.addTerrain(TerrainTile.SEA_EDGE_N);
      mm.addTerrain(TerrainTile.SEA_EDGE_N);
      mm.addTerrain(TerrainTile.SEA_EDGE_N);
      mm.addTerrain(TerrainTile.SEA_EDGE_N);
      mm.addTerrain(TerrainTile.SEA_EDGE_N);
      mm.addTerrain(TerrainTile.SEA_EDGE_N);
      mm.addTerrain(TerrainTile.SEA_EDGE_N);
      mm.addTerrain(TerrainTile.SEA_EDGE_N);
      mm.addTerrain(TerrainTile.SEA_EDGE_N);
      mm.addTerrain(TerrainTile.SEA_EDGE_N);
      mm.addTerrain(TerrainTile.SEA_EDGE_N);

      mm.nextRow();

      mm.addTerrain(TerrainTile.SEA);
      mm.addTerrain(TerrainTile.SEA);
      mm.addTerrain(TerrainTile.SEA);
      mm.addTerrain(TerrainTile.SEA);
      mm.addTerrain(TerrainTile.SEA);
      mm.addTerrain(TerrainTile.SEA);
      mm.addTerrain(TerrainTile.SEA);
      mm.addTerrain(TerrainTile.SEA);
      mm.addTerrain(TerrainTile.SEA);
      mm.addTerrain(TerrainTile.SEA);
      mm.addTerrain(TerrainTile.SEA);

      mm.nextRow();


      mm.addBuilding(TerrainType.HQ, Faction.BLUE_MOON, 0, 0);
      mm.addBuilding(TerrainType.CITY, Faction.BLUE_MOON, 1, 0);
      mm.addBuilding(TerrainType.CITY, Faction.BLUE_MOON, 2, 0);
      mm.addBuilding(TerrainType.BASE, Faction.BLUE_MOON, 3, 0);
      mm.addBuilding(TerrainType.BASE, Faction.BLUE_MOON, 4, 0);
      mm.addBuilding(TerrainType.AIRPORT, Faction.BLUE_MOON, 5, 0);
      mm.addBuilding(TerrainType.AIRPORT, Faction.BLUE_MOON, 6, 0);
      mm.addBuilding(TerrainType.PORT, Faction.BLUE_MOON, 0, 1);
      mm.addBuilding(TerrainType.PORT, Faction.BLUE_MOON, 1, 1);
      mm.addBuilding(TerrainType.PORT, Faction.BLUE_MOON, 2, 1);
      mm.addBuilding(TerrainType.PORT, Faction.BLUE_MOON, 3, 1);
      mm.addBuilding(TerrainType.AIRPORT, Faction.BLUE_MOON, 4, 1);
      mm.addBuilding(TerrainType.AIRPORT, Faction.BLUE_MOON, 5, 1);

      mm.addBuilding(TerrainType.HQ, Faction.ORANGE_STAR, 10, 1);
   }
}
