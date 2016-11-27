package se.lolektivet.linus.linuswars.graphicalgame;

import org.newdawn.slick.*;
import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.core.game.Building;
import se.lolektivet.linus.linuswars.core.game.LogicalUnit;
import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.game.WarGameListener;
import se.lolektivet.linus.linuswars.core.game.WarGameQueries;
import se.lolektivet.linus.linuswars.core.enums.Direction;
import se.lolektivet.linus.linuswars.core.enums.Faction;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Linus on 2014-09-26.
 */
public class GraphicalWarGame implements WarGameListener {
   private final Map<LogicalUnit, GraphicalUnit> _graphicsForUnits;
   private final Set<LogicalUnit> _hiddenUnits;
   private final WarGameQueries _warGameQueries;
   private final GraphicalUnitFactory _unitFactory;
   private GraphicalWarMap _theMap;
   private Sprites _sprites;
   private static final int HUD_OFFSET_HORIZONTAL = 8;
   private static final int HUD_OFFSET_VERTICAL = 8;
   private boolean _hudIsOnTheLeft = false;

   public GraphicalWarGame(WarGameQueries warGameQueries, GraphicalUnitFactory unitFactory) {
      _warGameQueries = warGameQueries;
      _graphicsForUnits = new HashMap<>();
      _hiddenUnits = new HashSet<>(0);
      _unitFactory = unitFactory;

      _warGameQueries.addListener(this);
   }

   public void init(Sprites sprites) {
      _sprites = sprites;
      _unitFactory.init(sprites);
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

   @Override
   public void buildingWasCaptured(Building building) {
      int posX = building.getPosition().getX();
      int posY = building.getPosition().getY();
      _theMap.addBuilding(_sprites.getBuildingSprite(building.getBuildingType(), building.getFaction()), posX, posY);
   }

   @Override
   public void unitDeployed(LogicalUnit logicalUnit, Position position) {
      addUnit(_unitFactory.getGraphicalUnit(_warGameQueries.getFactionForUnit(logicalUnit), logicalUnit.getType()), position);
   }

   public void setHudOnLeft(boolean left) {
      _hudIsOnTheLeft = left;
   }

   public void addUnit(GraphicalUnit graphicalUnit, Position position) {
      graphicalUnit.setTilePosition(position);
      _graphicsForUnits.put(_warGameQueries.getUnitAtPosition(position), graphicalUnit);
   }

   public GraphicalUnit getGraphicForUnit(LogicalUnit unit) {
      return _graphicsForUnits.get(unit);
   }

   public void setPositionOfGraphicForUnit(LogicalUnit logicalUnit, Position newPosition) {
      setUnitPosition(getGraphicForUnit(logicalUnit), newPosition);
   }

   public void resetUnitGraphicToUnitPosition(LogicalUnit logicalUnit) {
      setUnitPosition(getGraphicForUnit(logicalUnit), _warGameQueries.getPositionOfUnit(logicalUnit));
   }

   private void setUnitPosition(GraphicalUnit graphicalUnit, Position newPosition) {
      graphicalUnit.setTilePosition(newPosition);
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

   private void makeUnitFaceEnemyHq(LogicalUnit logicalUnit) {
      Position unitPosition = _warGameQueries.getPositionOfUnit(logicalUnit);
      for (Faction otherFaction : _warGameQueries.getFactionsInGame()) {
         if (_warGameQueries.areEnemies(logicalUnit, otherFaction)) {
            Position enemyHq = _warGameQueries.getHqPosition(otherFaction);
            Direction directionToFace = enemyHq.getX() < unitPosition.getX() ? Direction.LEFT : Direction.RIGHT;
            _graphicsForUnits.get(logicalUnit).setDirection(directionToFace);
         }
      }
   }

   public void drawMap(TileView tileView) {
      _theMap.draw(tileView);
   }

   public void drawUnits(TileView tileView, int x, int y) {
      for (Map.Entry<LogicalUnit, GraphicalUnit> entry : _graphicsForUnits.entrySet()) {
         if (unitIsHidden(entry.getKey())) {
            continue;
         }
         int hp = entry.getKey().getHp1To10();
         Renderable hpNumber = null;
         if (hp < 10) {
            hpNumber = _sprites.getHpNumberImage(hp);
         }
         if (_warGameQueries.unitHasMovedThisTurn(entry.getKey())) {
            entry.getValue().drawGreyed(x, y, hpNumber, tileView);
         } else {
            entry.getValue().draw(x, y, hpNumber, tileView);
         }
      }
   }

   public void drawHud(TileView tileView, int x, int y) {
      Image pane = _sprites.getMoneyCounterPane();
      int hudHorizontalOffset = _hudIsOnTheLeft ? HUD_OFFSET_HORIZONTAL : tileView.getVisiblePixelWidth() - HUD_OFFSET_HORIZONTAL - pane.getWidth();
      pane.draw(x + hudHorizontalOffset, y + HUD_OFFSET_VERTICAL);
      drawMoneyNumber(_warGameQueries.getMoneyForFaction(_warGameQueries.getCurrentlyActiveFaction()), x + hudHorizontalOffset + 55, y + HUD_OFFSET_VERTICAL + 3);
   }

   private void drawMoneyNumber(int nr, int x, int y) {
      int maxDigits = 6;
      int nrDigits = 0;
      do {
         _sprites.getMoneyNumberImage(nr % 10).draw(x, y);
         nr = nr / 10;
         x -= 8;
         nrDigits++;
      } while (nr > 0 && nrDigits < maxDigits);
   }

   private boolean unitIsHidden(LogicalUnit logicalUnit) {
      return _hiddenUnits.contains(logicalUnit);
   }
}
