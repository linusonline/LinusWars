package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.core.game.LogicalUnit;
import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Linus on 2014-09-29.
 */
public class StateSelectUnloadPosition implements GameState {

   private final GameStateContext _context;

   private final LogicalUnit _logicalUnit;
   private final MovementArrow _movementArrow;
   private final List<Position> _candidatePositions;
   private final GameState _previousState;
   private int _currentlySelectedPositionIndex;

   // TODO: Do not suggest illegal positions!

   public StateSelectUnloadPosition(GameStateContext context,
                                    LogicalUnit logicalUnit,
                                    MovementArrow movementArrow,
                                    Set<Position> vacantPositions,
                                    GameState previousState) {
      _context = context;
      _logicalUnit = logicalUnit;
      _movementArrow = movementArrow;
      _candidatePositions = new ArrayList<>(vacantPositions);
      _previousState = previousState;
      _currentlySelectedPositionIndex = 0;
      _context.interactiveWarGame.showAttackCursorOnPosition(getSelectedPosition());
   }

   private Position getSelectedPosition() {
      return _candidatePositions.get(_currentlySelectedPositionIndex);
   }

   private LogicalUnit getUnloadingUnit() {
      // TODO: Proper unit selection state
      return _context.warGameQueries.getTransportedUnits(_logicalUnit).get(0);
   }

   @Override
   public GameState handleExecuteDown() {
      LogicalUnit unloadingUnit = getUnloadingUnit();
      Position unloadingPosition = _candidatePositions.get(_currentlySelectedPositionIndex);
      _context.warGameMoves.executeUnloadMove(_logicalUnit, unloadingUnit, _movementArrow.getPath(), unloadingPosition);
      _context.interactiveWarGame.stopIndicatingPositions();
      _context.interactiveWarGame.hideAttackCursor();
      _context.interactiveWarGame.showGraphicForUnit(unloadingUnit);
      _context.interactiveWarGame.setPositionOfGraphicForUnit(unloadingUnit, unloadingPosition);
      return new StateStarting(_context);
   }

   @Override
   public GameState handleExecuteUp() {
      return this;
   }

   @Override
   public GameState handleCancel() {
      _context.interactiveWarGame.hideAttackCursor();
      return _previousState;
   }

   @Override
   public GameState handleDirection(Direction direction) {
      int indexStep;
      switch (direction) {
         case LEFT:
         case UP:
            indexStep = -1;
            break;
         case RIGHT:
         case DOWN:
         default:
            indexStep = 1;
      }
      _currentlySelectedPositionIndex += indexStep;
      if (_currentlySelectedPositionIndex < 0) {
         _currentlySelectedPositionIndex += _candidatePositions.size();
      } else if (_currentlySelectedPositionIndex >= _candidatePositions.size()) {
         _currentlySelectedPositionIndex -= _candidatePositions.size();
      }
      _context.interactiveWarGame.showAttackCursorOnPosition(getSelectedPosition());
      return this;
   }

   @Override
   public GameState update() {
      return this;
   }

   @Override
   public void init(Sprites sprites) {

   }

   @Override
   public void draw(GameContainer gc, Font font, int x, int y) {
      _context.interactiveWarGame.draw(gc, 0, 0);
   }
}
