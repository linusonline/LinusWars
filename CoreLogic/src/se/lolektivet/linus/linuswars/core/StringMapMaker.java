package se.lolektivet.linus.linuswars.core;

import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Linus on 2015-11-22.
 */
public class StringMapMaker {
   private final MapMaker _mapMaker;
   private final Map<Character, TerrainType> _terrainMap;
   private final Map<Character, Faction> _factions;
   private final List<Character> _factionCodes = new ArrayList<>(6);

   private int _currentPlaceInRow;
   private int _currentRow;

   public StringMapMaker(MapMaker mapMaker) {
      this(mapMaker, new DefaultFactions().getDefaultFactions());
   }

   public StringMapMaker(MapMaker mapMaker, List<Faction> factions) {
      _mapMaker = mapMaker;
      _terrainMap = new HashMap<>(14);
      _factions = new HashMap<>(5);
      _currentPlaceInRow = 0;
      _currentRow = 0;

      initTerrain(factions);
   }

   private void initTerrain(List<Faction> factions) {
      _terrainMap.put('L', TerrainType.PLAINS);
      _terrainMap.put('W', TerrainType.WOODS);
      _terrainMap.put('M', TerrainType.MOUNTAINS);
      _terrainMap.put('V', TerrainType.RIVER);
      _terrainMap.put('R', TerrainType.ROAD);
      _terrainMap.put('H', TerrainType.SHOAL);
      _terrainMap.put('D', TerrainType.BRIDGE);
      _terrainMap.put('S', TerrainType.SEA);
      _terrainMap.put('E', TerrainType.REEF);

      _terrainMap.put('C', TerrainType.CITY);
      _terrainMap.put('B', TerrainType.BASE);
      _terrainMap.put('A', TerrainType.AIRPORT);
      _terrainMap.put('P', TerrainType.PORT);
      _terrainMap.put('Q', TerrainType.HQ);

      _factionCodes.add('1');
      _factionCodes.add('2');
      _factionCodes.add('3');
      _factionCodes.add('4');
      _factionCodes.add('5');

      for (int f = 0; f < factions.size(); f++) {
         _factions.put(_factionCodes.get(f), factions.get(f));
      }
      _factions.put('o', Faction.ORANGE_STAR);
      _factions.put('b', Faction.BLUE_MOON);
      _factions.put('g', Faction.GREEN_EARTH);
      _factions.put('y', Faction.YELLOW_COMET);
      _factions.put('l', Faction.BLACK_HOLE);
      _factions.put('n', Faction.NEUTRAL);
   }

   private void addTerrain(TerrainType terrainTile, int x, int y) {
      _mapMaker.addTerrain(terrainTile, x, y);
   }

   private void addBuilding(TerrainType buildingType, Faction faction, int x, int y) {
      _mapMaker.addBuilding(buildingType, faction, x, y);
   }

   public void addSingleBuilding(TerrainType type, Faction faction) {
      addBuilding(type, faction, _currentPlaceInRow, _currentRow);
      _currentPlaceInRow++;
   }

   public void addSingleTile(TerrainType type) {
      addTerrain(type, _currentPlaceInRow, _currentRow);
      _currentPlaceInRow++;
   }

   public void nextRow() {
      _currentRow++;
      _currentPlaceInRow = 0;
   }

   public void readRow(String row) {
      int index = 0;
      while (index < row.length()) {
         if (' ' == row.charAt(index)) {
            index++;
            continue;
         }
         TerrainType type = _terrainMap.get(row.charAt(index));
         if (type.isBuilding()) {
            index++;
            Faction faction = _factions.get(row.charAt(index));
            addSingleBuilding(type, faction);
         } else {
            addSingleTile(type);
         }
         index++;
      }
      nextRow();
   }

   public void finish() {
      _mapMaker.finish();
   }
}
