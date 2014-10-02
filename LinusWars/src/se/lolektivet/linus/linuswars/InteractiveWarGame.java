package se.lolektivet.linus.linuswars;

import org.newdawn.slick.*;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import se.lolektivet.linus.linuswars.graphicalgame.GraphicalWarGame;
import se.lolektivet.linus.linuswars.graphicalgame.MapCoordinateTransformer;
import se.lolektivet.linus.linuswars.graphicalgame.MapCoordinateTransformerImpl;
import se.lolektivet.linus.linuswars.graphics.ResourceLoader;
import se.lolektivet.linus.linuswars.logic.enums.Direction;
import se.lolektivet.linus.linuswars.logic.LogicalUnit;
import se.lolektivet.linus.linuswars.logic.LogicalWarGame;
import se.lolektivet.linus.linuswars.logic.Position;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Linus on 2014-09-19.
 */
public class InteractiveWarGame {

   class CursorOutsideMapException extends Exception {}

   private final LogicalWarGame _logicalWarGame;
   private GraphicalWarGame _graphicalWarGame;

   private final MapCoordinateTransformer _coordinateTransformer;
   private Position _cursorPosition;
   private Position _positionUnderAttackCursor = null;
   private final Collection<Position> _positionsToIndicate;

   private Image _cursorImage;
   private Animation _attackCursor;
   private MovementArrowController _movementArrowController;

   public InteractiveWarGame(GraphicalWarGame graphicalWarGame, LogicalWarGame logicalWarGame) {
      _cursorPosition = new Position(0, 0);
      _coordinateTransformer = new MapCoordinateTransformerImpl();
      _logicalWarGame = logicalWarGame;
      _positionsToIndicate = new HashSet<Position>();
      _graphicalWarGame = graphicalWarGame;
   }

   public void init(ResourceLoader resourceLoader) {
      Image attackCursorImage = resourceLoader.getAttackCursorSheet();
      SpriteSheet attackCursorSheet = new SpriteSheet(attackCursorImage, 30, 29);
      _attackCursor = new Animation(attackCursorSheet, 500);
      _cursorImage = resourceLoader.getCursorImage();
   }

   public void setMovementArrowController(MovementArrowController movementArrowController) {
      _movementArrowController = movementArrowController;
   }

   Position getCursorPosition() {
      return _cursorPosition;
   }

   void moveCursor(Direction direction) throws CursorOutsideMapException {
      Position newCandidatePosition = _cursorPosition.getPositionAfterStep(direction);
      if (_logicalWarGame.isPositionInsideMap(newCandidatePosition)) {
         _cursorPosition = newCandidatePosition;
         if (_logicalWarGame.hasUnitAtPosition(_cursorPosition)) {
            System.out.println(_logicalWarGame.getUnitAtPosition(_cursorPosition));
            for (LogicalUnit transportedUnit : _logicalWarGame.getTransportedUnits(_logicalWarGame.getUnitAtPosition(_cursorPosition))) {
               System.out.println("   Transporting " + transportedUnit.getType());
            }
         }
      } else {
         throw new CursorOutsideMapException();
      }
   }

   void indicateSelectedPositions(Collection<Position> selectedPositions) {
      _positionsToIndicate.addAll(selectedPositions);
   }

   void stopIndicatingPositions() {
      _positionsToIndicate.clear();
   }

   public void showAttackCursorOnUnit(LogicalUnit logicalUnit) {
      _positionUnderAttackCursor = _logicalWarGame.getPositionOfUnit(logicalUnit);
   }

   public void showAttackCursorOnPosition(Position position) {
      _positionUnderAttackCursor = position;
   }

   public void hideAttackCursor() {
      _positionUnderAttackCursor = null;
   }

   public void hideGraphicForUnit(LogicalUnit logicalUnit) {
      _graphicalWarGame.hideGraphicForUnit(logicalUnit);
   }

   public void showGraphicForUnit(LogicalUnit logicalUnit) {
      _graphicalWarGame.showGraphicForUnit(logicalUnit);
   }

   void draw(Graphics g, Font font, int x, int y) {
      _graphicalWarGame.drawMap(g, font, x, y);
      for (Position indicatedPosition : _positionsToIndicate) {
         Shape fillShape = new Rectangle(
               x + _coordinateTransformer.transform(indicatedPosition.getX()),
               y + _coordinateTransformer.transform(indicatedPosition.getY()),
               _coordinateTransformer.transform(1),
               _coordinateTransformer.transform(1));
         g.fill(fillShape, new GradientFill(0, 0, new Color(255, 255, 255, 128), 0, 1, new Color(255, 255, 255, 128)));
      }
      if (_movementArrowController != null){
         _movementArrowController.draw(x, y, _coordinateTransformer);
      }
      _graphicalWarGame.drawUnits(g, font, x, y);
      if (_positionUnderAttackCursor != null) {
         _attackCursor.draw(x + _coordinateTransformer.transform(_positionUnderAttackCursor.getX()) - 4,
               y + _coordinateTransformer.transform(_positionUnderAttackCursor.getY()) - 4);
      }
      _cursorImage.draw(
            x + _coordinateTransformer.transform(_cursorPosition.getX()),
            y + _coordinateTransformer.transform(_cursorPosition.getY()));
   }

   void setPositionOfGraphicForUnit(LogicalUnit logicalUnit, Position newPosition) {
      _graphicalWarGame.setPositionOfGraphicForUnit(logicalUnit, newPosition);
   }
}
