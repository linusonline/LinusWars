package se.lolektivet.linus.linuswars.graphics;

import org.newdawn.slick.Renderable;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Linus on 2014-09-24.
 */
class MovementArrowSprites {

   private SpriteSheet _spriteSheet;

   void init(ResourceLoader resourceLoader) {
      _spriteSheet = new SpriteSheet(resourceLoader.getMovementArrowSpriteSheet(), 16, 16);
   }

   Renderable getArrowSection(MovementArrowSection arrowSection) {
      return _spriteSheet.getSubImage(arrowSection.getSheetIndex(), 0);
   }
}
