package se.lolektivet.linus.linuswars;

import org.newdawn.slick.*;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import se.lolektivet.linus.linuswars.graphicalgame.GraphicalWarGame;
import se.lolektivet.linus.linuswars.graphicalgame.MapCoordinateTransformer;
import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.logic.LogicalUnit;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.WarGameQueries;
import se.lolektivet.linus.linuswars.logic.enums.Direction;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Linus on 2014-09-19.
 */
public class InteractiveWarGame {

   class CursorOutsideMapException extends Exception {}

   private final WarGameQueries _warGameQueries;
   private final GraphicalWarGame _graphicalWarGame;

   private final MapCoordinateTransformer _coordinateTransformer;
   private Position _cursorPosition;
   private boolean _attackCursorVisible = false;
   private Position _positionUnderAttackCursor = null;
   private final Collection<Position> _positionsToIndicate;

   private Renderable _cursorImage;
   private Animation _attackCursor;
   private MovementArrowController _movementArrowController = new NullMovementArrowController();

   public InteractiveWarGame(GraphicalWarGame graphicalWarGame, WarGameQueries warGameQueries, MapCoordinateTransformer mapCoordinateTransformer) {
      _cursorPosition = new Position(0, 0);
      _coordinateTransformer = mapCoordinateTransformer;
      _warGameQueries = warGameQueries;
      _positionsToIndicate = new HashSet<>();
      _graphicalWarGame = graphicalWarGame;
   }

   public void init(Sprites sprites) {
      _attackCursor = sprites.getAttackCursor();
      _cursorImage = sprites.getCursor();
   }

   public void setMovementArrowController(MovementArrowController movementArrowController) {
      if (movementArrowController == null) {
         _movementArrowController = new NullMovementArrowController();
      } else {
         _movementArrowController = movementArrowController;
      }
   }

   Position getCursorPosition() {
      return _cursorPosition;
   }

   void moveCursor(Direction direction) throws CursorOutsideMapException {
      Position newCandidatePosition = _cursorPosition.getPositionAfterStep(direction);
      if (_warGameQueries.isPositionInsideMap(newCandidatePosition)) {
         moveCursor(newCandidatePosition);
      } else {
         throw new CursorOutsideMapException();
      }
   }

   private void moveCursor(Position newPosition) {
      _cursorPosition = newPosition;
      _coordinateTransformer.cursorMoved(newPosition, _warGameQueries.getMapWidth(), _warGameQueries.getMapHeight());
      if (_warGameQueries.hasUnitAtPosition(_cursorPosition)) {
         System.out.println(_warGameQueries.getUnitAtPosition(_cursorPosition));
         for (LogicalUnit transportedUnit : _warGameQueries.getTransportedUnits(_warGameQueries.getUnitAtPosition(_cursorPosition))) {
            System.out.println("   Transporting " + transportedUnit.getType());
         }
      }
   }

   void indicateSelectedPositions(Collection<Position> selectedPositions) {
      _positionsToIndicate.addAll(selectedPositions);
   }

   void stopIndicatingPositions() {
      _positionsToIndicate.clear();
   }

   public void showAttackCursorOnUnit(LogicalUnit logicalUnit) {
      _positionUnderAttackCursor = _warGameQueries.getPositionOfUnit(logicalUnit);
      _attackCursorVisible = true;
   }

   public void showAttackCursorOnPosition(Position position) {
      _positionUnderAttackCursor = position;
      _attackCursorVisible = true;
   }

   public void hideAttackCursor() {
      _attackCursorVisible = false;
   }

   public void hideGraphicForUnit(LogicalUnit logicalUnit) {
      _graphicalWarGame.hideGraphicForUnit(logicalUnit);
   }

   public void showGraphicForUnit(LogicalUnit logicalUnit) {
      _graphicalWarGame.showGraphicForUnit(logicalUnit);
   }

   void draw(GameContainer gc, int x, int y) {
      _graphicalWarGame.drawMap(gc);
      drawDestinationPositions(gc, x, y);
      _movementArrowController.draw(x, y, _coordinateTransformer);
      _graphicalWarGame.drawUnits(x, y);
      drawAttackCursor(x, y);
      _graphicalWarGame.drawHud(x, y);
      drawCursor(x, y);
   }

   private void drawDestinationPositions(GameContainer gc, int x, int y) {
      for (Position indicatedPosition : _positionsToIndicate) {
         if (_coordinateTransformer.isVisible(indicatedPosition.getX(), indicatedPosition.getY())) {
            Shape fillShape = new Rectangle(
                  x + _coordinateTransformer.transformX(indicatedPosition.getX()),
                  y + _coordinateTransformer.transformY(indicatedPosition.getY()),
                  _coordinateTransformer.transformX(1),
                  _coordinateTransformer.transformY(1));
            gc.getGraphics().fill(fillShape, new GradientFill(0, 0, new Color(255, 255, 255, 128), 0, 1, new Color(255, 255, 255, 128)));
         }
      }
   }

   private void drawAttackCursor(int x, int y) {
      if (_attackCursorVisible) {
         _attackCursor.draw(x + _coordinateTransformer.transformX(_positionUnderAttackCursor.getX()) - 4,
               y + _coordinateTransformer.transformY(_positionUnderAttackCursor.getY()) - 4);
      }
   }

   private void drawCursor(int x, int y) {
      _cursorImage.draw(
            x + _coordinateTransformer.transformX(_cursorPosition.getX()),
            y + _coordinateTransformer.transformY(_cursorPosition.getY()));
   }

   void setPositionOfGraphicForUnit(LogicalUnit logicalUnit, Position newPosition) {
      _graphicalWarGame.setPositionOfGraphicForUnit(logicalUnit, newPosition);
   }
}
