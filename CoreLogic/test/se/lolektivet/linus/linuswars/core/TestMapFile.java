package se.lolektivet.linus.linuswars.core;

import org.junit.Test;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;
import se.lolektivet.linus.linuswars.core.game.LogicalWarMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Linus on 2016-12-02.
 */
public class TestMapFile {

   @Test
   public void testReadMapFile() throws IOException {
      MapFile mapFile = new MapFile("test/testmap.lwmap");
      mapFile.readFileFully();
      List<Faction> factions = new ArrayList<>(2);
      factions.add(Faction.ORANGE_STAR);
      factions.add(Faction.BLUE_MOON);
      LogicalWarMap logicalWarMap = new LogicalGameFactory().createLogicalMap(mapFile, factions);

      assertEquals(TerrainType.HQ, logicalWarMap.getTerrainForTile(new Position(0, 0)));
      assertEquals(TerrainType.PLAINS, logicalWarMap.getTerrainForTile(new Position(0, 1)));
      assertEquals(TerrainType.PLAINS, logicalWarMap.getTerrainForTile(new Position(1, 0)));
      assertEquals(TerrainType.HQ, logicalWarMap.getTerrainForTile(new Position(1, 1)));
      assertEquals(2, logicalWarMap.getHeight());
      assertEquals(2, logicalWarMap.getWidth());
   }
}
