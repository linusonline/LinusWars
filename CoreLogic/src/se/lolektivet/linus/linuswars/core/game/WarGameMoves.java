package se.lolektivet.linus.linuswars.core.game;

import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.UnitType;
import se.lolektivet.linus.linuswars.core.pathfinding.Path;

/**
 * Created by Linus on 2014-09-18.
 */
public interface WarGameMoves {
   // Move sub X along path Y and then submerge/surface
   void executeMove(LogicalUnit logicalUnit, Path path);
   void executeAttackMove(LogicalUnit movingUnit, Path path, LogicalUnit attackedUnit);
   void executeSupplyMove(LogicalUnit movingUnit, Path path);
   void executeLoadMove(LogicalUnit movingUnit, Path path);
   void executeUnloadMove(LogicalUnit transport, LogicalUnit unloadingUnit, Path movementPath, Position unloadPosition);
   void executeJoinMove(LogicalUnit movingUnit, Path movementPath);
   void executeCaptureMove(LogicalUnit movingUnit, Path movementPath);
   void executeDeployMove(Position position, UnitType unitType);
   void endTurn();
}
