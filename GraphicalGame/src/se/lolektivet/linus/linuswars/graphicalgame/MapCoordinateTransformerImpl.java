package se.lolektivet.linus.linuswars.graphicalgame;

import se.lolektivet.linus.linuswars.logic.Position;

/**
 * Created by Linus on 2014-09-19.
 */
public class MapCoordinateTransformerImpl implements MapCoordinateTransformer {
   @Override
   public int transform(int mapCoord) {
      return mapCoord*16;
   }

   @Override
   public Position transform(Position position) {
      return new Position(transform(position.getX()), transform(position.getY()));
   }
}
