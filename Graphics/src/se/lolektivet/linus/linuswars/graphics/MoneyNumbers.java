package se.lolektivet.linus.linuswars.graphics;

import org.newdawn.slick.Renderable;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Linus on 2015-11-25.
 */
class MoneyNumbers {
   private SpriteSheet _spriteSheet;

   void init(ResourceLoader resourceLoader) {
      if (_spriteSheet == null) {
         _spriteSheet = new SpriteSheet(resourceLoader.getMoneyNumbersSpriteSheet(), 8, 7);
      }
   }

   Renderable getMoneyNumberImage(int nr) {
      if (nr < 0 || nr > 9) {
         throw new SpriteNotFoundException();
      }
      return _spriteSheet.getSubImage(nr % 5, nr / 5);
   }
}
