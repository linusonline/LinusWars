package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.logic.game.WarGameMoves;
import se.lolektivet.linus.linuswars.logic.game.WarGameQueries;
import se.lolektivet.linus.linuswars.logic.enums.Direction;

/**
 * Created by Linus on 2014-09-20.
 */
public class QuickMenuState implements InteractiveGameState {
   private final InteractiveWarGame _interactiveWarGame;
   private final WarGameQueries _warGameQueries;
   private final WarGameMoves _warGameMoves;
   private GraphicalMenu _theMenu;
   private Sprites _sprites;

   public QuickMenuState(InteractiveWarGame interactiveWarGame, WarGameQueries warGameQueries, WarGameMoves warGameMoves) {
      _interactiveWarGame = interactiveWarGame;
      _warGameQueries = warGameQueries;
      _warGameMoves = warGameMoves;
   }

   @Override
   public InteractiveGameState handleExecuteDown() {
      String menuItemText = _theMenu.getTextForSelectedItem();
      QuickMenuItem menuItem = QuickMenuItem.fromName(menuItemText);
      switch (menuItem) {
         case END_TURN:
            _warGameMoves.endTurn();
            // TODO: Play end-of-turn animations, change some graphics and bg sound.
            System.out.println("Turn ended!");
            break;
         case NOTHING:
            break;
         default:
      }
      return new StartingState(_interactiveWarGame, _warGameQueries, _warGameMoves);
   }

   @Override
   public InteractiveGameState handleExecuteUp() {
      return this;
   }

   @Override
   public InteractiveGameState handleCancel() {
      return new StartingState(_interactiveWarGame, _warGameQueries, _warGameMoves);
   }

   @Override
   public InteractiveGameState handleDirection(Direction direction) {
      _theMenu.moveCursor(direction);
      return this;
   }

   @Override
   public void setSprites(Sprites sprites) {
      if (_sprites == null) {
         _sprites = sprites;
         _theMenu = new GraphicalMenu(_sprites.getMenuCursor());
         _theMenu.addItem(QuickMenuItem.END_TURN.getName());
         _theMenu.addItem(QuickMenuItem.NOTHING.getName());
      }
   }

   @Override
   public void draw(GameContainer gc, Font font, int x, int y) {
      _interactiveWarGame.draw(gc, 0, 0);
      if (_theMenu != null) {
         _theMenu.draw(gc.getGraphics(), font);
      }
   }
}
