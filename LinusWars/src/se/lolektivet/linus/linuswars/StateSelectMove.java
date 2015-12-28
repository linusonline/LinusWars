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

   private final GameStateContext _context;

   private final LogicalUnit _logicalUnit;
   private Collection<Position> _reachablePositions;
   private MovementArrow _movementArrow;
   private final MovementArrowController _movementArrowController;

   public StateSelectMove(
         GameStateContext context,
         LogicalUnit logicalUnit,
         MovementArrow movementArrow) {
      _context = context;
      _logicalUnit = logicalUnit;
      _movementArrow = movementArrow;
      _movementArrowController = new MovementArrowControllerImpl(_movementArrow, _logicalUnit, _context.warGameQueries, _context.interactiveWarGame);
      _context.interactiveWarGame.setMovementArrowController(_movementArrowController);
      resetGraphicalUnit();
   }

   public StateSelectMove(
         GameStateContext context,
         LogicalUnit logicalUnit) {
      this(context, logicalUnit, new MovementArrow(context.warGameQueries.getPositionOfUnit(logicalUnit)));
   }

   private void resetGraphicalUnit() {
      _context.interactiveWarGame.resetUnitGraphicToUnitPosition(_logicalUnit);
   }

   @Override
   public GameState handleExecuteDown() {
      MoveAnalyzer moveAnalyzer = new MoveAnalyzer(_context.warGameQueries, _logicalUnit, _movementArrow.getPath());
      moveAnalyzer.analyze();
      if (moveAnalyzer.canDoSomeMove()) {
         // TODO: Animate travel.
         _context.interactiveWarGame.setPositionOfGraphicForUnit(_logicalUnit, _movementArrow.getFinalPosition());
         _context.interactiveWarGame.hideMovementArrow();
         return new StateActionMenu(_context, _logicalUnit, _movementArrow, moveAnalyzer);
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
      _context.interactiveWarGame.stopIndicatingPositions();
      _context.interactiveWarGame.hideMovementArrow();
      return new StateStarting(_context);
   }

   @Override
   public GameState handleDirection(Direction direction) {
      try {
         _context.interactiveWarGame.moveCursor(direction);
         Position newCursorPosition = _context.interactiveWarGame.getCursorPosition();
         if (_movementArrow.getFinalPosition().equals(newCursorPosition)) {
            return this;
         }
         if (!_reachablePositions.contains(newCursorPosition)) {
            return this;
         }
         if (_movementArrowController.canExtendMovementArrowToCursorPosition()) {
            _movementArrow.addPoint(newCursorPosition);
         } else {
            Path optimalPath = _context.warGameQueries.getOptimalPathForUnitToDestination(_logicalUnit, newCursorPosition);
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
      _reachablePositions = _context.warGameQueries.getAllReachablePoints(_logicalUnit);
      _context.interactiveWarGame.indicateSelectedPositions(_reachablePositions);
      _movementArrowController.init(sprites);
   }

   @Override
   public String toString() {
      return "Select Movement State";
   }

   @Override
   public void draw(GameContainer gc, Font font, int x, int y) {
      _context.interactiveWarGame.draw(gc, 0, 0);
   }
}
