package se.lolektivet.linus.linuswars.graphics;

import org.newdawn.slick.Renderable;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Linus on 2014-09-24.
 */
public class HpNumbers {
   private SpriteSheet _spriteSheet;

   public void init(ResourceLoader resourceLoader) {
      if (_spriteSheet == null) {
         _spriteSheet = new SpriteSheet(resourceLoader.getHpNumbersSpriteSheet(), 8, 7);
      }
   }

   public Renderable getHpNumberImage(int nr) {
      if (nr < 1 || nr > 9) {
         throw new SpriteNotFoundException();
      }
      nr--;
      return _spriteSheet.getSubImage(nr % 3, nr / 3);
   }
}
