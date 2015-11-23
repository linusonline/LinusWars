package se.lolektivet.linus.linuswars.logic.enums;

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

   CITY,
   BASE,
   AIRPORT,
   PORT,
   HQ,

   SEA,
   REEF;

   boolean isBuilding() {
      return equals(CITY) ||
            equals(BASE) ||
            equals(AIRPORT) ||
            equals(PORT) ||
            equals(HQ);
   }
}
