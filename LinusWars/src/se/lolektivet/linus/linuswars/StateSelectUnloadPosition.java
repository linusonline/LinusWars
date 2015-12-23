package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.logic.game.LogicalUnit;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.game.WarGameMoves;
import se.lolektivet.linus.linuswars.logic.game.WarGameQueries;
import se.lolektivet.linus.linuswars.logic.enums.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Linus on 2014-09-29.
 */
public class StateSelectUnloadPosition implements InteractiveGameState {
   private final InteractiveWarGame _interactiveWarGame;
   private final WarGameQueries _warGameQueries;
   private final WarGameMoves _warGameMoves;
   private final LogicalUnit _logicalUnit;
   private final MovementArrow _movementArrow;
   private final List<Position> _candidatePositions;
   private final InteractiveGameState _previousState;
   private int _currentlySelectedPositionIndex;

   public StateSelectUnloadPosition(InteractiveWarGame interactiveWarGame,
                                    WarGameQueries warGameQueries,
                                    WarGameMoves warGameMoves,
                                    LogicalUnit logicalUnit,
                                    MovementArrow movementArrow,
                                    Set<Position> vacantPositions,
                                    InteractiveGameState previousState) {
      _interactiveWarGame = interactiveWarGame;
      _warGameQueries = warGameQueries;
      _warGameMoves = warGameMoves;
      _logicalUnit = logicalUnit;
      _movementArrow = movementArrow;
      _candidatePositions = new ArrayList<>(vacantPositions);
      _previousState = previousState;
      _currentlySelectedPositionIndex = 0;
      _interactiveWarGame.showAttackCursorOnPosition(getSelectedPosition());
   }

   private Position getSelectedPosition() {
      return _candidatePositions.get(_currentlySelectedPositionIndex);
   }

   private LogicalUnit getUnloadingUnit() {
      // TODO: Proper unit selection state
      return _warGameQueries.getTransportedUnits(_logicalUnit).get(0);
   }

   @Override
   public InteractiveGameState handleExecuteDown() {
      LogicalUnit unloadingUnit = getUnloadingUnit();
      Position unloadingPosition = _candidatePositions.get(_currentlySelectedPositionIndex);
      _warGameMoves.executeUnloadMove(_logicalUnit, unloadingUnit, _movementArrow.getPath(), unloadingPosition);
      _interactiveWarGame.stopIndicatingPositions();
      _interactiveWarGame.hideAttackCursor();
      _interactiveWarGame.showGraphicForUnit(unloadingUnit);
      _interactiveWarGame.setPositionOfGraphicForUnit(unloadingUnit, unloadingPosition);
      return new StateTurnTransition(_interactiveWarGame, _warGameQueries, _warGameMoves);
   }

   @Override
   public InteractiveGameState handleExecuteUp() {
      return this;
   }

   @Override
   public InteractiveGameState handleCancel() {
      _interactiveWarGame.hideAttackCursor();
      return _previousState;
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
   public InteractiveGameState update() {
      return this;
   }

   @Override
   public void setSprites(Sprites sprites) {

   }

   @Override
   public void draw(GameContainer gc, Font font, int x, int y) {
      _interactiveWarGame.draw(gc, 0, 0);
   }
}
