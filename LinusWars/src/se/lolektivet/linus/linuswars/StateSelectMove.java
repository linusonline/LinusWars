package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.Direction;
import se.lolektivet.linus.linuswars.logic.game.LogicalUnit;
import se.lolektivet.linus.linuswars.logic.game.WarGameMoves;
import se.lolektivet.linus.linuswars.logic.game.WarGameQueries;
import se.lolektivet.linus.linuswars.logic.pathfinding.Path;

import java.util.Collection;

/**
 * Created by Linus on 2014-09-20.
 */
public class StateSelectMove implements GameState {
   private final InteractiveWarGame _interactiveWarGame;
   private final WarGameQueries _warGameQueries;
   private final WarGameMoves _warGameMoves;
   private final LogicalUnit _logicalUnit;
   private Collection<Position> _reachablePositions;
   private MovementArrow _movementArrow;
   private final MovementArrowController _movementArrowController;

   public StateSelectMove(
         InteractiveWarGame interactiveWarGame,
         WarGameQueries warGameQueries, WarGameMoves warGameMoves,
         LogicalUnit logicalUnit,
         MovementArrow movementArrow) {
      _interactiveWarGame = interactiveWarGame;
      _warGameQueries = warGameQueries;
      _warGameMoves = warGameMoves;
      _logicalUnit = logicalUnit;
      _movementArrow = movementArrow;
      _movementArrowController = new MovementArrowControllerImpl(_movementArrow, _logicalUnit, _warGameQueries, _interactiveWarGame);
      _interactiveWarGame.setMovementArrowController(_movementArrowController);
      resetGraphicalUnit();
   }

   public StateSelectMove(
         InteractiveWarGame interactiveWarGame,
         WarGameQueries warGameQueries, WarGameMoves warGameMoves,
         LogicalUnit logicalUnit) {
      this(interactiveWarGame, warGameQueries, warGameMoves, logicalUnit, new MovementArrow(warGameQueries.getPositionOfUnit(logicalUnit)));
   }

   private void resetGraphicalUnit() {
      _interactiveWarGame.resetUnitGraphicToUnitPosition(_logicalUnit);
   }

   @Override
   public GameState handleExecuteDown() {
      MoveAnalyzer moveAnalyzer = new MoveAnalyzer(_warGameQueries, _logicalUnit, _movementArrow.getPath());
      moveAnalyzer.analyze();
      if (moveAnalyzer.canDoSomeMove()) {
         // TODO: Animate travel.
         _interactiveWarGame.setPositionOfGraphicForUnit(_logicalUnit, _movementArrow.getFinalPosition());
         _interactiveWarGame.hideMovementArrow();
         return new StateActionMenu(_interactiveWarGame, _warGameQueries, _warGameMoves, _logicalUnit, _movementArrow, moveAnalyzer);
      } else {
         return this;
      }
   }

   @Override
   public GameState handleExecuteUp() {
      return this;
   }

   @Override
   public GameState handleCancel() {
      _interactiveWarGame.stopIndicatingPositions();
      _interactiveWarGame.hideMovementArrow();
      return new StateStarting(_interactiveWarGame, _warGameQueries, _warGameMoves);
   }

   @Override
   public GameState handleDirection(Direction direction) {
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
   public GameState update() {
      return this;
   }

   @Override
   public void init(Sprites sprites) {
      _reachablePositions = _warGameQueries.getAllReachablePoints(_logicalUnit);
      _interactiveWarGame.indicateSelectedPositions(_reachablePositions);
      _movementArrowController.init(sprites);
   }

   @Override
   public String toString() {
      return "Select Movement State";
   }

   @Override
   public void draw(GameContainer gc, Font font, int x, int y) {
      _interactiveWarGame.draw(gc, 0, 0);
   }
}
