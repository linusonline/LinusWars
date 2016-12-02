package se.lolektivet.linus.linuswars.graphicalgame;

import org.newdawn.slick.Renderable;
import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.core.InitializationException;
import se.lolektivet.linus.linuswars.core.LogicalMapMaker;
import se.lolektivet.linus.linuswars.core.MapMaker;
import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.TerrainTile;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;
import se.lolektivet.linus.linuswars.core.game.LogicalWarMapImpl;
import se.lolektivet.linus.linuswars.core.game.ModuleBuildings;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Linus on 2014-09-22.
 */
public class GraphicalMapMaker implements MapMaker {
   private final GraphicalWarMap _graphicalWarMap;
   private final LogicalMapMaker _logicalMapMaker;
   private final Sprites _sprites;
   private final LogicalWarMapImpl _logicalWarMap;
   private final Map<Position, Renderable> _buildings = new HashMap<>();

   private boolean _onTheFlyDetermined = false;
   private boolean _buildOnTheFly;

   public GraphicalMapMaker(Sprites sprites, GraphicalWarMap graphicalWarMap) {
      _graphicalWarMap = graphicalWarMap;
      _sprites = sprites;
      _logicalWarMap = new LogicalWarMapImpl(new ModuleBuildings());
      _logicalMapMaker = new LogicalMapMaker(_logicalWarMap);
   }

   @Override
   public void addTerrain(TerrainTile terrainTile, int x, int y) {
      if (_onTheFlyDetermined && _buildOnTheFly) {
         throw new InitializationException("GraphicalWarMap may be built on-the-fly or not, but not both!");
      }
      _buildOnTheFly = false;
      _onTheFlyDetermined = true;
      _graphicalWarMap.addTerrain(_sprites.getTerrainSprite(terrainTile), x, y);
   }

   @Override
   public void addTerrain(TerrainType terrainType, int x, int y) {
      if (_onTheFlyDetermined && !_buildOnTheFly) {
         throw new InitializationException("GraphicalWarMap may be built on-the-fly or not, but not both!");
      }
      _buildOnTheFly = true;
      _onTheFlyDetermined = true;
      _logicalMapMaker.addTerrain(terrainType, x, y);
   }

   @Override
   public void addBuilding(TerrainType buildingType, Faction faction, int x, int y) {
      if (!_onTheFlyDetermined) {
         _buildOnTheFly = true;
         _onTheFlyDetermined = true;
      }
      if (_buildOnTheFly) {
         _buildings.put(new Position(x, y), _sprites.getBuildingSprite(buildingType, faction));
         _logicalMapMaker.addBuilding(buildingType, faction, x, y);
      } else {
         _graphicalWarMap.addBuilding(_sprites.getBuildingSprite(buildingType, faction), x, y);
      }
   }

   @Override
   public void finish() {
      if (_buildOnTheFly) {
         TerrainMap terrainMap = new TerrainMapCreator().create(_logicalWarMap);
         for (Map.Entry<Position, TerrainTile> entry : terrainMap.entrySet()) {
            _graphicalWarMap.addTerrain(_sprites.getTerrainSprite(entry.getValue()), entry.getKey().getX(), entry.getKey().getY());
         }
         for (Map.Entry<Position, Renderable> entry : _buildings.entrySet()) {
            _graphicalWarMap.addBuilding(entry.getValue(), entry.getKey().getX(), entry.getKey().getY());
         }
      }
   }
}
