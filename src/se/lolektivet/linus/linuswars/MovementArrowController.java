package se.lolektivet.linus.linuswars;

import se.lolektivet.linus.linuswars.graphicalgame.MapCoordinateTransformer;
import se.lolektivet.linus.linuswars.graphics.MovementArrowSprites;
import se.lolektivet.linus.linuswars.graphics.ResourceLoader;
import se.lolektivet.linus.linuswars.logic.LogicalUnit;
import se.lolektivet.linus.linuswars.logic.LogicalWarGame;
import se.lolektivet.linus.linuswars.logic.Position;

import java.util.Collection;

/**
 * Created by Linus on 2014-09-25.
 */
public class MovementArrowController {
   private MovementArrow _movementArrow;
   private final LogicalUnit _movingUnit;
   private final LogicalWarGame _logicalWarGame;
   private final InteractiveWarGame _interactiveWarGame;
   private MovementArrowSprites _movementArrowSprites;

   public MovementArrowController(MovementArrow movementArrow, LogicalUnit movingUnit, LogicalWarGame logicalWarGame, InteractiveWarGame interactiveWarGame) {
      _movementArrow = movementArrow;
      _movingUnit = movingUnit;
      _logicalWarGame = logicalWarGame;
      _interactiveWarGame = interactiveWarGame;
   }

   public void init(ResourceLoader resourceLoader) {
      _movementArrowSprites = new MovementArrowSprites();
      _movementArrowSprites.init(resourceLoader);
   }

   public void setMovementArrow(MovementArrow movementArrow) {
      _movementArrow = movementArrow;
   }

   public boolean canExtendMovementArrowToCursorPosition() {
      Position cursorPosition = _interactiveWarGame.getCursorPosition();
      Collection<Position> positionsAdjacentToArrowHead =
            _logicalWarGame.getAdjacentPositions(_movementArrow.getFinalPosition());
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
      return _logicalWarGame.isPathAllowedForUnit(_movementArrow.getPath(), _movingUnit);
   }

   private boolean positionIsOnArrowPath(Position position) {
      return _movementArrow.getPath().getPositionList().contains(position) ||
            _movementArrow.getPath().getOrigin().equals(position);
   }

   public void draw(int x, int y, MapCoordinateTransformer coordinateTransformer) {
      for (int i = 0; i < _movementArrow.getLength(); i++) {
         _movementArrowSprites.getArrowSection(_movementArrow.getSection(i)).draw(
               x + coordinateTransformer.transform(_movementArrow.getPosition(i).getX()),
               y + coordinateTransformer.transform(_movementArrow.getPosition(i).getY()));
      }
   }
}
