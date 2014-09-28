package se.lolektivet.linus.linuswars.graphicalgame;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Renderable;
import se.lolektivet.linus.linuswars.graphics.HpNumbers;
import se.lolektivet.linus.linuswars.logic.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Linus on 2014-09-26.
 */
public class GraphicalWarGame implements LogicalWarGame.Listener {
   private final HpNumbers _hpNumbers;
   private final MapCoordinateTransformer _coordinateTransformer;
   private final Map<LogicalUnit, GraphicalUnit> _graphicsForUnits;
   private final LogicalWarGame _logicalWarGame;
   private GraphicalWarMap _theMap;

   public GraphicalWarGame(HpNumbers hpNumbers, LogicalWarGame logicalWarGame) {
      _hpNumbers = hpNumbers;
      _coordinateTransformer = new MapCoordinateTransformerImpl();
      _logicalWarGame = logicalWarGame;
      _graphicsForUnits = new HashMap<LogicalUnit, GraphicalUnit>();

      _logicalWarGame.addListener(this);
   }

   public void setMap(GraphicalWarMap map) {
      _theMap = map;
   }

   public void callGameStart() {
      makeAllUnitsFaceEnemyHq();
   }

   @Override
   public void unitWasDestroyed(LogicalUnit logicalUnit) {
      // TODO: Queue up explosion animations
      _graphicsForUnits.remove(logicalUnit);
   }

   public void addUnit(GraphicalUnit graphicalUnit, LogicalUnit logicalUnit, Position position) {
      graphicalUnit.setPosition(_coordinateTransformer.transform(position));
      _graphicsForUnits.put(logicalUnit, graphicalUnit);
   }

   public GraphicalUnit getGraphicForUnit(LogicalUnit unit) {
      return _graphicsForUnits.get(unit);
   }

   public void setPositionOfGraphicForUnit(LogicalUnit logicalUnit, Position newPosition) {
      setUnitPosition(getGraphicForUnit(logicalUnit), newPosition);
   }

   private void setUnitPosition(GraphicalUnit graphicalUnit, Position newPosition) {
      graphicalUnit.setPosition(_coordinateTransformer.transform(newPosition));
   }

   public void makeAllUnitsFaceEnemyHq() {
      for (LogicalUnit logicalUnit : _graphicsForUnits.keySet()) {
         makeUnitFaceEnemyHq(logicalUnit);
      }
   }

   public void makeUnitFaceEnemyHq(LogicalUnit logicalUnit) {
      Faction friendlyFaction = _logicalWarGame.getFactionForUnit(logicalUnit);
      Position friendlyHq = _logicalWarGame.getHqPosition(friendlyFaction);
      for (Faction otherFaction : _logicalWarGame.getFactionsInGame()) {
         if (!friendlyFaction.equals(otherFaction)) {
            Position enemyHq = _logicalWarGame.getHqPosition(otherFaction);
            Direction directionToFace = enemyHq.getX() < friendlyHq.getX() ? Direction.LEFT : Direction.RIGHT;
            _graphicsForUnits.get(logicalUnit).setDirection(directionToFace);
         }
      }
   }

   public void drawMap(Graphics g, Font font, int x, int y) {
      _theMap.draw(_coordinateTransformer, x, y);
   }

   public void drawUnits(Graphics g, Font font, int x, int y) {
      for (Map.Entry<LogicalUnit, GraphicalUnit> entry : _graphicsForUnits.entrySet()) {
         int hp = entry.getKey().getHp1To10();
         Renderable hpNumber = null;
         if (hp < 10) {
            hpNumber = _hpNumbers.getHpNumberImage(hp);
         }
         entry.getValue().draw(x, y, hpNumber);
      }
   }
}
