package se.lolektivet.linus.linuswars.graphicalgame;

import org.newdawn.slick.GameContainer;

/**
 * Created by Linus on 2015-11-24.
 */
public class ScrollingMapContainer {
   private final GraphicalWarMap _theMap;

   public ScrollingMapContainer(GraphicalWarMap theMap) {
      _theMap = theMap;
   }

   public void draw(GameContainer gc, MapCoordinateTransformer coordinateTransformer) {
      _theMap.draw(coordinateTransformer);
   }
}
