package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.logic.game.LogicalUnit;
import se.lolektivet.linus.linuswars.logic.game.WarGameMoves;
import se.lolektivet.linus.linuswars.logic.game.WarGameQueries;
import se.lolektivet.linus.linuswars.logic.enums.Direction;

/**
 * Created by Linus on 2014-09-19.
 */
public class StateStarting implements InteractiveGameState {
   private final InteractiveWarGame _interactiveWarGame;
   private final WarGameQueries _warGameQueries;
   private final WarGameMoves _warGameMoves;
   private boolean _executeHasBeenPressed = false;

   public StateStarting(InteractiveWarGame interactiveWarGame, WarGameQueries warGameQueries, WarGameMoves warGameMoves) {
      _interactiveWarGame = interactiveWarGame;
      _warGameMoves = warGameMoves;
      _warGameQueries = warGameQueries;
   }

   @Override
   public InteractiveGameState handleExecuteDown() {
      _executeHasBeenPressed = true;
      if (_warGameQueries.hasActiveUnitAtPosition(_interactiveWarGame.getCursorPosition())) {
         LogicalUnit logicalUnit = _warGameQueries.getUnitAtPosition(_interactiveWarGame.getCursorPosition());
         return new StateSelectMove(_interactiveWarGame, _warGameQueries, _warGameMoves, logicalUnit);
      } else {
         return new StateQuickMenu(_interactiveWarGame, _warGameQueries, _warGameMoves);
      }
   }

   @Override
   public InteractiveGameState handleExecuteUp() {
      if (!_executeHasBeenPressed) {
         return this;
      }
      _executeHasBeenPressed = false;
      return this;
   }

   @Override
   public InteractiveGameState handleCancel() {
      return this;
   }

   @Override
   public InteractiveGameState handleDirection(Direction direction) {
      try {
         _interactiveWarGame.moveCursor(direction);
      } catch (InteractiveWarGame.CursorOutsideMapException ignored) {
      }
      return this;
   }

   @Override
   public void setSprites(Sprites sprites) {

   }

   @Override
   public String toString() {
      return "Starting State";
   }

   @Override
   public void draw(GameContainer gc, Font font, int x, int y) {
      _interactiveWarGame.draw(gc, 0, 0);
   }
}
