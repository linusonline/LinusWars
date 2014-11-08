package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import se.lolektivet.linus.linuswars.graphics.ResourceLoader;
import se.lolektivet.linus.linuswars.logic.WarGameMoves;
import se.lolektivet.linus.linuswars.logic.WarGameQueries;
import se.lolektivet.linus.linuswars.logic.enums.Direction;

/**
 * Created by Linus on 2014-09-20.
 */
public class QuickMenuState implements InteractiveGameState {
   private final InteractiveWarGame _interactiveWarGame;
   private final WarGameQueries _warGameQueries;
   private final WarGameMoves _warGameMoves;
   private GraphicalMenu _theMenu;
   private ResourceLoader _resourceLoader;

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
   public void setResourceLoader(ResourceLoader loader) {
      if (_resourceLoader == null) {
         _resourceLoader = loader;
         _theMenu = new GraphicalMenu(_resourceLoader.getMenuCursorImage());
         _theMenu.addItem(QuickMenuItem.END_TURN.getName(), null);
         _theMenu.addItem(QuickMenuItem.NOTHING.getName(), null);
      }
   }

   @Override
   public void draw(Graphics graphics, Font font, int x, int y) {
      _interactiveWarGame.draw(graphics, font, 0, 0);
      if (_theMenu != null) {
         _theMenu.draw(graphics, font);
      }
   }
}
