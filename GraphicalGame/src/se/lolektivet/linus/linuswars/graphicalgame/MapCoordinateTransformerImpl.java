package se.lolektivet.linus.linuswars.graphicalgame;

import se.lolektivet.linus.linuswars.logic.Position;

/**
 * Created by Linus on 2014-09-19.
 */
public class MapCoordinateTransformerImpl implements MapCoordinateTransformer {
   private int _tileOffsetX = 0;
   private int _tileOffsetY = 0;
   private int _visibleTileWidth = 1000;
   private int _visibleTileHeight = 1000;
   private static final int HORIZONTAL_CURSOR_MARGIN = 3;
   private static final int VERTICAL_CURSOR_MARGIN = 3;

   @Override
   public int transformX(int mapCoord) {
      return baseTransform(mapCoord - _tileOffsetX);
   }

   @Override
   public int transformY(int mapCoord) {
      return baseTransform(mapCoord - _tileOffsetY);
   }

   @Override
   public Position transform(Position position) {
      return new Position(transformX(position.getX()), transformY(position.getY()));
   }

   @Override
   public void setTileOffset(int x, int y) {
      _tileOffsetX = x;
      _tileOffsetY = y;
   }

   @Override
   public void setVisibleRectSize(int width, int height) {
      _visibleTileWidth = width;
      _visibleTileHeight = height;
   }

   @Override
   public boolean isVisible(int mapX, int mapY) {
      return _tileOffsetX <= mapX && mapX < _tileOffsetX + _visibleTileWidth &&
            _tileOffsetY <= mapY && mapY < _tileOffsetY + _visibleTileHeight;

   }

   public void cursorMoved(Position newPosition, int mapWidth, int mapHeight) {
      if (newPosition.getX() < _tileOffsetX + HORIZONTAL_CURSOR_MARGIN) {
         _tileOffsetX = Math.max(0, newPosition.getX() - HORIZONTAL_CURSOR_MARGIN);
      } else if (newPosition.getX() >= _tileOffsetX + _visibleTileWidth - HORIZONTAL_CURSOR_MARGIN) {
         _tileOffsetX = Math.min(mapWidth - _visibleTileWidth, newPosition.getX() + HORIZONTAL_CURSOR_MARGIN + 1- _visibleTileWidth);
      }
      if (newPosition.getY() < _tileOffsetY + VERTICAL_CURSOR_MARGIN) {
         _tileOffsetY = Math.max(0, newPosition.getY() - VERTICAL_CURSOR_MARGIN);
      } else if (newPosition.getY() >= _tileOffsetY + _visibleTileHeight - VERTICAL_CURSOR_MARGIN) {
         _tileOffsetY = Math.min(mapHeight - _visibleTileHeight, newPosition.getY() + VERTICAL_CURSOR_MARGIN + 1 - _visibleTileHeight);
      }
   }

   private int baseTransform(int mapCoord) {
      return mapCoord*16;
   }
}
