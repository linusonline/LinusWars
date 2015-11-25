package se.lolektivet.linus.linuswars.graphics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Linus on 2014-09-19.
 */
class ResourceLoader {

   private final Map<String, Image> _imageCache;

   class ResourceNotLoadedException extends RuntimeException {
      ResourceNotLoadedException(String message) {
         super(message);
      }
   }

   ResourceLoader() {
      _imageCache = new HashMap<>(0);
   }

   Image getCursorImage() {
      return getImage("res/Cursor.png");
   }

   Image getMenuCursorImage() {
      return getImage("res/menucursor.png");
   }

   Image getAttackCursorSheet() {
      return getImage("res/attackcursor.png");
   }

   Image getUnitSpriteSheet(String unitName, String factionName) {
      return getImage("res/" + unitName + "-" + factionName + ".png");
   }

   Image getFontSpriteSheet() {
      return getImage("res/spritefont.png");
   }

   Image getHpNumbersSpriteSheet() {
      return getImage("res/hpnumbers.png");
   }

   Image getMovementArrowSpriteSheet() {
      return getImage("res/movearrow.png");
   }

   Image getBuildingsSpriteSheet() {
      return getImage("res/buildings.png");
   }

   Image getTerrainSpriteSheet() {
      return getImage("res/Landscape.png");
   }

   Image getMoneyCounterImage() {
      return getImage("res/moneypane.png");
   }

   Image getMoneyNumbersSpriteSheet() {
      return getImage("res/goldnumbers.png");
   }

   private Image getImage(String path) {
      Image image;
      image = _imageCache.get(path);
      if (image == null) {
         image = loadImage(path);
      }
      return image;
   }

   private Image loadImage(String path) {
      try {
         Image image = new Image(path);
         _imageCache.put(path, image);
         return image;
      } catch (SlickException e) {
         throw new ResourceNotLoadedException(e.getMessage());
      }
   }
}
