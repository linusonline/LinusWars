package se.lolektivet.linus.linuswars.core.map;

import se.lolektivet.linus.linuswars.core.IllegalMapException;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.TerrainTile;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;

/**
 * Created by Linus on 2015-11-23.
 */
public class RowMapMaker {
   private MapMaker _mapMaker;

   private int _currentPlaceInRow;
   private int _currentRow;
   private boolean _widthDetermined;
   private int _mapWidth;
   private boolean _buildingAdded;
   private int _expectedNumberOfRows;

   public RowMapMaker() {
      _currentPlaceInRow = 0;
      _currentRow = 0;
      _widthDetermined = false;
      _buildingAdded = false;
   }

   public void init(MapMaker mapMaker, int totalNumberOfRows) {
      _mapMaker = mapMaker;
      _expectedNumberOfRows = totalNumberOfRows;
   }

   public void addTerrain(TerrainTile terrainTile) {
      addTerrain(terrainTile.getTerrainType());
   }

   public void addTerrain(TerrainType terrainType) {
      if (_buildingAdded) {
         throw new IllegalMapException("You must add all terrain before you start adding buildings!");
      }
      _mapMaker.addTerrain(terrainType, _currentPlaceInRow, _currentRow);
      _currentPlaceInRow++;
   }

   public void nextRow() {
      if (_widthDetermined) {
         if (_mapWidth != _currentPlaceInRow) {
            throw new IllegalMapException("All rows in map must be the same length!");
         }
      } else {
         _mapWidth = _currentPlaceInRow;
      }
      _currentRow++;
      _currentPlaceInRow = 0;
   }

   public void addBuilding(TerrainType buildingType, Faction faction, int x, int y) {
      if (!isValid()) {
         throw new IllegalMapException("You must complete the map (all rows the same length, end with nextRow) before you start adding buildings!");
      }
      if (x < 0 || x > _mapWidth || y < 0 || y > _currentRow) {
         throw new IllegalMapException("Building is outside map!");
      }
      _mapMaker.addBuilding(buildingType, faction, x, y);
      _buildingAdded = true;
   }

   public void finish() {
      if (!isValid()) {
         throw new IllegalMapException("Map is invalid! All rows must be the same length, end with nextRow.");
      }
      _mapMaker.finish();
   }

   private boolean isValid() {
      return (!_widthDetermined || _mapWidth == _currentPlaceInRow)
            && _currentRow == _expectedNumberOfRows;
   }
}
