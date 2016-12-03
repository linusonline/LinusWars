package se.lolektivet.linus.linuswars.core.enums;

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
   RIVER_T_WEST(TerrainType.RIVER),
   RIVER_T_EAST(TerrainType.RIVER),
   RIVER_X(TerrainType.RIVER),

   ROAD_HORIZONTAL(TerrainType.ROAD),
   ROAD_VERTICAL(TerrainType.ROAD),
   ROAD_BEND_NE(TerrainType.ROAD),
   ROAD_BEND_SW(TerrainType.ROAD),
   ROAD_BEND_NW(TerrainType.ROAD),
   ROAD_BEND_SE(TerrainType.ROAD),
   ROAD_T_SOUTH(TerrainType.ROAD),
   ROAD_T_NORTH(TerrainType.ROAD),
   ROAD_T_WEST(TerrainType.ROAD),
   ROAD_T_EAST(TerrainType.ROAD),
   ROAD_X(TerrainType.ROAD),

   // TODO: Make sprites for Sea, Shoal and Reef tiles.

   SEA(TerrainType.SEA),
   SEA_EDGE_N(TerrainType.SEA),
   SEA_EDGE_S(TerrainType.SEA),
   SEA_EDGE_W(TerrainType.SEA),
   SEA_EDGE_E(TerrainType.SEA),
   SEA_EDGE_NW(TerrainType.SEA),
   SEA_EDGE_NE(TerrainType.SEA),
   SEA_EDGE_SW(TerrainType.SEA),
   SEA_EDGE_SE(TerrainType.SEA),

   SEA_BEND_NW(TerrainType.SEA),
   SEA_BEND_NE(TerrainType.SEA),
   SEA_BEND_SW(TerrainType.SEA),
   SEA_BEND_SE(TerrainType.SEA),
   SEA_T_N(TerrainType.SEA),
   SEA_T_S(TerrainType.SEA),
   SEA_T_W(TerrainType.SEA),
   SEA_T_E(TerrainType.SEA),
   SEA_X(TerrainType.SEA),

   SEA_HORIZONTAL(TerrainType.SEA),
   SEA_VERTICAL(TerrainType.SEA),
   SEA_END_N(TerrainType.SEA),
   SEA_END_S(TerrainType.SEA),
   SEA_END_W(TerrainType.SEA),
   SEA_END_E(TerrainType.SEA),
   SEA_SINGLE(TerrainType.SEA),

   SEA_CORNER_NW(TerrainType.SEA),
   SEA_CORNER_NE(TerrainType.SEA),
   SEA_CORNER_SW(TerrainType.SEA),
   SEA_CORNER_SE(TerrainType.SEA),

   // Names are analogous to sea tiles
   // Corners use same tiles as sea
   SHOAL_EDGE_N(TerrainType.SHOAL),
   SHOAL_EDGE_N_END_W(TerrainType.SHOAL),
   SHOAL_EDGE_N_END_E(TerrainType.SHOAL),
   SHOAL_EDGE_N_END_WE(TerrainType.SHOAL),

   SHOAL_EDGE_S(TerrainType.SHOAL),
   SHOAL_EDGE_S_END_W(TerrainType.SHOAL),
   SHOAL_EDGE_S_END_E(TerrainType.SHOAL),
   SHOAL_EDGE_S_END_WE(TerrainType.SHOAL),

   SHOAL_EDGE_W(TerrainType.SHOAL),
   SHOAL_EDGE_W_END_N(TerrainType.SHOAL),
   SHOAL_EDGE_W_END_S(TerrainType.SHOAL),
   SHOAL_EDGE_W_END_NS(TerrainType.SHOAL),

   SHOAL_EDGE_E(TerrainType.SHOAL),
   SHOAL_EDGE_E_END_N(TerrainType.SHOAL),
   SHOAL_EDGE_E_END_S(TerrainType.SHOAL),
   SHOAL_EDGE_E_END_NS(TerrainType.SHOAL),

   SHOAL_EDGE_NW(TerrainType.SHOAL),
   SHOAL_EDGE_NE(TerrainType.SHOAL),
   SHOAL_EDGE_SW(TerrainType.SHOAL),
   SHOAL_EDGE_SE(TerrainType.SHOAL),

   SHOAL_CORNER_NW(TerrainType.SHOAL),
   SHOAL_CORNER_NE(TerrainType.SHOAL),
   SHOAL_CORNER_SW(TerrainType.SHOAL),
   SHOAL_CORNER_SE(TerrainType.SHOAL),

   SHOAL_END_N(TerrainType.SHOAL),
   SHOAL_END_S(TerrainType.SHOAL),
   SHOAL_END_W(TerrainType.SHOAL),
   SHOAL_END_E(TerrainType.SHOAL),

   REEF(TerrainType.REEF),

   BRIDGE_HORIZONTAL(TerrainType.BRIDGE),
   BRIDGE_VERTICAL(TerrainType.BRIDGE);

   private TerrainType _terrainType;

   TerrainTile(TerrainType terrainType) {
      _terrainType = terrainType;
   }

   public TerrainType getTerrainType() {
      return _terrainType;
   }
}
