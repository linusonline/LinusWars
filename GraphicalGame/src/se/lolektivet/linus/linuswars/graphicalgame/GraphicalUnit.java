package se.lolektivet.linus.linuswars.graphicalgame;

import org.newdawn.slick.Renderable;
import se.lolektivet.linus.linuswars.graphics.UnitSprite;
import se.lolektivet.linus.linuswars.logic.enums.Direction;
import se.lolektivet.linus.linuswars.logic.Position;

/**
 * Created by Linus on 2014-09-18.
 */
public class GraphicalUnit {
   private final UnitSprite _unitSprite;
   private Direction _direction;
   private int _tilePositionX;
   private int _tilePositionY;

   public GraphicalUnit(UnitSprite unitSprite) {
      _unitSprite = unitSprite;
      setDirection(Direction.RIGHT);
   }

   public void setDirection(Direction direction) {
      _direction = direction;
   }

   public void draw(int pixelOffsetX, int pixelOffsetY, Renderable hpNumber, MapCoordinateTransformer coordinateTransformer) {
      if (!coordinateTransformer.isVisible(_tilePositionX, _tilePositionY)) {
         return;
      }
      int placeX = pixelOffsetX + coordinateTransformer.transformX(_tilePositionX);
      int placeY = pixelOffsetY + coordinateTransformer.transformY(_tilePositionY);

      _unitSprite.getUnitSprite(_direction, false).draw(
            placeX,
            placeY - 3);
      if (hpNumber != null) {
         hpNumber.draw(placeX + 8,
               placeY + 8);
      }
   }

   public void setTilePosition(Position position) {
      _tilePositionX = position.getX();
      _tilePositionY = position.getY();
   }
}
