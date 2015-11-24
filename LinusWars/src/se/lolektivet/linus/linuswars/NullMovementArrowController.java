package se.lolektivet.linus.linuswars;

import se.lolektivet.linus.linuswars.graphicalgame.MapCoordinateTransformer;
import se.lolektivet.linus.linuswars.graphics.Sprites;

/**
 * Created by Linus on 2015-11-24.
 */
public class NullMovementArrowController implements MovementArrowController {
   public NullMovementArrowController() {
   }

   @Override
   public void init(Sprites sprites) {
   }

   @Override
   public void setMovementArrow(MovementArrow movementArrow) {
   }

   @Override
   public boolean canExtendMovementArrowToCursorPosition() {
      return false;
   }

   @Override
   public void draw(int x, int y, MapCoordinateTransformer coordinateTransformer) {
   }
}
