package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import se.lolektivet.linus.linuswars.graphics.ResourceLoader;
import se.lolektivet.linus.linuswars.logic.enums.Direction;

/**
 * Created by Linus on 2014-09-19.
 */
public interface InteractiveGameState {
   InteractiveGameState handleExecuteDown();
   InteractiveGameState handleExecuteUp();
   InteractiveGameState handleCancel();
   InteractiveGameState handleDirection(Direction direction);
   void setResourceLoader(ResourceLoader loader);
   void draw(Graphics g, Font font, int x, int y);
}
