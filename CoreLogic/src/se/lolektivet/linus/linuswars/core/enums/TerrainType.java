package se.lolektivet.linus.linuswars.core.enums;

/**
 * Created by Linus on 2014-09-18.
 */
public enum TerrainType {
   ROAD,
   RIVER,
   SHOAL,
   PLAINS,
   WOODS,
   MOUNTAINS,
   BRIDGE,

   CITY,
   BASE,
   AIRPORT,
   PORT,
   HQ,

   SEA,
   REEF;

   public boolean isBase() {
      return this == BASE ||
            this == AIRPORT ||
            this == PORT;
   }

   public boolean isBuilding() {
      return this == CITY ||
            this == HQ ||
            isBase();
   }
}
