package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import se.lolektivet.linus.linuswars.graphics.ResourceLoader;
import se.lolektivet.linus.linuswars.logic.Direction;
import se.lolektivet.linus.linuswars.logic.LogicalUnit;
import se.lolektivet.linus.linuswars.logic.LogicalWarGame;

/**
 * Created by Linus on 2014-09-19.
 */
public class StartingState implements InteractiveGameState {
   private final InteractiveWarGame _interactiveWarGame;
   private final LogicalWarGame _logicalWarGame;
   private boolean _executeHasBeenPressed = false;

   public StartingState(InteractiveWarGame interactiveWarGame, LogicalWarGame logicalWarGame) {
      _interactiveWarGame = interactiveWarGame;
      _logicalWarGame = logicalWarGame;
   }

   @Override
   public InteractiveGameState handleExecuteDown() {
      _executeHasBeenPressed = true;
      if (_logicalWarGame.hasActiveUnitAtPosition(_interactiveWarGame.getCursorPosition())) {
         LogicalUnit logicalUnit = _logicalWarGame.getUnitAtPosition(_interactiveWarGame.getCursorPosition());
         return new SelectMovementState(_interactiveWarGame, _logicalWarGame, logicalUnit);
      } else {
         return new QuickMenuState(_interactiveWarGame, _logicalWarGame);
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
   public void setResourceLoader(ResourceLoader loader) {

   }

   @Override
   public String toString() {
      return "Starting State";
   }

   @Override
   public void draw(Graphics graphics, Font font, int x, int y) {
      _interactiveWarGame.draw(graphics, font, 0, 0);
   }
}
