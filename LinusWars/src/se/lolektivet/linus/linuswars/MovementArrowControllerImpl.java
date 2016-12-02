package se.lolektivet.linus.linuswars;

import se.lolektivet.linus.linuswars.core.pathfinding.Path;
import se.lolektivet.linus.linuswars.graphicalgame.TileView;
import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.core.game.LogicalUnit;
import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.game.WarGameQueries;

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
   public boolean canExtendMovementArrowToPosition(Position position) {
      Collection<Position> positionsAdjacentToArrowHead =
            _warGameQueries.getAdjacentPositions(_movementArrow.getFinalPosition());
      if (!positionsAdjacentToArrowHead.contains(position)) {
         return false;
      }
      if (!_movementArrow.isEmpty()) {
         Position backtrackPosition = _movementArrow.getBacktrackPosition();
         if (position.equals(backtrackPosition)) {
            return true;
         }
      }
      if (positionIsOnArrowPath(position)) {
         return false;
      }
      Path newPath = new Path(_movementArrow.getPath());
      newPath.addPoint(position);
      return _warGameQueries.isPathAllowedForUnit(newPath, _movingUnit);
   }

   @Override
   public boolean canExtendMovementArrowToCursorPosition() {
      return canExtendMovementArrowToPosition(_interactiveWarGame.getCursorPosition());
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
