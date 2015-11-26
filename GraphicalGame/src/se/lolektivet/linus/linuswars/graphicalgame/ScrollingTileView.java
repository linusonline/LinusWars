package se.lolektivet.linus.linuswars.graphicalgame;

import se.lolektivet.linus.linuswars.logic.Position;

/**
 * Created by Linus on 2015-11-25.
 */
public interface ScrollingTileView extends TileView {
   void cursorMoved(Position newPosition, int mapWidth, int mapHeight);
   int getVisibleTileOffsetX();
   int getVisibleTileOffsetY();
}
