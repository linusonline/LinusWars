package se.lolektivet.linus.linuswars.graphics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Linus on 2015-12-23.
 */
class DamageCounterPane {
   private SpriteSheet _spriteSheet;

   void init(ResourceLoader resourceLoader) {
      if (_spriteSheet == null) {
         _spriteSheet = new SpriteSheet(resourceLoader.getDamageCounter(), 32, 27);
      }
   }

   Image getDamageCounterAbove() {
      return _spriteSheet.getSubImage(0, 0);
   }

   Image getDamageCounterBelow() {
      return _spriteSheet.getSubImage(0, 1);
   }
}
