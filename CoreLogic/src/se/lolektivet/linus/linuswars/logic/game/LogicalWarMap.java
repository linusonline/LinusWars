package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;

/**
 * Created by Linus on 2015-12-20.
 */
public interface LogicalWarMap {
   TerrainType getTerrainForTile(Position tile);

   int getWidth();

   int getHeight();

   boolean isPositionInsideMap(Position position);
}
