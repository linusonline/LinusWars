package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.logic.enums.Direction;

/**
 * Created by Linus on 2014-09-19.
 */
public interface GameState {
   GameState handleExecuteDown();
   GameState handleExecuteUp();
   GameState handleCancel();
   GameState handleDirection(Direction direction);
   GameState update();
   void init(Sprites sprites);
   void draw(GameContainer gc, Font font, int x, int y);
}
