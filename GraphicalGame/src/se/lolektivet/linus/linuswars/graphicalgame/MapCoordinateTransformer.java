package se.lolektivet.linus.linuswars.graphicalgame;

import se.lolektivet.linus.linuswars.logic.Position;

/**
 * Created by Linus on 2014-09-19.
 */
public interface MapCoordinateTransformer {
   int transformX(int mapCoord);
   int transformY(int mapCoord);
   Position transform(Position position);
   void setTileOffset(int x, int y);
   void setVisibleRectSize(int width, int height);
   boolean isVisible(int mapX, int mapY);
   void cursorMoved(Position newPosition, int mapWidth, int mapHeight);
}
