package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import se.lolektivet.linus.linuswars.InteractiveGameState;
import se.lolektivet.linus.linuswars.InteractiveWarGame;
import se.lolektivet.linus.linuswars.StateStarting;
import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.logic.enums.Direction;
import se.lolektivet.linus.linuswars.logic.game.WarGameMoves;
import se.lolektivet.linus.linuswars.logic.game.WarGameQueries;

/**
 * Created by Linus on 2015-12-23.
 */
public class StateTurnTransition implements InteractiveGameState {
   private final InteractiveWarGame _interactiveWarGame;
   private final WarGameQueries _warGameQueries;
   private final WarGameMoves _warGameMoves;

   public StateTurnTransition(InteractiveWarGame interactiveWarGame, WarGameQueries warGameQueries, WarGameMoves warGameMoves) {
      _interactiveWarGame = interactiveWarGame;
      _warGameQueries = warGameQueries;
      _warGameMoves = warGameMoves;
   }

   @Override
   public InteractiveGameState handleExecuteDown() {
      return this;
   }

   @Override
   public InteractiveGameState handleExecuteUp() {
      return this;
   }

   @Override
   public InteractiveGameState handleCancel() {
      return this;
   }

   @Override
   public InteractiveGameState handleDirection(Direction direction) {
      return this;
   }

   @Override
   public InteractiveGameState update() {
      return new StateStarting(_interactiveWarGame, _warGameQueries, _warGameMoves);
   }

   @Override
   public void setSprites(Sprites sprites) {

   }

   @Override
   public void draw(GameContainer gc, Font font, int x, int y) {
      _interactiveWarGame.draw(gc, 0, 0);
   }
}
