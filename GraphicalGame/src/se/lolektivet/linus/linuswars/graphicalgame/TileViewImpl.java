package se.lolektivet.linus.linuswars.graphicalgame;

import se.lolektivet.linus.linuswars.logic.Position;

/**
 * Created by Linus on 2014-09-19.
 */
public class TileViewImpl implements TileView {
   private int _tileOffsetX = 0;
   private int _tileOffsetY = 0;
   private int _visibleTileWidth = 1000;
   private int _visibleTileHeight = 1000;

   @Override
   public boolean isTileVisible(int tileX, int tileY) {
      return _tileOffsetX <= tileX && tileX < _tileOffsetX + _visibleTileWidth &&
            _tileOffsetY <= tileY && tileY < _tileOffsetY + _visibleTileHeight;

   }

   @Override
   public int tileToPixelX(int tileX) {
      return baseTransform(tileX - _tileOffsetX);
   }

   @Override
   public int tileToPixelY(int tileY) {
      return baseTransform(tileY - _tileOffsetY);
   }

   @Override
   public int tileHeightInPixels(int tiles) {
      return baseTransform(tiles);
   }

   @Override
   public int tileWidthInPixels(int tiles) {
      return baseTransform(tiles);
   }

   public void setVisibleRectSize(int width, int height) {
      _visibleTileWidth = width;
      _visibleTileHeight = height;
   }

   void setTileOffset(int x, int y) {
      _tileOffsetX = x;
      _tileOffsetY = y;
   }

   Position getTileOffset() {
      return new Position(_tileOffsetX, _tileOffsetY);
   }

   @Override
   public int getVisibleTileWidth() {
      return _visibleTileWidth;
   }

   @Override
   public int getVisibleTileHeight() {
      return _visibleTileHeight;
   }

   @Override
   public int getVisiblePixelWidth() {
      return baseTransform(_visibleTileWidth);
   }

   @Override
   public int getVisiblePixelHeight() {
      return baseTransform(_visibleTileHeight);
   }

   private int baseTransform(int tileCoord) {
      return tileCoord*16;
   }
}
