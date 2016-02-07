package se.lolektivet.linus.linuswars;

import se.lolektivet.linus.linuswars.logic.game.WarGameMoves;
import se.lolektivet.linus.linuswars.logic.game.WarGameQueries;

/**
 * Created by Linus on 2015-12-28.
 */
public class GameStateContext {
   public final InteractiveWarGame interactiveWarGame;
   public final WarGameQueries warGameQueries;
   public final WarGameMoves warGameMoves;

   public GameStateContext(InteractiveWarGame interactiveWarGame, WarGameQueries warGameQueries, WarGameMoves warGameMoves) {
      this.interactiveWarGame = interactiveWarGame;
      this.warGameQueries = warGameQueries;
      this.warGameMoves = warGameMoves;
   }
}
