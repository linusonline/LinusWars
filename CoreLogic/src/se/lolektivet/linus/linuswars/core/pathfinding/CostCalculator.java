package se.lolektivet.linus.linuswars.core.pathfinding;

import se.lolektivet.linus.linuswars.core.Position;

/**
 * Created by Linus on 2014-09-23.
 */
public interface CostCalculator {
   Cost getCostForPosition(Position position);
}
