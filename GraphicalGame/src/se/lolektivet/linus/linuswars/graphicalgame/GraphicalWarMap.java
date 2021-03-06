package se.lolektivet.linus.linuswars.graphicalgame;

import org.newdawn.slick.Renderable;
import se.lolektivet.linus.linuswars.core.MutablePosition;
import se.lolektivet.linus.linuswars.core.game.LogicalWarMap;
import se.lolektivet.linus.linuswars.core.Position;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Linus on 2014-09-18.
 */
public class GraphicalWarMap {
   private final LogicalWarMap _logicalWarMap;
   private final Map<Position, Renderable> _buildingSprites;
   // TODO: Make this a list of lists to speed up lookup.
   private final Map<MutablePosition, Renderable> _terrainSprites;

   private TileView _tileView;

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
      _terrainSprites.put(new MutablePosition(x, y), terrainImage);
   }

   public void addBuilding(Renderable buildingImage, int x, int y) {
      _buildingSprites.put(new Position(x, y), buildingImage);
   }

   public void draw(TileView tileView) {
      _tileView = tileView;
      MutablePosition currentPosition = new MutablePosition(0, 0);
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
      if (_tileView.isTileVisible(x, y)) {
         image.draw(_tileView.tileToPixelX(x),
               _tileView.tileToPixelY(y) - 15);
      }
   }

   private void drawTile(Renderable image, int x, int y) {
      if (_tileView.isTileVisible(x, y)) {
         image.draw(_tileView.tileToPixelX(x),
               _tileView.tileToPixelY(y));
      }
   }
}
