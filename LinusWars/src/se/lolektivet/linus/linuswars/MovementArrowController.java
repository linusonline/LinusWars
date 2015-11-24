package se.lolektivet.linus.linuswars;

import se.lolektivet.linus.linuswars.graphicalgame.MapCoordinateTransformer;
import se.lolektivet.linus.linuswars.graphics.Sprites;

/**
 * Created by Linus on 2015-11-24.
 */
public interface MovementArrowController {
   void init(Sprites sprites);

   void setMovementArrow(MovementArrow movementArrow);

   boolean canExtendMovementArrowToCursorPosition();

   void draw(int x, int y, MapCoordinateTransformer coordinateTransformer);
}
