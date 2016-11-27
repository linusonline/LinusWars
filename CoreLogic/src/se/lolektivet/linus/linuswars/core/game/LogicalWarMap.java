package se.lolektivet.linus.linuswars.core.game;

import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;

/**
 * Created by Linus on 2015-12-20.
 */
public interface LogicalWarMap {
   boolean hasTerrainForTile(Position tile);
   TerrainType getTerrainForTile(Position tile);

   int getWidth();

   int getHeight();

   boolean isPositionInsideMap(Position position);
}
