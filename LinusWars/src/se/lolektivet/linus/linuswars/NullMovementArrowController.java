package se.lolektivet.linus.linuswars;

import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.graphicalgame.TileView;
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
   public boolean canExtendMovementArrowToPosition(Position position) {
      return false;
   }

   @Override
   public boolean canExtendMovementArrowToCursorPosition() {
      return false;
   }

   @Override
   public void draw(int x, int y, TileView tileView) {
   }
}
