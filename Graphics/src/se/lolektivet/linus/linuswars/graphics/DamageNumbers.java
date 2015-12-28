package se.lolektivet.linus.linuswars.graphics;

import org.newdawn.slick.Renderable;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Linus on 2015-12-23.
 */
class DamageNumbers {
   private SpriteSheet _spriteSheet;

   void init(ResourceLoader resourceLoader) {
      if (_spriteSheet == null) {
         _spriteSheet = new SpriteSheet(resourceLoader.getDamageNumbersSpriteSheet(), 7, 12);
      }
   }

   Renderable getDamageNumberImage(int nr) {
      if (nr < 0 || nr > 9) {
         throw new SpriteNotFoundException();
      }
      return _spriteSheet.getSubImage(nr, 0);
   }
}
