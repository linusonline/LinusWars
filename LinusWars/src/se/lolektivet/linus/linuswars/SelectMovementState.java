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
   private final LogicalWarGame _logicalWarGame;
   private final LogicalUnit _logicalUnit;
   private Collection<Position> _reachablePositions;
   private MovementArrow _movementArrow;
   private final MovementArrowController _movementArrowController;

   public SelectMovementState(
         InteractiveWarGame interactiveWarGame,
         LogicalWarGame logicalWarGame,
         LogicalUnit logicalUnit,
         MovementArrow movementArrow) {
      _interactiveWarGame = interactiveWarGame;
      _logicalWarGame = logicalWarGame;
      _logicalUnit = logicalUnit;
      _movementArrow = movementArrow;
      _movementArrowController = new MovementArrowController(_movementArrow, _logicalUnit, _logicalWarGame, _interactiveWarGame);
      _interactiveWarGame.setMovementArrowController(_movementArrowController);
      resetGraphicalUnit();
   }

   public SelectMovementState(
         InteractiveWarGame interactiveWarGame,
         LogicalWarGame logicalWarGame,
         LogicalUnit logicalUnit) {
      this(interactiveWarGame, logicalWarGame, logicalUnit, new MovementArrow(logicalWarGame.getPositionOfUnit(logicalUnit)));
   }

   private void resetGraphicalUnit() {
      _interactiveWarGame.setPositionOfGraphicForUnit(_logicalUnit, _logicalWarGame.getPositionOfUnit(_logicalUnit));
   }

   @Override
   public InteractiveGameState handleExecuteDown() {
      if (canDoMove()) {
         // TODO: Animate travel.
         _interactiveWarGame.setPositionOfGraphicForUnit(_logicalUnit, _movementArrow.getFinalPosition());
         _interactiveWarGame.setMovementArrowController(null);
         return new ActionMenuState(_interactiveWarGame, _logicalWarGame, _logicalUnit, _movementArrow);
      } else if (canDoLoadMove()) {
         _interactiveWarGame.setPositionOfGraphicForUnit(_logicalUnit, _movementArrow.getFinalPosition());
         _interactiveWarGame.setMovementArrowController(null);
         return new LoadMenuState(_interactiveWarGame, _logicalWarGame, _logicalUnit, _movementArrow);
      } else {
         return this;
      }
   }

   private boolean canDoMove() {
      return canMoveAtAll() &&
            (destinationIsVacant() ||
                  pathIsEmpty());
   }

   private boolean canDoLoadMove() {
      return canMoveAtAll() &&
            (!destinationIsVacant() &&
                  canLoadOntoUnitAtDestination());
   }

   private boolean destinationIsVacant() {
      return !_logicalWarGame.hasUnitAtPosition(_interactiveWarGame.getCursorPosition());
   }

   private boolean pathIsEmpty() {
      return _movementArrow.isEmpty();
   }

   private boolean canLoadOntoUnitAtDestination() {
      return _logicalWarGame.canLoadOnto(_logicalUnit, _logicalWarGame.getUnitAtPosition(_interactiveWarGame.getCursorPosition()));
   }

   private boolean canMoveAtAll() {
      return unitIsMovable() &&
            destinationIsReachable();
   }

   private boolean unitIsMovable() {
      return _logicalWarGame.getCurrentlyActiveFaction().equals(_logicalWarGame.getFactionForUnit(_logicalUnit));
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
      return new StartingState(_interactiveWarGame, _logicalWarGame);
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
            Path optimalPath = _logicalWarGame.getOptimalPathForUnitToDestination(_logicalUnit, newCursorPosition);
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
      _reachablePositions = _logicalWarGame.getAllReachablePoints(_logicalUnit);
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
