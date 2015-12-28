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
public class StateStarting implements GameState {

   private final GameStateContext _context;
   private boolean _executeHasBeenPressed = false;

   public StateStarting(GameStateContext context) {
      _context = context;
   }

   @Override
   public GameState handleExecuteDown() {
      _executeHasBeenPressed = true;
      if (_context.warGameQueries.hasActiveUnitAtPosition(_context.interactiveWarGame.getCursorPosition())) {
         LogicalUnit logicalUnit = _context.warGameQueries.getUnitAtPosition(_context.interactiveWarGame.getCursorPosition());
         return new StateSelectMove(_context, logicalUnit);
      } else {
         return new StateQuickMenu(_context, this);
      }
   }

   @Override
   public GameState handleExecuteUp() {
      if (!_executeHasBeenPressed) {
         return this;
      }
      _executeHasBeenPressed = false;
      return this;
   }

   @Override
   public GameState handleCancel() {
      return this;
   }

   @Override
   public GameState handleDirection(Direction direction) {
      try {
         _context.interactiveWarGame.moveCursor(direction);
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

   }

   @Override
   public String toString() {
      return "Starting State";
   }

   @Override
   public void draw(GameContainer gc, Font font, int x, int y) {
      _context.interactiveWarGame.draw(gc, 0, 0);
   }
}
