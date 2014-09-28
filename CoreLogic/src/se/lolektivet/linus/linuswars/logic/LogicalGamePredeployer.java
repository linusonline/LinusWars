package se.lolektivet.linus.linuswars.logic;

/**
 * Created by Linus on 2014-09-22.
 */
public class LogicalGamePredeployer {
   private final LogicalWarGame _logicalWarGame;
   private final LogicalUnitFactory _unitFactory;

   public LogicalGamePredeployer(LogicalWarGame logicalWarGame) {
      _logicalWarGame = logicalWarGame;
      _unitFactory = new LogicalUnitFactory();
   }

   void addNewUnit(UnitType type, Position position, Faction faction) {
      _logicalWarGame.addUnit(_unitFactory.createLogicalUnit(type), position, faction);
   }
}
