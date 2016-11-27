package se.lolektivet.linus.linuswars;

import org.newdawn.slick.*;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import se.lolektivet.linus.linuswars.graphicalgame.GraphicalWarGame;
import se.lolektivet.linus.linuswars.graphicalgame.ScrollingTileView;
import se.lolektivet.linus.linuswars.graphicalgame.TileView;
import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.core.game.LogicalUnit;
import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.game.WarGameQueries;
import se.lolektivet.linus.linuswars.core.enums.Direction;

import java.util.Collection;
import java.util.HashSet;
import java.util.logging.Logger;

/**
 * Created by Linus on 2014-09-19.
 */
public class InteractiveWarGame {

   private static final Logger _logger = Logger.getLogger(InteractiveWarGame.class.getName());

   static class CursorOutsideMapException extends Exception {}

   private final WarGameQueries _warGameQueries;
   private final GraphicalWarGame _graphicalWarGame;

   private final ScrollingTileView _scrollingTileView;
   private Position _cursorPosition;
   private boolean _attackCursorVisible = false;
   private Position _positionUnderAttackCursor = null;
   private final Collection<Position> _positionsToIndicate;

   private Renderable _cursorImage;
   private Animation _attackCursor;
   private MovementArrowController _movementArrowController = new NullMovementArrowController();

   public InteractiveWarGame(GraphicalWarGame graphicalWarGame, WarGameQueries warGameQueries, ScrollingTileView tileView) {
      _cursorPosition = new Position(0, 0);
      _scrollingTileView = tileView;
      _warGameQueries = warGameQueries;
      _positionsToIndicate = new HashSet<>();
      _graphicalWarGame = graphicalWarGame;
   }

   public void init(Sprites sprites) {
      _attackCursor = sprites.getAttackCursor();
      _cursorImage = sprites.getCursor();
   }

   public void hideMovementArrow() {
      _movementArrowController = new NullMovementArrowController();
   }

   public void setMovementArrowController(MovementArrowController movementArrowController) {
      if (movementArrowController == null) {
         throw new NullPointerException("MovementArrowController can not be null. Use hideMovementArrow() instead.");
      }
      _movementArrowController = movementArrowController;
   }

   Position getCursorPosition() {
      return _cursorPosition;
   }

   TileView getTileView() {
      return _scrollingTileView;
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
      _scrollingTileView.cursorMoved(newPosition, _warGameQueries.getMapWidth(), _warGameQueries.getMapHeight());
      adjustHudToCursor();
      if (_warGameQueries.hasUnitAtPosition(_cursorPosition)) {
         _logger.info(_warGameQueries.getUnitAtPosition(_cursorPosition).toString());
         for (LogicalUnit transportedUnit : _warGameQueries.getTransportedUnits(_warGameQueries.getUnitAtPosition(_cursorPosition))) {
            _logger.info("   Transporting " + transportedUnit.getType());
         }
      }
   }

   private void adjustHudToCursor() {
      int leftLimit = _scrollingTileView.getVisibleTileOffsetX() + _scrollingTileView.getVisibleTileWidth() / 3;
      if (_cursorPosition.getX() < leftLimit) {
         _graphicalWarGame.setHudOnLeft(false);
      } else {
         int rightLimit = _scrollingTileView.getVisibleTileOffsetX() + 2 * _scrollingTileView.getVisibleTileWidth() / 3 - 1;
         if (_cursorPosition.getX() > rightLimit) {
            _graphicalWarGame.setHudOnLeft(true);
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
      _graphicalWarGame.drawMap(_scrollingTileView);
      drawDestinationPositions(gc, x, y);
      _movementArrowController.draw(x, y, _scrollingTileView);
      _graphicalWarGame.drawUnits(_scrollingTileView, x, y);
      drawAttackCursor(x, y);
      drawCursor(x, y);
      _graphicalWarGame.drawHud(_scrollingTileView, x, y);
   }

   private void drawDestinationPositions(GameContainer gc, int x, int y) {
      for (Position indicatedPosition : _positionsToIndicate) {
         if (_scrollingTileView.isTileVisible(indicatedPosition.getX(), indicatedPosition.getY())) {
            Shape fillShape = new Rectangle(
                  x + _scrollingTileView.tileToPixelX(indicatedPosition.getX()),
                  y + _scrollingTileView.tileToPixelY(indicatedPosition.getY()),
                  _scrollingTileView.tileWidthInPixels(1),
                  _scrollingTileView.tileHeightInPixels(1));
            gc.getGraphics().fill(fillShape, new GradientFill(0, 0, new Color(255, 255, 255, 128), 0, 1, new Color(255, 255, 255, 128)));
         }
      }
   }

   private void drawAttackCursor(int x, int y) {
      if (_attackCursorVisible) {
         _attackCursor.draw(x + _scrollingTileView.tileToPixelX(_positionUnderAttackCursor.getX()) - 4,
               y + _scrollingTileView.tileToPixelY(_positionUnderAttackCursor.getY()) - 4);
      }
   }

   private void drawCursor(int x, int y) {
      _cursorImage.draw(
            x + _scrollingTileView.tileToPixelX(_cursorPosition.getX()),
            y + _scrollingTileView.tileToPixelY(_cursorPosition.getY()));
   }

   void setPositionOfGraphicForUnit(LogicalUnit logicalUnit, Position newPosition) {
      _graphicalWarGame.setPositionOfGraphicForUnit(logicalUnit, newPosition);
   }

   void resetUnitGraphicToUnitPosition(LogicalUnit logicalUnit) {
      _graphicalWarGame.resetUnitGraphicToUnitPosition(logicalUnit);
   }
}
