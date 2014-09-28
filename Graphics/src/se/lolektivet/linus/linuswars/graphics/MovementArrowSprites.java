package se.lolektivet.linus.linuswars.graphics;

import org.newdawn.slick.Renderable;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Linus on 2014-09-24.
 */
public class MovementArrowSprites {
   public enum ArrowSection {
      HORIZONTAL(9),
      VERTICAL(0),
      BEND_S_TO_E(1),
      BEND_S_TO_W(2),
      BEND_N_TO_E(3),
      BEND_N_TO_W(4),
      END_N(7),
      END_S(5),
      END_E(8),
      END_W(6);

      private int _sheetIndex;

      ArrowSection(int sheetIndex) {
         _sheetIndex = sheetIndex;
      }

      public int getSheetIndex() {
         return _sheetIndex;
      }
   }

   private SpriteSheet _spriteSheet;

   public void init(ResourceLoader resourceLoader) {
      _spriteSheet = new SpriteSheet(resourceLoader.getMovementArrowSpriteSheet(), 16, 16);
   }

   public Renderable getArrowSection(ArrowSection arrowSection) {
      return _spriteSheet.getSubImage(arrowSection.getSheetIndex(), 0);
   }
}
