package se.lolektivet.linus.linuswars;

import se.lolektivet.linus.linuswars.graphicalgame.MapCoordinateTransformer;
import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.logic.LogicalUnit;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.WarGameQueries;

import java.util.Collection;

/**
 * Created by Linus on 2014-09-25.
 */
public class MovementArrowController {
   private MovementArrow _movementArrow;
   private final LogicalUnit _movingUnit;
   private WarGameQueries _warGameQueries;
   private final InteractiveWarGame _interactiveWarGame;
   private Sprites _sprites;

   public MovementArrowController(MovementArrow movementArrow, LogicalUnit movingUnit, WarGameQueries warGameQueries, InteractiveWarGame interactiveWarGame) {
      _movementArrow = movementArrow;
      _movingUnit = movingUnit;
      _warGameQueries = warGameQueries;
      _interactiveWarGame = interactiveWarGame;
   }

   public void init(Sprites sprites) {
      _sprites = sprites;
   }

   public void setMovementArrow(MovementArrow movementArrow) {
      _movementArrow = movementArrow;
   }

   public boolean canExtendMovementArrowToCursorPosition() {
      Position cursorPosition = _interactiveWarGame.getCursorPosition();
      Collection<Position> positionsAdjacentToArrowHead =
            _warGameQueries.getAdjacentPositions(_movementArrow.getFinalPosition());
      if (!positionsAdjacentToArrowHead.contains(cursorPosition)) {
         return false;
      }
      if (!_movementArrow.isEmpty()) {
         Position backtrackPosition = _movementArrow.getBacktrackPosition();
         if (cursorPosition.equals(backtrackPosition)) {
            return true;
         }
      }
      if (positionIsOnArrowPath(cursorPosition)) {
         return false;
      }
      return _warGameQueries.isPathAllowedForUnit(_movementArrow.getPath(), _movingUnit);
   }

   private boolean positionIsOnArrowPath(Position position) {
      return _movementArrow.getPath().getPositionList().contains(position) ||
            _movementArrow.getPath().getOrigin().equals(position);
   }

   public void draw(int x, int y, MapCoordinateTransformer coordinateTransformer) {
      for (int i = 0; i < _movementArrow.getLength(); i++) {
         _sprites.getMovementArrowSection(_movementArrow.getSection(i)).draw(
               x + coordinateTransformer.transform(_movementArrow.getPosition(i).getX()),
               y + coordinateTransformer.transform(_movementArrow.getPosition(i).getY()));
      }
   }
}
