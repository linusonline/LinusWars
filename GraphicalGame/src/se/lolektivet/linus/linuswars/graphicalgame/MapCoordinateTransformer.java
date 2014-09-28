package se.lolektivet.linus.linuswars.graphicalgame;

import se.lolektivet.linus.linuswars.logic.Position;

/**
 * Created by Linus on 2014-09-19.
 */
public interface MapCoordinateTransformer {
   int transform(int mapCoord);
   Position transform(Position position);
}
