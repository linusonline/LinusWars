package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.logic.enums.Direction;
import se.lolektivet.linus.linuswars.logic.game.WarGameMoves;
import se.lolektivet.linus.linuswars.logic.game.WarGameQueries;

/**
 * Created by Linus on 2015-12-23.
 */
public class StateTurnTransition implements GameState {
   private final InteractiveWarGame _interactiveWarGame;
   private final WarGameQueries _warGameQueries;
   private final WarGameMoves _warGameMoves;

   public StateTurnTransition(InteractiveWarGame interactiveWarGame, WarGameQueries warGameQueries, WarGameMoves warGameMoves) {
      _interactiveWarGame = interactiveWarGame;
      _warGameQueries = warGameQueries;
      _warGameMoves = warGameMoves;
   }

   @Override
   public GameState handleExecuteDown() {
      return this;
   }

   @Override
   public GameState handleExecuteUp() {
      return this;
   }

   @Override
   public GameState handleCancel() {
      return this;
   }

   @Override
   public GameState handleDirection(Direction direction) {
      return this;
   }

   @Override
   public GameState update() {
      // TODO: This should probably be based on real time, and ignore the number of calls.
      if (++steps > 2000) {
         return new StateStarting(_interactiveWarGame, _warGameQueries, _warGameMoves);
      } else {
         return this;
      }
   }

   @Override
   public void init(Sprites sprites) {

   }

   private int steps = 0;
   @Override
   public void draw(GameContainer gc, Font font, int x, int y) {
      int i = _warGameQueries.getDayNumber();
      _interactiveWarGame.draw(gc, 0, 0);
      font.drawString(100, 100, "Day " + i);
//      gc.getGraphics().drawString("Day " + i, 100, 100);
   }
}
