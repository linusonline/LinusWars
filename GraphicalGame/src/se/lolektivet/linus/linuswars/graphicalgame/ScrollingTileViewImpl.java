package se.lolektivet.linus.linuswars.graphicalgame;


import se.lolektivet.linus.linuswars.core.Position;

/**
 * Created by Linus on 2015-11-24.
 */
public class ScrollingTileViewImpl extends TileViewImpl implements ScrollingTileView {

   private static final int HORIZONTAL_CURSOR_MARGIN = 4;
   private static final int VERTICAL_CURSOR_MARGIN = 4;

   @Override
   public void cursorMoved(Position newPosition, int mapWidth, int mapHeight) {
      Position oldOffset = getTileOffset();
      int newOffsetX = oldOffset.getX();
      int newOffsetY = oldOffset.getY();

      int rectHeight = getVisibleTileHeight();
      int rectWidth = getVisibleTileWidth();

      if (newPosition.getX() < oldOffset.getX() + HORIZONTAL_CURSOR_MARGIN) {
         newOffsetX = Math.max(0, newPosition.getX() - HORIZONTAL_CURSOR_MARGIN);
      } else if (newPosition.getX() >= oldOffset.getX() + rectWidth - HORIZONTAL_CURSOR_MARGIN) {
         newOffsetX = Math.min(mapWidth - rectWidth, newPosition.getX() + HORIZONTAL_CURSOR_MARGIN + 1 - rectWidth);
      }
      if (newPosition.getY() < oldOffset.getY() + VERTICAL_CURSOR_MARGIN) {
         newOffsetY = Math.max(0, newPosition.getY() - VERTICAL_CURSOR_MARGIN);
      } else if (newPosition.getY() >= oldOffset.getY() + rectHeight - VERTICAL_CURSOR_MARGIN) {
         newOffsetY = Math.min(mapHeight - rectHeight, newPosition.getY() + VERTICAL_CURSOR_MARGIN + 1 - rectHeight);
      }
      setTileOffset(newOffsetX, newOffsetY);
   }

   @Override
   public int getVisibleTileOffsetX() {
      return getTileOffset().getX();
   }

   @Override
   public int getVisibleTileOffsetY() {
      return getTileOffset().getY();
   }
}
