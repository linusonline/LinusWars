package se.lolektivet.linus.linuswars;

import se.lolektivet.linus.linuswars.graphicalgame.TileView;
import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.logic.game.LogicalUnit;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.game.WarGameQueries;

import java.util.Collection;

/**
 * Created by Linus on 2014-09-25.
 */
public class MovementArrowControllerImpl implements MovementArrowController {
   private MovementArrow _movementArrow;
   private final LogicalUnit _movingUnit;
   private WarGameQueries _warGameQueries;
   private final InteractiveWarGame _interactiveWarGame;
   private Sprites _sprites;

   public MovementArrowControllerImpl(MovementArrow movementArrow, LogicalUnit movingUnit, WarGameQueries warGameQueries, InteractiveWarGame interactiveWarGame) {
      _movementArrow = movementArrow;
      _movingUnit = movingUnit;
      _warGameQueries = warGameQueries;
      _interactiveWarGame = interactiveWarGame;
   }

   @Override
   public void init(Sprites sprites) {
      _sprites = sprites;
   }

   @Override
   public void setMovementArrow(MovementArrow movementArrow) {
      _movementArrow = movementArrow;
   }

   @Override
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

   @Override
   public void draw(int x, int y, TileView tileView) {
      for (int i = 0; i < _movementArrow.getLength(); i++) {
         _sprites.getMovementArrowSection(_movementArrow.getSection(i)).draw(
               x + tileView.tileToPixelX(_movementArrow.getPosition(i).getX()),
               y + tileView.tileToPixelY(_movementArrow.getPosition(i).getY()));
      }
   }
}
