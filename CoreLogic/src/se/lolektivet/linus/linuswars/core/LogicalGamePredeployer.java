package se.lolektivet.linus.linuswars.core;

import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.UnitType;
import se.lolektivet.linus.linuswars.core.game.LogicalUnit;
import se.lolektivet.linus.linuswars.core.game.LogicalUnitFactory;
import se.lolektivet.linus.linuswars.core.game.WarGameSetup;

/**
 * Created by Linus on 2014-09-22.
 */
public class LogicalGamePredeployer implements GamePredeployer {
   private final WarGameSetup _logicalWarGame;
   private final LogicalUnitFactory _unitFactory;

   public LogicalGamePredeployer(WarGameSetup logicalWarGame, LogicalUnitFactory logicalUnitFactory) {
      _logicalWarGame = logicalWarGame;
      _unitFactory = logicalUnitFactory;
   }

   @Override
   public void addNewUnit(UnitType type, Faction faction, int x, int y) {
      addNewUnit(type, faction, x, y, 100);
   }

   @Override
   public void addNewUnit(UnitType type, Faction faction, int x, int y, int hpPercent) {
      LogicalUnit unit = createNewUnit(type);
      setHp(unit, hpPercent);
      addUnit(unit, faction, x, y);
   }

   @Override
   public void addNewUnit(UnitType type, Faction faction, int x, int y, int hpPercent, int fuel) {
      LogicalUnit unit = createNewUnit(type);
      setHp(unit, hpPercent);
      setFuelLevel(unit, fuel);
      addUnit(unit, faction, x, y);
   }

   @Override
   public void addNewSubmergedSub(Faction faction, int x, int y, int hpPercent, int fuel) {
      LogicalUnit unit = createNewUnit(UnitType.SUB);
      setHp(unit, hpPercent);
      setFuelLevel(unit, fuel);
      unit.setSubSubmerged(true);
      addUnit(unit, faction, x, y);
   }

   private void addUnit(LogicalUnit unit, Faction faction, int x, int y) {
      _logicalWarGame.addUnit(unit, new Position(x, y), faction);
   }

   private void setFuelLevel(LogicalUnit unit, int fuel) {
      if (fuel > unit.getMaxFuel() || fuel < 0) {
         throw new InternalError("Unit must have 0 to " + unit.getMaxFuel() + " fuel!");
      }
      unit.subtractFuel(unit.getMaxFuel() - fuel);
   }

   private void setHp(LogicalUnit unit, int hpPercent) {
      if (hpPercent < 1 || hpPercent > 100) {
         throw new InternalError("Unit must have 1 to 100 HP%!");
      }
      unit.setHp1To100(hpPercent);
   }

   private LogicalUnit createNewUnit(UnitType type) {
      return _unitFactory.createLogicalUnit(type);
   }
}
