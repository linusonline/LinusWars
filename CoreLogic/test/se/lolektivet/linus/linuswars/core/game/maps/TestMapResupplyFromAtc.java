package se.lolektivet.linus.linuswars.core.game.maps;

import se.lolektivet.linus.linuswars.core.map.MapMaker;
import se.lolektivet.linus.linuswars.core.map.RowMapMaker;
import se.lolektivet.linus.linuswars.core.map.WarMapAdapter;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.TerrainTile;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;

import java.util.List;

/**
 * Created by Linus on 2014-09-21.
 */
public class TestMapResupplyFromAtc extends WarMapAdapter {

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

      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.PLAIN);
      mm.addTerrain(TerrainTile.SEA_EDGE_NE);
      mm.addTerrain(TerrainTile.SEA_EDGE_N);
      mm.addTerrain(TerrainTile.SEA_EDGE_N);
      mm.addTerrain(TerrainTile.SEA_EDGE_N);
      mm.addTerrain(TerrainTile.SEA_EDGE_N);
      mm.addTerrain(TerrainTile.SEA_EDGE_N);

      mm.nextRow();

      mm.addTerrain(TerrainTile.SEA_EDGE_N);
      mm.addTerrain(TerrainTile.SEA_EDGE_N);
      mm.addTerrain(TerrainTile.SEA_EDGE_N);
      mm.addTerrain(TerrainTile.SEA_EDGE_N);
      mm.addTerrain(TerrainTile.SEA_EDGE_N);
      mm.addTerrain(TerrainTile.SEA);
      mm.addTerrain(TerrainTile.SEA);
      mm.addTerrain(TerrainTile.SEA);
      mm.addTerrain(TerrainTile.SEA);
      mm.addTerrain(TerrainTile.SEA);
      mm.addTerrain(TerrainTile.SEA);

      mm.nextRow();


      mm.addBuilding(TerrainType.HQ, Faction.BLUE_MOON, 0, 0);
      mm.addBuilding(TerrainType.HQ, Faction.ORANGE_STAR, 0, 1);
   }
}
