package se.lolektivet.linus.linuswars.logic;

import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainTile;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;

/**
 * Created by Linus on 2015-11-23.
 */
public class RowMapMaker {
   private final MapMaker _mapMaker;

   private int _currentPlaceInRow;
   private int _currentRow;
   private boolean _widthDetermined;
   private int _mapWidth;
   private boolean _buildingAdded;

   public RowMapMaker(MapMaker mapMaker) {
      _mapMaker = mapMaker;
      _currentPlaceInRow = 0;
      _currentRow = 0;
      _widthDetermined = false;
      _buildingAdded = false;
   }

   public void addTerrain(TerrainTile terrainTile) {
      if (_buildingAdded) {
         throw new InitializationException("You must add all terrain before you start adding buildings!");
      }
      _mapMaker.addTerrain(terrainTile, _currentPlaceInRow, _currentRow);
      _currentPlaceInRow++;
   }

   public void addBuilding(TerrainType buildingType, Faction faction, int x, int y) {
      if (!isValid()) {
         throw new InitializationException("You must complete the map (all rows the same length) before you start adding buildings!");
      }
      _mapMaker.addBuilding(buildingType, faction, x, y);
      _buildingAdded = true;
   }

   private boolean isValid() {
      return !_widthDetermined || _mapWidth == _currentPlaceInRow;
   }

   public void nextRow() {
      if (_widthDetermined) {
         if (_mapWidth != _currentPlaceInRow) {
            throw new InitializationException("All rows in map must be the same length!");
         }
      } else {
         _mapWidth = _currentPlaceInRow;
      }
      _currentRow++;
      _currentPlaceInRow = 0;
   }
}
