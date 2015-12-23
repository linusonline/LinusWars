package se.lolektivet.linus.linuswars.graphics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Linus on 2014-09-19.
 */
class ResourceLoader {

   private static final String RES_PATH = "res/";
   private static final String HUD_PATH = RES_PATH + "hud/";
   private static final String UNIT_PATH = RES_PATH + "units/";
   private static final String MAP_PATH = RES_PATH + "map/";

   private final Map<String, Image> _imageCache;

   static class ResourceNotLoadedException extends RuntimeException {
      ResourceNotLoadedException(String message) {
         super(message);
      }
   }

   ResourceLoader() {
      _imageCache = new HashMap<>(0);
   }

   Image getCursorImage() {
      return getImage(HUD_PATH + "cursor.png");
   }

   Image getMenuCursorImage() {
      return getImage(HUD_PATH + "menucursor.png");
   }

   Image getAttackCursorSheet() {
      return getImage(HUD_PATH + "attackcursor.png");
   }

   Image getUnitSpriteSheet(String unitName, String factionName) {
      return getImage(UNIT_PATH + unitName + "-" + factionName + ".png");
   }

   Image getFontSpriteSheet() {
      return getImage(HUD_PATH + "spritefont.png");
   }

   Image getHpNumbersSpriteSheet() {
      return getImage(HUD_PATH + "hpnumbers.png");
   }

   Image getMovementArrowSpriteSheet() {
      return getImage(HUD_PATH + "movearrow.png");
   }

   Image getBuildingsSpriteSheet() {
      return getImage(MAP_PATH + "buildings.png");
   }

   Image getTerrainSpriteSheet() {
      return getImage(MAP_PATH + "landscape.png");
   }

   Image getMoneyCounterImage() {
      return getImage(HUD_PATH + "moneypane.png");
   }

   Image getMoneyNumbersSpriteSheet() {
      return getImage(HUD_PATH + "goldnumbers.png");
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
