package se.lolektivet.linus.linuswars.core;

import org.junit.Test;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;
import se.lolektivet.linus.linuswars.core.game.LogicalWarMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Linus on 2016-12-02.
 */
public class TestMapFile extends LinusWarsTest {

   @Test
   public void testReadMapFile() throws IOException {
      MapFile mapFile = new MapFile("test/testmap.lwmap");
      mapFile.readFileFully();
      LogicalWarMap logicalWarMap = new LogicalGameFactory().createLogicalMap(mapFile, Arrays.asList(Faction.ORANGE_STAR, Faction.BLUE_MOON));

      assertSimpleMap(logicalWarMap);
   }

   @Test
   public void testReadBuffer() throws IOException {
      String mapFileContent =
            "LINUSMAP:2\n" +
                  "Q1L\n" +
                  "LQ2\n";
      LogicalWarMap logicalWarMap = getMapFileFromFileContents(mapFileContent, Arrays.asList(Faction.ORANGE_STAR, Faction.BLUE_MOON));
      assertSimpleMap(logicalWarMap);
   }

   @Test
   public void testAcceptEmptyLines() throws IOException {
      String mapFileContent = "\n\n\n" +
            "LINUSMAP:2\n" +
            "Q1L\n" +
            "LQ2\n";
      LogicalWarMap logicalWarMap = getMapFileFromFileContents(mapFileContent, Arrays.asList(Faction.ORANGE_STAR, Faction.BLUE_MOON));
      assertSimpleMap(logicalWarMap);
   }

   @Test
   public void testAcceptEmptyLinesInMap() throws IOException {
      String mapFileContent = "\n" +
            "LINUSMAP:2\n" +
            "\n" +
            "Q1L\n" +
            "\n" +
            "LQ2\n" +
            "\n";
      LogicalWarMap logicalWarMap = getMapFileFromFileContents(mapFileContent, Arrays.asList(Faction.ORANGE_STAR, Faction.BLUE_MOON));
      assertSimpleMap(logicalWarMap);
   }

   @Test
   public void testAcceptComments() throws IOException {
      String mapFileContent = "# This is a comment\n" +
            "LINUSMAP:2\n" +
            "Q1L\n" +
            "LQ2\n";
      LogicalWarMap logicalWarMap = getMapFileFromFileContents(mapFileContent, Arrays.asList(Faction.ORANGE_STAR, Faction.BLUE_MOON));
      assertSimpleMap(logicalWarMap);
   }

   @Test(expected = IllegalMapOrSetupException.class)
   public void testThrowOnWrongNumberOfFactions() throws IOException {
      String mapFileContent = "# This is a comment\n" +
            "LINUSMAP:3\n" +
            "Q1L\n" +
            "LQ2\n";
      LogicalWarMap logicalWarMap = getMapFileFromFileContents(mapFileContent, Arrays.asList(Faction.ORANGE_STAR, Faction.BLUE_MOON));
   }

   @Test
   public void testAcceptCommentsInMap() throws IOException {
      String mapFileContent = "# This is a comment\n" +
            "LINUSMAP:2\n" +
            "# This is a comment\n" +
            "Q1L\n" +
            "# This is a comment\n" +
            "LQ2\n" +
            "# This is a comment\n";
      LogicalWarMap logicalWarMap = getMapFileFromFileContents(mapFileContent, Arrays.asList(Faction.ORANGE_STAR, Faction.BLUE_MOON));
      assertSimpleMap(logicalWarMap);
   }

   public void assertSimpleMap(LogicalWarMap logicalWarMap) {
      assertEquals(TerrainType.HQ, logicalWarMap.getTerrainForTile(new Position(0, 0)));
      assertEquals(TerrainType.PLAINS, logicalWarMap.getTerrainForTile(new Position(1, 0)));
      assertEquals(TerrainType.PLAINS, logicalWarMap.getTerrainForTile(new Position(0, 1)));
      assertEquals(TerrainType.HQ, logicalWarMap.getTerrainForTile(new Position(1, 1)));
      assertEquals(2, logicalWarMap.getHeight());
      assertEquals(2, logicalWarMap.getWidth());
   }

   private LogicalWarMap getMapFileFromFileContents(String fileContents, List<Faction> factions) throws IOException {
      MapFile mapFile = new MapFile();
      mapFile.readBufferFully(new BufferedReader(new StringReader(fileContents)));
      return new LogicalGameFactory().createLogicalMap(mapFile, factions);
   }
}
