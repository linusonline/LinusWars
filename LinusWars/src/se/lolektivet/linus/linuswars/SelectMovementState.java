package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import se.lolektivet.linus.linuswars.graphics.ResourceLoader;
import se.lolektivet.linus.linuswars.logic.*;
import se.lolektivet.linus.linuswars.logic.enums.Direction;
import se.lolektivet.linus.linuswars.logic.pathfinding.Path;

import java.util.Collection;

/**
 * Created by Linus on 2014-09-20.
 */
public class SelectMovementState implements InteractiveGameState {
   private final InteractiveWarGame _interactiveWarGame;
   private final WarGameQueries _warGameQueries;
   private final WarGameMoves _warGameMoves;
   private final LogicalUnit _logicalUnit;
   private Collection<Position> _reachablePositions;
   private MovementArrow _movementArrow;
   private final MovementArrowController _movementArrowController;

   public SelectMovementState(
         InteractiveWarGame interactiveWarGame,
         WarGameQueries warGameQueries, WarGameMoves warGameMoves,
         LogicalUnit logicalUnit,
         MovementArrow movementArrow) {
      _interactiveWarGame = interactiveWarGame;
      _warGameQueries = warGameQueries;
      _warGameMoves = warGameMoves;
      _logicalUnit = logicalUnit;
      _movementArrow = movementArrow;
      _movementArrowController = new MovementArrowController(_movementArrow, _logicalUnit, _warGameQueries, _interactiveWarGame);
      _interactiveWarGame.setMovementArrowController(_movementArrowController);
      resetGraphicalUnit();
   }

   public SelectMovementState(
         InteractiveWarGame interactiveWarGame,
         WarGameQueries warGameQueries, WarGameMoves warGameMoves,
         LogicalUnit logicalUnit) {
      this(interactiveWarGame, warGameQueries, warGameMoves, logicalUnit, new MovementArrow(warGameQueries.getPositionOfUnit(logicalUnit)));
   }

   private void resetGraphicalUnit() {
      _interactiveWarGame.setPositionOfGraphicForUnit(_logicalUnit, _warGameQueries.getPositionOfUnit(_logicalUnit));
   }

   @Override
   public InteractiveGameState handleExecuteDown() {
      if (canDoMove()) {
         // TODO: Animate travel.
         _interactiveWarGame.setPositionOfGraphicForUnit(_logicalUnit, _movementArrow.getFinalPosition());
         _interactiveWarGame.setMovementArrowController(null);
         return new ActionMenuState(_interactiveWarGame, _warGameQueries, _warGameMoves, _logicalUnit, _movementArrow);
      } else if (canDoLoadMove()) {
         _interactiveWarGame.setPositionOfGraphicForUnit(_logicalUnit, _movementArrow.getFinalPosition());
         _interactiveWarGame.setMovementArrowController(null);
         return new LoadMenuState(_interactiveWarGame, _warGameQueries, _warGameMoves, _logicalUnit, _movementArrow);
      } else if (canDoJoinMove()) {
         _interactiveWarGame.setPositionOfGraphicForUnit(_logicalUnit, _movementArrow.getFinalPosition());
         _interactiveWarGame.setMovementArrowController(null);
         return new JoinMenuState(_interactiveWarGame, _warGameQueries, _warGameMoves, _logicalUnit, _movementArrow);
      } else {
         return this;
      }
   }

   private boolean canDoMove() {
      return canMoveAtAll() &&
            (destinationIsVacant() ||
                  selfSelected());
   }

   private boolean canDoLoadMove() {
      return canMoveAtAll() &&
            (!destinationIsVacant() &&
                  canLoadOntoUnitAtDestination());
   }

   private boolean canDoJoinMove() {
      return canMoveAtAll() &&
            (!destinationIsVacant() &&
            canJoinWithUnitAtDestination());
   }

   private boolean destinationIsVacant() {
      return !_warGameQueries.hasUnitAtPosition(_interactiveWarGame.getCursorPosition());
   }

   private boolean selfSelected() {
      return _movementArrow.isEmpty();
   }

   private boolean canLoadOntoUnitAtDestination() {
      return _warGameQueries.canLoadOnto(_logicalUnit, _warGameQueries.getUnitAtPosition(_interactiveWarGame.getCursorPosition()));
   }

   private boolean canJoinWithUnitAtDestination() {
      return _warGameQueries.canJoinWith(_logicalUnit, _warGameQueries.getUnitAtPosition(_interactiveWarGame.getCursorPosition()));
   }

   private boolean canMoveAtAll() {
      return unitIsMovable() &&
            destinationIsReachable();
   }

   private boolean unitIsMovable() {
      return _warGameQueries.getCurrentlyActiveFaction().equals(_warGameQueries.getFactionForUnit(_logicalUnit));
   }

   private boolean destinationIsReachable() {
      return _reachablePositions.contains(_interactiveWarGame.getCursorPosition());
   }

   @Override
   public InteractiveGameState handleExecuteUp() {
      return this;
   }

   @Override
   public InteractiveGameState handleCancel() {
      _interactiveWarGame.stopIndicatingPositions();
      _interactiveWarGame.setMovementArrowController(null);
      return new StartingState(_interactiveWarGame, _warGameQueries, _warGameMoves);
   }

   @Override
   public InteractiveGameState handleDirection(Direction direction) {
      try {
         _interactiveWarGame.moveCursor(direction);
         Position newCursorPosition = _interactiveWarGame.getCursorPosition();
         if (_movementArrow.getFinalPosition().equals(newCursorPosition)) {
            return this;
         }
         if (!_reachablePositions.contains(newCursorPosition)) {
            return this;
         }
         if (_movementArrowController.canExtendMovementArrowToCursorPosition()) {
            _movementArrow.addPoint(newCursorPosition);
         } else {
            Path optimalPath = _warGameQueries.getOptimalPathForUnitToDestination(_logicalUnit, newCursorPosition);
            _movementArrow = new MovementArrow(optimalPath);
            _movementArrow.build();
            _movementArrowController.setMovementArrow(_movementArrow);
         }
      } catch (InteractiveWarGame.CursorOutsideMapException ignored) {
      }
      return this;
   }

   @Override
   public void setResourceLoader(ResourceLoader loader) {
      _reachablePositions = _warGameQueries.getAllReachablePoints(_logicalUnit);
      _interactiveWarGame.indicateSelectedPositions(_reachablePositions);
      _movementArrowController.init(loader);
   }

   @Override
   public String toString() {
      return "Select Movement State";
   }

   @Override
   public void draw(Graphics graphics, Font font, int x, int y) {
      _interactiveWarGame.draw(graphics, font, 0, 0);
   }
}
