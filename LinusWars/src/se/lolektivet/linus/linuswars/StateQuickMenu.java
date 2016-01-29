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
public class StateQuickMenu implements GameState {

   private final GameStateContext _context;

   private final GameState _previousState;
   private GraphicalMenu<QuickMenuItem> _theMenu;
   private Sprites _sprites;

   public StateQuickMenu(GameStateContext context, GameState previousState) {
      _context = context;
      _previousState = previousState;
   }

   @Override
   public GameState handleExecuteDown() {
      QuickMenuItem menuItem = _theMenu.getObjectForSelectedItem();
      switch (menuItem) {
         case END_TURN:
            _context.warGameMoves.endTurn();
            // TODO: Play end-of-turn animations, change some graphics and bg sound.
            System.out.println("Turn ended!");
            return new StateTurnTransition(_context);
         case NOTHING:
            return _previousState;
         default:
            return this;
      }
   }

   @Override
   public GameState handleExecuteUp() {
      return this;
   }

   @Override
   public GameState handleCancel() {
      return _previousState;
   }

   @Override
   public GameState handleDirection(Direction direction) {
      _theMenu.moveCursor(direction);
      return this;
   }

   @Override
   public GameState update() {
      return this;
   }

   @Override
   public void init(Sprites sprites) {
      if (_sprites == null) {
         _sprites = sprites;
         _theMenu = new GraphicalMenu<>(_sprites.getMenuCursor());
         for (QuickMenuItem menuItem : QuickMenuItem.values()) {
            _theMenu.addItem(menuItem.getName(), menuItem);
         }
      }
   }

   @Override
   public void draw(GameContainer gc, Font font, int x, int y) {
      _context.interactiveWarGame.draw(gc, 0, 0);
      if (_theMenu != null) {
         _theMenu.draw(gc.getGraphics(), font);
      }
   }
}
