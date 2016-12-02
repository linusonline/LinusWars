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
public class TestMap_executeDeployMove extends WarMapAdapter {

   public static final int ORANGE_STAR_STARTING_FUNDS = 15000;
   // ORANGE STAR has 14 properties, starts with 14,000 G.

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


      mm.addBuilding(TerrainType.HQ, Faction.ORANGE_STAR, 0, 0);
      mm.addBuilding(TerrainType.CITY, Faction.ORANGE_STAR, 1, 0);
      mm.addBuilding(TerrainType.CITY, Faction.ORANGE_STAR, 2, 0);
      mm.addBuilding(TerrainType.CITY, Faction.ORANGE_STAR, 3, 0);
      mm.addBuilding(TerrainType.BASE, Faction.ORANGE_STAR, 4, 0);
      mm.addBuilding(TerrainType.BASE, Faction.ORANGE_STAR, 5, 0);
      mm.addBuilding(TerrainType.BASE, Faction.ORANGE_STAR, 6, 0);
      mm.addBuilding(TerrainType.AIRPORT, Faction.ORANGE_STAR, 7, 0);
      mm.addBuilding(TerrainType.AIRPORT, Faction.ORANGE_STAR, 8, 0);
      mm.addBuilding(TerrainType.AIRPORT, Faction.ORANGE_STAR, 9, 0);
      mm.addBuilding(TerrainType.PORT, Faction.ORANGE_STAR, 1, 2);
      mm.addBuilding(TerrainType.PORT, Faction.ORANGE_STAR, 2, 2);
      mm.addBuilding(TerrainType.PORT, Faction.ORANGE_STAR, 3, 2);
      mm.addBuilding(TerrainType.BASE, Faction.ORANGE_STAR, 4, 2);
      mm.addBuilding(TerrainType.BASE, Faction.BLUE_MOON, 5, 2);
      mm.addBuilding(TerrainType.BASE, Faction.ORANGE_STAR, 6, 2);

      mm.addBuilding(TerrainType.HQ, Faction.BLUE_MOON, 10, 1);
   }
}
