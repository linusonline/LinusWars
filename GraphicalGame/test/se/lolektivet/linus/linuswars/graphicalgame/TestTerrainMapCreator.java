package se.lolektivet.linus.linuswars.graphicalgame;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.TerrainTile;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;

/**
 * Created by Linus on 2015-12-18.
 */
public class TestTerrainMapCreator {
   private TerrainMapCreator _terrainMapCreator;

   @Before
   public void setup() {
      _terrainMapCreator = new TerrainMapCreator();
   }

   @Test
   public void testSingleRoad() {
      MapTestCase testCase = new MapTestCase();
      testCase.addTerrain(new Position(0, 0), TerrainType.ROAD, TerrainTile.ROAD_HORIZONTAL);
      testCase.setSize(1, 1);

      TerrainMap terrainMap = _terrainMapCreator.create(testCase.getLogicalMapMock());

      Assert.assertTrue(testCase.pass(terrainMap));
   }

   @Test
   public void testSinglePlain() {
      MapTestCase testCase = new MapTestCase();
      testCase.addTerrain(new Position(0, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.setSize(1, 1);

      TerrainMap terrainMap = _terrainMapCreator.create(testCase.getLogicalMapMock());

      Assert.assertTrue(testCase.pass(terrainMap));
   }

   @Test
   public void testSingleWoods() {
      MapTestCase testCase = new MapTestCase();
      testCase.addTerrain(new Position(0, 0), TerrainType.WOODS, TerrainTile.WOODS_SINGLE);
      testCase.setSize(1, 1);

      TerrainMap terrainMap = _terrainMapCreator.create(testCase.getLogicalMapMock());

      Assert.assertTrue(testCase.pass(terrainMap));
   }

   @Test
   public void testSingleBridge() {
      MapTestCase testCase = new MapTestCase();
      testCase.addTerrain(new Position(0, 0), TerrainType.BRIDGE, TerrainTile.BRIDGE_HORIZONTAL);
      testCase.setSize(1, 1);

      TerrainMap terrainMap = _terrainMapCreator.create(testCase.getLogicalMapMock());

      Assert.assertTrue(testCase.pass(terrainMap));
   }

   @Test
   public void testSingleMountain() {
      MapTestCase testCase = new MapTestCase();
      testCase.addTerrain(new Position(0, 0), TerrainType.MOUNTAINS, TerrainTile.MOUNTAIN_SMALL);
      testCase.setSize(1, 1);

      TerrainMap terrainMap = _terrainMapCreator.create(testCase.getLogicalMapMock());

      Assert.assertTrue(testCase.pass(terrainMap));
   }

   @Test
   public void testDoublePlain() {
      MapTestCase testCase = new MapTestCase();
      testCase.addTerrain(new Position(0, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.setSize(2, 1);

      TerrainMap terrainMap = _terrainMapCreator.create(testCase.getLogicalMapMock());

      Assert.assertTrue(testCase.pass(terrainMap));
   }

   @Test
   public void testDoubleRoad() {
      MapTestCase testCase = new MapTestCase();
      testCase.addTerrain(new Position(0, 0), TerrainType.ROAD, TerrainTile.ROAD_HORIZONTAL);
      testCase.addTerrain(new Position(1, 0), TerrainType.ROAD, TerrainTile.ROAD_HORIZONTAL);
      testCase.setSize(2, 1);

      TerrainMap terrainMap = _terrainMapCreator.create(testCase.getLogicalMapMock());

      Assert.assertTrue(testCase.pass(terrainMap));
   }

   @Test
   public void testRoadAndPlain() {
      MapTestCase testCase = new MapTestCase();
      testCase.addTerrain(new Position(0, 0), TerrainType.ROAD, TerrainTile.ROAD_HORIZONTAL);
      testCase.addTerrain(new Position(1, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.setSize(2, 1);

      TerrainMap terrainMap = _terrainMapCreator.create(testCase.getLogicalMapMock());

      Assert.assertTrue(testCase.pass(terrainMap));
   }

   @Test
   public void testFourPlains() {
      MapTestCase testCase = new MapTestCase();
      testCase.addTerrain(new Position(0, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(0, 1), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 1), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.setSize(2, 2);

      TerrainMap terrainMap = _terrainMapCreator.create(testCase.getLogicalMapMock());

      Assert.assertTrue(testCase.pass(terrainMap));
   }

   @Test
   public void testVerticalRoad() {
      MapTestCase testCase = new MapTestCase();
      testCase.addTerrain(new Position(0, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(2, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(0, 1), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 1), TerrainType.ROAD, TerrainTile.ROAD_VERTICAL);
      testCase.addTerrain(new Position(2, 1), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(0, 2), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 2), TerrainType.ROAD, TerrainTile.ROAD_VERTICAL);
      testCase.addTerrain(new Position(2, 2), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(0, 3), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 3), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(2, 3), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.setSize(3, 4);

      TerrainMap terrainMap = _terrainMapCreator.create(testCase.getLogicalMapMock());

      Assert.assertEquals(terrainMap.toString(), testCase.getExpected(), terrainMap);
   }

   @Test
   public void testVerticalRoadOnEdge() {
      MapTestCase testCase = new MapTestCase();
      testCase.addTerrain(new Position(0, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 0), TerrainType.ROAD, TerrainTile.ROAD_VERTICAL);
      testCase.addTerrain(new Position(2, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(0, 1), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 1), TerrainType.ROAD, TerrainTile.ROAD_VERTICAL);
      testCase.addTerrain(new Position(2, 1), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.setSize(3, 2);

      TerrainMap terrainMap = _terrainMapCreator.create(testCase.getLogicalMapMock());

      Assert.assertEquals(terrainMap.toString(), testCase.getExpected(), terrainMap);
   }

   @Test
   public void testRoadBendStoW() {
      MapTestCase testCase = new MapTestCase();
      testCase.addTerrain(new Position(0, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(2, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(0, 1), TerrainType.ROAD, TerrainTile.ROAD_HORIZONTAL);
      testCase.addTerrain(new Position(1, 1), TerrainType.ROAD, TerrainTile.ROAD_BEND_SW);
      testCase.addTerrain(new Position(2, 1), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(0, 2), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 2), TerrainType.ROAD, TerrainTile.ROAD_VERTICAL);
      testCase.addTerrain(new Position(2, 2), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.setSize(3, 3);

      TerrainMap terrainMap = _terrainMapCreator.create(testCase.getLogicalMapMock());

      Assert.assertEquals(terrainMap.toString(), testCase.getExpected(), terrainMap);
   }

   @Test
   public void testRoadBendStoE() {
      MapTestCase testCase = new MapTestCase();
      testCase.addTerrain(new Position(0, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(2, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(0, 1), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 1), TerrainType.ROAD, TerrainTile.ROAD_BEND_SE);
      testCase.addTerrain(new Position(2, 1), TerrainType.ROAD, TerrainTile.ROAD_HORIZONTAL);
      testCase.addTerrain(new Position(0, 2), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 2), TerrainType.ROAD, TerrainTile.ROAD_VERTICAL);
      testCase.addTerrain(new Position(2, 2), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.setSize(3, 3);

      TerrainMap terrainMap = _terrainMapCreator.create(testCase.getLogicalMapMock());

      Assert.assertEquals(terrainMap.toString(), testCase.getExpected(), terrainMap);
   }

   @Test
   public void testRoadBendNtoW() {
      MapTestCase testCase = new MapTestCase();
      testCase.addTerrain(new Position(0, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 0), TerrainType.ROAD, TerrainTile.ROAD_VERTICAL);
      testCase.addTerrain(new Position(2, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(0, 1), TerrainType.ROAD, TerrainTile.ROAD_HORIZONTAL);
      testCase.addTerrain(new Position(1, 1), TerrainType.ROAD, TerrainTile.ROAD_BEND_NW);
      testCase.addTerrain(new Position(2, 1), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(0, 2), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 2), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(2, 2), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.setSize(3, 3);

      TerrainMap terrainMap = _terrainMapCreator.create(testCase.getLogicalMapMock());

      Assert.assertEquals(terrainMap.toString(), testCase.getExpected(), terrainMap);
   }

   @Test
   public void testRoadBendNtoE() {
      MapTestCase testCase = new MapTestCase();
      testCase.addTerrain(new Position(0, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 0), TerrainType.ROAD, TerrainTile.ROAD_VERTICAL);
      testCase.addTerrain(new Position(2, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(0, 1), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 1), TerrainType.ROAD, TerrainTile.ROAD_BEND_NE);
      testCase.addTerrain(new Position(2, 1), TerrainType.ROAD, TerrainTile.ROAD_HORIZONTAL);
      testCase.addTerrain(new Position(0, 2), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 2), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(2, 2), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.setSize(3, 3);

      TerrainMap terrainMap = _terrainMapCreator.create(testCase.getLogicalMapMock());

      Assert.assertEquals(terrainMap.toString(), testCase.getExpected(), terrainMap);
   }

   @Test
   public void testRoadTSouth() {
      MapTestCase testCase = new MapTestCase();
      testCase.addTerrain(new Position(0, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(2, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(0, 1), TerrainType.ROAD, TerrainTile.ROAD_HORIZONTAL);
      testCase.addTerrain(new Position(1, 1), TerrainType.ROAD, TerrainTile.ROAD_T_SOUTH);
      testCase.addTerrain(new Position(2, 1), TerrainType.ROAD, TerrainTile.ROAD_HORIZONTAL);
      testCase.addTerrain(new Position(0, 2), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 2), TerrainType.ROAD, TerrainTile.ROAD_VERTICAL);
      testCase.addTerrain(new Position(2, 2), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.setSize(3, 3);

      TerrainMap terrainMap = _terrainMapCreator.create(testCase.getLogicalMapMock());

      Assert.assertEquals(terrainMap.toString(), testCase.getExpected(), terrainMap);
   }

   @Test
   public void testRoadTNorth() {
      MapTestCase testCase = new MapTestCase();
      testCase.addTerrain(new Position(0, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 0), TerrainType.ROAD, TerrainTile.ROAD_VERTICAL);
      testCase.addTerrain(new Position(2, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(0, 1), TerrainType.ROAD, TerrainTile.ROAD_HORIZONTAL);
      testCase.addTerrain(new Position(1, 1), TerrainType.ROAD, TerrainTile.ROAD_T_NORTH);
      testCase.addTerrain(new Position(2, 1), TerrainType.ROAD, TerrainTile.ROAD_HORIZONTAL);
      testCase.addTerrain(new Position(0, 2), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 2), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(2, 2), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.setSize(3, 3);

      TerrainMap terrainMap = _terrainMapCreator.create(testCase.getLogicalMapMock());

      Assert.assertEquals(terrainMap.toString(), testCase.getExpected(), terrainMap);
   }

   @Test
   public void testRoadTWest() {
      MapTestCase testCase = new MapTestCase();
      testCase.addTerrain(new Position(0, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 0), TerrainType.ROAD, TerrainTile.ROAD_VERTICAL);
      testCase.addTerrain(new Position(2, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(0, 1), TerrainType.ROAD, TerrainTile.ROAD_HORIZONTAL);
      testCase.addTerrain(new Position(1, 1), TerrainType.ROAD, TerrainTile.ROAD_T_WEST);
      testCase.addTerrain(new Position(2, 1), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(0, 2), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 2), TerrainType.ROAD, TerrainTile.ROAD_VERTICAL);
      testCase.addTerrain(new Position(2, 2), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.setSize(3, 3);

      TerrainMap terrainMap = _terrainMapCreator.create(testCase.getLogicalMapMock());

      Assert.assertEquals(terrainMap.toString(), testCase.getExpected(), terrainMap);
   }

   @Test
   public void testRoadTEast() {
      MapTestCase testCase = new MapTestCase();
      testCase.addTerrain(new Position(0, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 0), TerrainType.ROAD, TerrainTile.ROAD_VERTICAL);
      testCase.addTerrain(new Position(2, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(0, 1), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 1), TerrainType.ROAD, TerrainTile.ROAD_T_EAST);
      testCase.addTerrain(new Position(2, 1), TerrainType.ROAD, TerrainTile.ROAD_HORIZONTAL);
      testCase.addTerrain(new Position(0, 2), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 2), TerrainType.ROAD, TerrainTile.ROAD_VERTICAL);
      testCase.addTerrain(new Position(2, 2), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.setSize(3, 3);

      TerrainMap terrainMap = _terrainMapCreator.create(testCase.getLogicalMapMock());

      Assert.assertEquals(terrainMap.toString(), testCase.getExpected(), terrainMap);
   }

   @Test
   public void testRoadX() {
      MapTestCase testCase = new MapTestCase();
      testCase.addTerrain(new Position(0, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 0), TerrainType.ROAD, TerrainTile.ROAD_VERTICAL);
      testCase.addTerrain(new Position(2, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(0, 1), TerrainType.ROAD, TerrainTile.ROAD_HORIZONTAL);
      testCase.addTerrain(new Position(1, 1), TerrainType.ROAD, TerrainTile.ROAD_X);
      testCase.addTerrain(new Position(2, 1), TerrainType.ROAD, TerrainTile.ROAD_HORIZONTAL);
      testCase.addTerrain(new Position(0, 2), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 2), TerrainType.ROAD, TerrainTile.ROAD_VERTICAL);
      testCase.addTerrain(new Position(2, 2), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.setSize(3, 3);

      TerrainMap terrainMap = _terrainMapCreator.create(testCase.getLogicalMapMock());

      Assert.assertEquals(terrainMap.toString(), testCase.getExpected(), terrainMap);
   }

   @Test
   public void testRoadBendStoWOnEdge() {
      MapTestCase testCase = new MapTestCase();
      testCase.addTerrain(new Position(0, 0), TerrainType.ROAD, TerrainTile.ROAD_HORIZONTAL);
      testCase.addTerrain(new Position(1, 0), TerrainType.ROAD, TerrainTile.ROAD_BEND_SW);
      testCase.addTerrain(new Position(0, 1), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 1), TerrainType.ROAD, TerrainTile.ROAD_VERTICAL);
      testCase.setSize(2, 2);

      TerrainMap terrainMap = _terrainMapCreator.create(testCase.getLogicalMapMock());

      Assert.assertEquals(terrainMap.toString(), testCase.getExpected(), terrainMap);
   }

   @Test
   public void testRoadBendStoEOnEdge() {
      MapTestCase testCase = new MapTestCase();
      testCase.addTerrain(new Position(0, 0), TerrainType.ROAD, TerrainTile.ROAD_BEND_SE);
      testCase.addTerrain(new Position(1, 0), TerrainType.ROAD, TerrainTile.ROAD_HORIZONTAL);
      testCase.addTerrain(new Position(0, 1), TerrainType.ROAD, TerrainTile.ROAD_VERTICAL);
      testCase.addTerrain(new Position(1, 1), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.setSize(2, 2);

      TerrainMap terrainMap = _terrainMapCreator.create(testCase.getLogicalMapMock());

      Assert.assertEquals(terrainMap.toString(), testCase.getExpected(), terrainMap);
   }

   @Test
   public void testRoadBendNtoWOnEdge() {
      MapTestCase testCase = new MapTestCase();
      testCase.addTerrain(new Position(0, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(1, 0), TerrainType.ROAD, TerrainTile.ROAD_VERTICAL);
      testCase.addTerrain(new Position(0, 1), TerrainType.ROAD, TerrainTile.ROAD_HORIZONTAL);
      testCase.addTerrain(new Position(1, 1), TerrainType.ROAD, TerrainTile.ROAD_BEND_NW);
      testCase.setSize(2, 2);

      TerrainMap terrainMap = _terrainMapCreator.create(testCase.getLogicalMapMock());

      Assert.assertEquals(terrainMap.toString(), testCase.getExpected(), terrainMap);
   }

   @Test
   public void testRoadBendNtoEOnEdge() {
      MapTestCase testCase = new MapTestCase();
      testCase.addTerrain(new Position(0, 0), TerrainType.ROAD, TerrainTile.ROAD_VERTICAL);
      testCase.addTerrain(new Position(1, 0), TerrainType.PLAINS, TerrainTile.PLAIN);
      testCase.addTerrain(new Position(0, 1), TerrainType.ROAD, TerrainTile.ROAD_BEND_NE);
      testCase.addTerrain(new Position(1, 1), TerrainType.ROAD, TerrainTile.ROAD_HORIZONTAL);
      testCase.setSize(2, 2);

      TerrainMap terrainMap = _terrainMapCreator.create(testCase.getLogicalMapMock());

      Assert.assertEquals(terrainMap.toString(), testCase.getExpected(), terrainMap);
   }

}
