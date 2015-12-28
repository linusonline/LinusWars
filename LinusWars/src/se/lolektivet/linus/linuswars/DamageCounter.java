package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Image;
import se.lolektivet.linus.linuswars.graphicalgame.TileView;
import se.lolektivet.linus.linuswars.graphics.Sprites;

/**
 * Created by Linus on 2015-12-23.
 */
public class DamageCounter {
   private Sprites _sprites;
   private static final int NUMBER_OFFSET_TOP = 10;
   private static final int NUMBER_OFFSET_LEFT = 2;
   private static final int EXTRA_TOP_OFFSET_FOR_BELOW_VERSION = 4;
   private static final int NUMBER_OFFSET_WIDTH = 7;

   public void init(Sprites sprites) {
      _sprites = sprites;
   }

   public void draw(int tileX, int tileY, TileView tileView, int damageNr) {
      if (tileView.isTileVisible(tileX, tileY - 2)) {
         drawCounterAbove(tileX, tileY, tileView, damageNr);
      } else {
         drawCounterBelow(tileX, tileY, tileView, damageNr);
      }
   }

   private void drawCounterAbove(int x, int y, TileView tileView, int damageNr) {
      Image damageCounter = _sprites.getDamageCounterAbove();
      int drawX = tileView.tileToPixelX(x) + tileView.tileWidthInPixels(1) / 2 - damageCounter.getWidth() / 2;
      int drawY = tileView.tileToPixelY(y) - damageCounter.getHeight();
      drawDamageCounter(damageCounter, drawX, drawY);
      drawDamageNumbers(damageNr, drawX + NUMBER_OFFSET_LEFT, drawY + NUMBER_OFFSET_TOP);
   }

   private void drawCounterBelow(int x, int y, TileView tileView, int damageNr) {
      Image damageCounter = _sprites.getDamageCounterBelow();
      int drawX = tileView.tileToPixelX(x) + tileView.tileWidthInPixels(1) / 2 - damageCounter.getWidth() / 2;
      int drawY = tileView.tileToPixelY(y + 1);
      drawDamageCounter(damageCounter, drawX, drawY);
      drawDamageNumbers(damageNr, drawX + NUMBER_OFFSET_LEFT, drawY + NUMBER_OFFSET_TOP + EXTRA_TOP_OFFSET_FOR_BELOW_VERSION);
   }

   private void drawDamageCounter(Image damageCounter, int drawX, int drawY) {
      damageCounter.draw(drawX, drawY);
   }

   private void drawDamageNumbers(int damageNr, int x, int y) {
      int drawX = x;
      int remainingDamage = damageNr;
      if (remainingDamage > 99) {
         _sprites.getDamageNumberImage(remainingDamage / 100).draw(drawX, y);
         drawX += NUMBER_OFFSET_WIDTH;
         remainingDamage = remainingDamage % 100;
      }
      if (damageNr > 99 || remainingDamage > 9) {
         _sprites.getDamageNumberImage(remainingDamage / 10).draw(drawX, y);
         drawX += NUMBER_OFFSET_WIDTH;
         remainingDamage = remainingDamage % 10;
      }
      _sprites.getDamageNumberImage(remainingDamage).draw(drawX, y);
   }
}
