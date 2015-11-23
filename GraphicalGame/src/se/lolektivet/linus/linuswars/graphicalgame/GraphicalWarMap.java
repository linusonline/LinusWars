package se.lolektivet.linus.linuswars.graphicalgame;

import org.newdawn.slick.Renderable;
import se.lolektivet.linus.linuswars.logic.LogicalWarMap;
import se.lolektivet.linus.linuswars.logic.Position;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Linus on 2014-09-18.
 */
public class GraphicalWarMap {
   private final LogicalWarMap _logicalWarMap;
   private final Map<Position, Renderable> _buildingSprites;
   // TODO: Make this a list of lists to speed up lookup.
   private final Map<Position, Renderable> _terrainSprites;

   private int _relativeX;
   private int _relativeY;
   private MapCoordinateTransformer _transformer;

   public GraphicalWarMap(LogicalWarMap logicalWarMap) {
      _buildingSprites = new HashMap<>();
      _terrainSprites = new HashMap<>();
      _logicalWarMap = logicalWarMap;
   }

   public int getWidth() {
      return _logicalWarMap.getWidth();
   }

   public int getHeight() {
      return _logicalWarMap.getHeight();
   }

   public void addTerrain(Renderable terrainImage, int x, int y) {
      _terrainSprites.put(new Position(x, y), terrainImage);
   }

   public void addBuilding(Renderable buildingImage, int x, int y) {
      _buildingSprites.put(new Position(x, y), buildingImage);
   }

   public void draw(MapCoordinateTransformer transformer, int x, int y) {
      _relativeX = x;
      _relativeY = y;
      _transformer = transformer;
      Position currentPosition = new Position(0, 0);
      for (int mapy = 0; mapy < _logicalWarMap.getHeight(); mapy++) {
         currentPosition.setY(mapy);
         for (int mapx = 0; mapx < _logicalWarMap.getWidth(); mapx++) {
            currentPosition.setX(mapx);
            drawTile(_terrainSprites.get(currentPosition), mapx, mapy);
         }
      }
      for (Map.Entry<Position, Renderable> entry : _buildingSprites.entrySet()) {
         drawBuilding(entry.getValue(), entry.getKey().getX(), entry.getKey().getY());
      }
   }

   private void drawBuilding(Renderable image, int x, int y) {
      image.draw(_relativeX + _transformer.transform(x),
            _relativeY + _transformer.transform(y) - 15);
   }

   private void drawTile(Renderable image, int x, int y) {
      image.draw(_relativeX + _transformer.transform(x),
            _relativeY + _transformer.transform(y));
   }
}
