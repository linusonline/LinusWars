package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import se.lolektivet.linus.linuswars.graphics.ResourceLoader;
import se.lolektivet.linus.linuswars.logic.enums.Direction;
import se.lolektivet.linus.linuswars.logic.LogicalWarGame;

/**
 * Created by Linus on 2014-09-20.
 */
public class QuickMenuState implements InteractiveGameState {
   private final InteractiveWarGame _interactiveWarGame;
   private final LogicalWarGame _logicalWarGame;
   private GraphicalMenu _theMenu;
   private ResourceLoader _resourceLoader;

   public QuickMenuState(InteractiveWarGame interactiveWarGame, LogicalWarGame logicalWarGame) {
      _interactiveWarGame = interactiveWarGame;
      _logicalWarGame = logicalWarGame;
   }

   @Override
   public InteractiveGameState handleExecuteDown() {
      String menuItemText = _theMenu.getTextForSelectedItem();
      QuickMenuItem menuItem = QuickMenuItem.fromName(menuItemText);
      switch (menuItem) {
         case END_TURN:
            _logicalWarGame.endTurn();
            // TODO: Play end-of-turn animations, change some graphics and bg sound.
            System.out.println("Turn ended!");
            break;
         case NOTHING:
            break;
         default:
      }
      return new StartingState(_interactiveWarGame, _logicalWarGame);
   }

   @Override
   public InteractiveGameState handleExecuteUp() {
      return this;
   }

   @Override
   public InteractiveGameState handleCancel() {
      return new StartingState(_interactiveWarGame, _logicalWarGame);
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
