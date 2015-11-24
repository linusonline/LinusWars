package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.logic.enums.Direction;

/**
 * Created by Linus on 2014-09-19.
 */
public interface InteractiveGameState {
   InteractiveGameState handleExecuteDown();
   InteractiveGameState handleExecuteUp();
   InteractiveGameState handleCancel();
   InteractiveGameState handleDirection(Direction direction);
   void setSprites(Sprites sprites);
   void draw(GameContainer gc, Font font, int x, int y);
}
