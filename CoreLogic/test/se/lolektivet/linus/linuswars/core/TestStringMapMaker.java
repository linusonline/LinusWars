package se.lolektivet.linus.linuswars.core;

import org.junit.Before;
import org.junit.Test;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;
import se.lolektivet.linus.linuswars.core.map.MapMaker;
import se.lolektivet.linus.linuswars.core.map.StringMapMaker;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Linus on 2015-11-23.
 */
public class TestStringMapMaker extends LinusWarsTest {

   private MapMaker _mapMakerMock;
   private StringMapMaker _stringMapMaker;

   @Before
   public void setup() {
      _mapMakerMock = mock(MapMaker.class);
      _stringMapMaker = new StringMapMaker(_mapMakerMock);
   }

   @Test
   public void testSinglePlainTile() {
      _stringMapMaker.readRow("L");

      verify(_mapMakerMock).addTerrain(TerrainType.PLAINS, 0, 0);
   }

   @Test
   public void testSingleWoodTile() {
      _stringMapMaker.readRow("W");

      verify(_mapMakerMock).addTerrain(TerrainType.WOODS, 0, 0);
   }

   @Test
   public void testSingleMtnTile() {
      _stringMapMaker.readRow("M");

      verify(_mapMakerMock).addTerrain(TerrainType.MOUNTAINS, 0, 0);
   }

   @Test
   public void testRow() {
      _stringMapMaker.readRow("LL");

      verify(_mapMakerMock).addTerrain(TerrainType.PLAINS, 0, 0);
      verify(_mapMakerMock).addTerrain(TerrainType.PLAINS, 1, 0);
   }

   @Test
   public void testMultiRow() {
      _stringMapMaker.readRow("LL");
      _stringMapMaker.readRow("LL");

      verify(_mapMakerMock).addTerrain(TerrainType.PLAINS, 0, 0);
      verify(_mapMakerMock).addTerrain(TerrainType.PLAINS, 1, 0);
      verify(_mapMakerMock).addTerrain(TerrainType.PLAINS, 0, 1);
      verify(_mapMakerMock).addTerrain(TerrainType.PLAINS, 1, 1);
   }

   @Test
   public void testMixedTerrain() {
      _stringMapMaker.readRow("LWMVRHDSE");

      verify(_mapMakerMock).addTerrain(TerrainType.PLAINS, 0, 0);
      verify(_mapMakerMock).addTerrain(TerrainType.WOODS, 1, 0);
      verify(_mapMakerMock).addTerrain(TerrainType.MOUNTAINS, 2, 0);
      verify(_mapMakerMock).addTerrain(TerrainType.RIVER, 3, 0);
      verify(_mapMakerMock).addTerrain(TerrainType.ROAD, 4, 0);
      verify(_mapMakerMock).addTerrain(TerrainType.SHOAL, 5, 0);
      verify(_mapMakerMock).addTerrain(TerrainType.BRIDGE, 6, 0);
      verify(_mapMakerMock).addTerrain(TerrainType.SEA, 7, 0);
      verify(_mapMakerMock).addTerrain(TerrainType.REEF, 8, 0);
   }

   @Test
   public void testBuildings() {
      _stringMapMaker.readRow("C1B2A3P4Q5Cn");

      verify(_mapMakerMock).addBuilding(TerrainType.CITY, Faction.ORANGE_STAR, 0, 0);
      verify(_mapMakerMock).addBuilding(TerrainType.BASE, Faction.BLUE_MOON, 1, 0);
      verify(_mapMakerMock).addBuilding(TerrainType.AIRPORT, Faction.GREEN_EARTH, 2, 0);
      verify(_mapMakerMock).addBuilding(TerrainType.PORT, Faction.YELLOW_COMET, 3, 0);
      verify(_mapMakerMock).addBuilding(TerrainType.HQ, Faction.BLACK_HOLE, 4, 0);
      verify(_mapMakerMock).addBuilding(TerrainType.CITY, Faction.NEUTRAL, 5, 0);
   }

   @Test
   public void testReplaceFactions() {
      _stringMapMaker = new StringMapMaker(_mapMakerMock, Arrays.asList(Faction.BLUE_MOON, Faction.GREEN_EARTH, Faction.YELLOW_COMET, Faction.BLACK_HOLE, Faction.ORANGE_STAR));
      _stringMapMaker.readRow("C1B2A3P4Q5Cn");

      verify(_mapMakerMock).addBuilding(TerrainType.CITY, Faction.BLUE_MOON, 0, 0);
      verify(_mapMakerMock).addBuilding(TerrainType.BASE, Faction.GREEN_EARTH, 1, 0);
      verify(_mapMakerMock).addBuilding(TerrainType.AIRPORT, Faction.YELLOW_COMET, 2, 0);
      verify(_mapMakerMock).addBuilding(TerrainType.PORT, Faction.BLACK_HOLE, 3, 0);
      verify(_mapMakerMock).addBuilding(TerrainType.HQ, Faction.ORANGE_STAR, 4, 0);
      verify(_mapMakerMock).addBuilding(TerrainType.CITY, Faction.NEUTRAL, 5, 0);
   }

   @Test
   public void testHardcodedFactions() {
      _stringMapMaker = new StringMapMaker(_mapMakerMock, Arrays.asList(Faction.BLUE_MOON, Faction.GREEN_EARTH, Faction.YELLOW_COMET, Faction.BLACK_HOLE, Faction.ORANGE_STAR));
      _stringMapMaker.readRow("CoBbAgPyQlCn");

      verify(_mapMakerMock).addBuilding(TerrainType.CITY, Faction.ORANGE_STAR, 0, 0);
      verify(_mapMakerMock).addBuilding(TerrainType.BASE, Faction.BLUE_MOON, 1, 0);
      verify(_mapMakerMock).addBuilding(TerrainType.AIRPORT, Faction.GREEN_EARTH, 2, 0);
      verify(_mapMakerMock).addBuilding(TerrainType.PORT, Faction.YELLOW_COMET, 3, 0);
      verify(_mapMakerMock).addBuilding(TerrainType.HQ, Faction.BLACK_HOLE, 4, 0);
      verify(_mapMakerMock).addBuilding(TerrainType.CITY, Faction.NEUTRAL, 5, 0);
   }

   @Test
   public void testAcceptSpaces() {
      _stringMapMaker.readRow("LL");
      _stringMapMaker.readRow("L    L");
      _stringMapMaker.readRow("L L");

      verify(_mapMakerMock).addTerrain(TerrainType.PLAINS, 0, 0);
      verify(_mapMakerMock).addTerrain(TerrainType.PLAINS, 1, 0);
      verify(_mapMakerMock).addTerrain(TerrainType.PLAINS, 0, 1);
      verify(_mapMakerMock).addTerrain(TerrainType.PLAINS, 1, 1);
      verify(_mapMakerMock).addTerrain(TerrainType.PLAINS, 0, 2);
      verify(_mapMakerMock).addTerrain(TerrainType.PLAINS, 1, 2);
   }

   @Test(expected = IllegalMapException.class)
   public void testThrowOnEmptyLine() {
      _stringMapMaker.readRow("");
   }

   @Test(expected = IllegalMapException.class)
   public void testThrowOnOnlySpace() {
      _stringMapMaker.readRow(" ");
   }

   @Test(expected = IllegalMapException.class)
   public void testThrowOnOnlyWhiteSpace() {
      _stringMapMaker.readRow(" \n\t\r");
   }

   @Test(expected = IllegalMapException.class)
   public void testThrowOnUnknownCode() {
      _stringMapMaker.readRow("LLLXLLL");
   }

   @Test(expected = IllegalMapException.class)
   public void testThrowOnUnknownFaction() {
      _stringMapMaker.readRow("LLLBxLLL");
   }

   @Test(expected = IllegalMapException.class)
   public void testThrowOnUnknownFactionNumber() {
      _stringMapMaker.readRow("LLLB9LLL");
   }

   @Test
   public void testFinish() {
      _stringMapMaker.finish();

      verify(_mapMakerMock).finish();
   }
}
