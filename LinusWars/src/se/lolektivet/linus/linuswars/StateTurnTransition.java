package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.core.enums.Direction;

/**
 * Created by Linus on 2015-12-23.
 */
public class StateTurnTransition implements GameState {

   private final GameStateContext _context;
   private static final int TRANSITION_DURATION_MS = 2000;
   private final long _startTime;

   public StateTurnTransition(GameStateContext context) {
      _context = context;
      _startTime = System.currentTimeMillis();
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
      if (System.currentTimeMillis() > _startTime + TRANSITION_DURATION_MS) {
         return new StateStarting(_context);
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
      int i = _context.warGameQueries.getDayNumber();
      _context.interactiveWarGame.draw(gc, 0, 0);
      font.drawString(100, 100, "Day " + i);
//      gc.getGraphics().drawString("Day " + i, 100, 100);
   }
}
