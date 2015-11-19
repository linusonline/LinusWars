package se.lolektivet.linus.linuswars.logic;

import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;

/**
 * Created by Linus on 2014-09-22.
 */
public class LogicalGamePredeployer {
   private final WarGameSetup _logicalWarGame;
   private final LogicalUnitFactory _unitFactory;

   public LogicalGamePredeployer(WarGameSetup logicalWarGame) {
      _logicalWarGame = logicalWarGame;
      _unitFactory = new LogicalUnitFactory();
   }

   void addNewUnit(UnitType type, Position position, Faction faction, int hpPercent) {
      if (hpPercent < 1 || hpPercent > 100) {
         throw new InternalError("Unit must have 1 to 100 HP%!");
      }
      LogicalUnit logicalUnit = _unitFactory.createLogicalUnit(type);
      logicalUnit.setHp1To100(hpPercent);
      _logicalWarGame.addUnit(logicalUnit, position, faction);
   }

   void addNewUnit(UnitType type, Position position, Faction faction) {
      _logicalWarGame.addUnit(_unitFactory.createLogicalUnit(type), position, faction);
   }
}
