package se.lolektivet.linus.linuswars.graphics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Linus on 2014-09-19.
 */
public class ResourceLoader {

   private final Map<String, Image> _imageCache;

   class ResourceNotLoadedException extends RuntimeException {
      ResourceNotLoadedException(String message) {
         super(message);
      }
   }

   public ResourceLoader() {
      _imageCache = new HashMap<String, Image>(0);
   }

   public Image getCursorImage() {
      return getImage("res/Cursor.png");
   }

   public Image getMenuCursorImage() {
      return getImage("res/menucursor.png");
   }

   public Image getAttackCursorSheet() {
      return getImage("res/attackcursor.png");
   }

   public Image getUnitSpriteSheet(String unitName, String factionName) {
      return getImage("res/" + unitName + "-" + factionName + ".png");
   }

   public Image getFontSpriteSheet() {
      return getImage("res/spritefont.png");
   }

   public Image getHpNumbersSpriteSheet() {
      return getImage("res/hpnumbers.png");
   }

   public Image getMovementArrowSpriteSheet() {
      return getImage("res/movearrow.png");
   }

   public Image getBuildingsSpriteSheet() {
      return getImage("res/buildings.png");
   }

   public Image getTerrainSpriteSheet() {
      return getImage("res/Landscape.png");
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
