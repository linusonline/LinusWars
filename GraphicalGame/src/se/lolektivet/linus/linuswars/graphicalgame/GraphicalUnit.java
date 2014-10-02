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
   private int _positionX;
   private int _positionY;

   public GraphicalUnit(UnitSprite unitSprite) {
      _unitSprite = unitSprite;
      setDirection(Direction.RIGHT);
   }

   public void setDirection(Direction direction) {
      _direction = direction;
   }

   public void draw(int relX, int relY, Renderable hpNumber) {
      _unitSprite.getSprite(_direction, false).draw(relX + _positionX, relY + _positionY - 3);
      if (hpNumber != null) {
         hpNumber.draw(relX + _positionX + 8,
               relY + _positionY + 8);
      }
   }

   public void setPosition(Position position) {
      _positionX = position.getX();
      _positionY = position.getY();
   }
}
