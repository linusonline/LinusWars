package se.lolektivet.linus.linuswars.logic.enums;

/**
* Created by Linus on 2014-09-21.
*/
public enum TerrainTile {
   PLAIN(TerrainType.PLAINS),
   WOODS_SINGLE(TerrainType.WOODS),
   MOUNTAIN_SMALL(TerrainType.MOUNTAINS),
   WOODS_4X4_NW(TerrainType.WOODS),
   WOODS_4X4_NE(TerrainType.WOODS),
   WOODS_4X4_SW(TerrainType.WOODS),
   WOODS_4X4_SE(TerrainType.WOODS),
   RIVER_HORIZONTAL(TerrainType.RIVER),
   RIVER_VERTICAL(TerrainType.RIVER),
   RIVER_BEND_NW(TerrainType.RIVER),
   RIVER_BEND_NE(TerrainType.RIVER),
   RIVER_BEND_SW(TerrainType.RIVER),
   RIVER_BEND_SE(TerrainType.RIVER),
   RIVER_T_SOUTH(TerrainType.RIVER),
   RIVER_T_NORTH(TerrainType.RIVER),
   ROAD_HORIZONTAL(TerrainType.ROAD),
   ROAD_VERTICAL(TerrainType.ROAD),
   ROAD_BEND_NE(TerrainType.ROAD),
   ROAD_BEND_SW(TerrainType.ROAD),
   ROAD_BEND_NW(TerrainType.ROAD),
   ROAD_BEND_SE(TerrainType.ROAD),
   BRIDGE_HORIZONTAL(TerrainType.ROAD);

   private TerrainType _terrainType;

   TerrainTile(TerrainType terrainType) {
      _terrainType = terrainType;
   }

   public TerrainType getTerrainType() {
      return _terrainType;
   }
}
