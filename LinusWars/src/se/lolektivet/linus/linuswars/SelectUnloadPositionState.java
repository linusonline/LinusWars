package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import se.lolektivet.linus.linuswars.graphics.ResourceLoader;
import se.lolektivet.linus.linuswars.logic.enums.Direction;
import se.lolektivet.linus.linuswars.logic.LogicalUnit;
import se.lolektivet.linus.linuswars.logic.LogicalWarGame;
import se.lolektivet.linus.linuswars.logic.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Linus on 2014-09-29.
 */
public class SelectUnloadPositionState implements InteractiveGameState {
   private final InteractiveWarGame _interactiveWarGame;
   private final LogicalWarGame _logicalWarGame;
   private final LogicalUnit _logicalUnit;
   private final MovementArrow _movementArrow;
   private final List<Position> _candidatePositions;
   private int _currentlySelectedPositionIndex;

   public SelectUnloadPositionState(InteractiveWarGame interactiveWarGame, LogicalWarGame logicalWarGame, LogicalUnit logicalUnit, MovementArrow movementArrow, Set<Position> vacantPositions) {
      _interactiveWarGame = interactiveWarGame;
      _logicalWarGame = logicalWarGame;
      _logicalUnit = logicalUnit;
      _movementArrow = movementArrow;
      _candidatePositions = new ArrayList<Position>(vacantPositions);
      _currentlySelectedPositionIndex = 0;
      _interactiveWarGame.showAttackCursorOnPosition(getSelectedPosition());
   }

   private Position getSelectedPosition() {
      return _candidatePositions.get(_currentlySelectedPositionIndex);
   }

   private LogicalUnit getUnloadingUnit() {
      // TODO: Proper unit selection state
      return _logicalWarGame.getTransportedUnits(_logicalUnit).get(0);
   }

   @Override
   public InteractiveGameState handleExecuteDown() {
      LogicalUnit unloadingUnit = getUnloadingUnit();
      Position unloadingPosition = _candidatePositions.get(_currentlySelectedPositionIndex);
      _logicalWarGame.executeUnloadMove(_logicalUnit, unloadingUnit, _movementArrow.getPath(), unloadingPosition);
      _interactiveWarGame.stopIndicatingPositions();
      _interactiveWarGame.hideAttackCursor();
      _interactiveWarGame.showGraphicForUnit(unloadingUnit);
      _interactiveWarGame.setPositionOfGraphicForUnit(unloadingUnit, unloadingPosition);
      return new StartingState(_interactiveWarGame, _logicalWarGame);
   }

   @Override
   public InteractiveGameState handleExecuteUp() {
      return this;
   }

   @Override
   public InteractiveGameState handleCancel() {
      _interactiveWarGame.hideAttackCursor();
      return new ActionMenuState(_interactiveWarGame, _logicalWarGame, _logicalUnit, _movementArrow);
   }

   @Override
   public InteractiveGameState handleDirection(Direction direction) {
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
      _interactiveWarGame.showAttackCursorOnPosition(getSelectedPosition());
      return this;
   }

   @Override
   public void setResourceLoader(ResourceLoader loader) {

   }

   @Override
   public void draw(Graphics g, Font font, int x, int y) {
      _interactiveWarGame.draw(g, font, 0, 0);
   }
}