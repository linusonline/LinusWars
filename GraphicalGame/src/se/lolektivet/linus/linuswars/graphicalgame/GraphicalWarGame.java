package se.lolektivet.linus.linuswars.graphicalgame;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Renderable;
import se.lolektivet.linus.linuswars.graphics.HpNumbers;
import se.lolektivet.linus.linuswars.logic.*;
import se.lolektivet.linus.linuswars.logic.enums.Direction;
import se.lolektivet.linus.linuswars.logic.enums.Faction;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Linus on 2014-09-26.
 */
public class GraphicalWarGame implements WarGameListener {
   private final HpNumbers _hpNumbers;
   private final MapCoordinateTransformer _coordinateTransformer;
   private final Map<LogicalUnit, GraphicalUnit> _graphicsForUnits;
   private final Set<LogicalUnit> _hiddenUnits;
   private final WarGameQueries _warGameQueries;
   private GraphicalWarMap _theMap;

   public GraphicalWarGame(HpNumbers hpNumbers, WarGameQueries warGameQueries) {
      _hpNumbers = hpNumbers;
      _coordinateTransformer = new MapCoordinateTransformerImpl();
      _warGameQueries = warGameQueries;
      _graphicsForUnits = new HashMap<LogicalUnit, GraphicalUnit>();
      _hiddenUnits = new HashSet<LogicalUnit>(0);

      _warGameQueries.addListener(this);
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

   @Override
   public void unitJoined(LogicalUnit logicalUnit) {
      _graphicsForUnits.remove(logicalUnit);
      _hiddenUnits.remove(logicalUnit);
   }

   @Override
   public void transportedUnitWasDestroyed(LogicalUnit logicalUnit) {
      _graphicsForUnits.remove(logicalUnit);
      _hiddenUnits.remove(logicalUnit);
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

   public void hideGraphicForUnit(LogicalUnit logicalUnit) {
      _hiddenUnits.add(logicalUnit);
   }

   public void showGraphicForUnit(LogicalUnit logicalUnit) {
      _hiddenUnits.remove(logicalUnit);
   }

   public void makeAllUnitsFaceEnemyHq() {
      for (LogicalUnit logicalUnit : _graphicsForUnits.keySet()) {
         makeUnitFaceEnemyHq(logicalUnit);
      }
   }

   public void makeUnitFaceEnemyHq(LogicalUnit logicalUnit) {
      Faction friendlyFaction = _warGameQueries.getFactionForUnit(logicalUnit);
      Position friendlyHq = _warGameQueries.getHqPosition(friendlyFaction);
      for (Faction otherFaction : _warGameQueries.getFactionsInGame()) {
         if (!friendlyFaction.equals(otherFaction)) {
            Position enemyHq = _warGameQueries.getHqPosition(otherFaction);
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
         if (unitIsHidden(entry.getKey())) {
            continue;
         }
         int hp = entry.getKey().getHp1To10();
         Renderable hpNumber = null;
         if (hp < 10) {
            hpNumber = _hpNumbers.getHpNumberImage(hp);
         }
         entry.getValue().draw(x, y, hpNumber);
      }
   }

   private boolean unitIsHidden(LogicalUnit logicalUnit) {
      return _hiddenUnits.contains(logicalUnit);
   }
}
