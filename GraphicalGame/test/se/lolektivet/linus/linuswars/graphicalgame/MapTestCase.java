package se.lolektivet.linus.linuswars.graphicalgame;

import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.TerrainTile;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;
import se.lolektivet.linus.linuswars.core.game.LogicalWarMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Linus on 2015-12-18.
 */
public class MapTestCase {
   private LogicalWarMap mapMock;
   private TerrainMap expectedTerrainMap;

   public MapTestCase() {
      mapMock = mock(LogicalWarMap.class);
      expectedTerrainMap = new TerrainMap();
   }

   public void setSize(int width, int height) {
      when(mapMock.getWidth()).thenReturn(width);
      when(mapMock.getHeight()).thenReturn(height);
   }

   public void addTerrain(Position position, TerrainType type, TerrainTile expectedTerrain) {
      when(mapMock.getTerrainForTile(position)).thenReturn(type);
      expectedTerrainMap.put(position, expectedTerrain);
   }

   public LogicalWarMap getLogicalMapMock() {
      return new LogicalWarMapMock(mapMock);
   }

   public TerrainMap getExpected() {
      return expectedTerrainMap;
   }

   public boolean pass(TerrainMap result) {
      return expectedTerrainMap.equals(result);
   }
}
