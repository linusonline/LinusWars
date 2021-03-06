package se.lolektivet.linus.linuswars.graphicalgame;

import se.lolektivet.linus.linuswars.core.LogicException;
import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;
import se.lolektivet.linus.linuswars.core.game.LogicalWarMap;

/**
 * Created by Linus on 2015-12-20.
 */
public class LogicalWarMapMock implements LogicalWarMap {
   private LogicalWarMap _mapMock;

   public LogicalWarMapMock(LogicalWarMap mapMock) {
      _mapMock = mapMock;
   }

   @Override
   public boolean hasTerrainForTile(Position tile) {
      return _mapMock.getTerrainForTile(tile) != null;
   }

   @Override
   public TerrainType getTerrainForTile(Position tile) {
      TerrainType type = _mapMock.getTerrainForTile(tile);
      if (type == null) {
         throw new LogicException();
      }
      return type;
   }

   @Override
   public int getWidth() {
      return _mapMock.getWidth();
   }

   @Override
   public int getHeight() {
      return _mapMock.getHeight();
   }

   @Override
   public boolean isPositionInsideMap(Position position) {
      return position.getX() >= 0 &&
            position.getX() < getWidth() &&
            position.getY() >= 0 &&
            position.getY() < getHeight();
   }
}
