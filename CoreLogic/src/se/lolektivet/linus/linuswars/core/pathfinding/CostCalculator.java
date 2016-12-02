package se.lolektivet.linus.linuswars.core.pathfinding;

import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.game.LogicalUnit;
import se.lolektivet.linus.linuswars.core.game.WarGameQueries;

/**
 * Created by Linus on 2014-09-23.
 */
public interface CostCalculator {
   Cost getCostForPosition(Position position);

   static CostCalculator createForUnitAndGame(LogicalUnit logicalUnit, WarGameQueries queries) {
      return new CostCalculator() {
         @Override
         public Cost getCostForPosition(Position position) {
            InfiniteInteger movementCost = queries.getTravelCostForUnitOnTile(logicalUnit, position);
            InfiniteInteger fuelCost = queries.getFuelCostForUnitOnTile(logicalUnit, position);
            return new Cost(movementCost, fuelCost);
         }
      };
   }
}
