package se.lolektivet.linus.linuswars.graphics;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SpriteSheet;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Linus on 2014-09-21.
 */
public class Buildings {
   private SpriteSheet _spriteSheet;
   private final Map<Faction, Integer> _spriteRowForFaction;
   private final Map<TerrainType, Integer> _spriteColumnForBuildingType;
   private final List<TerrainType> _allowedTypes;

   public Buildings() {
      _spriteRowForFaction = new HashMap<Faction, Integer>(6);
      _spriteRowForFaction.put(Faction.ORANGE_STAR, 0);
      _spriteRowForFaction.put(Faction.GREEN_EARTH, 1);
      _spriteRowForFaction.put(Faction.BLUE_MOON, 2);
      _spriteRowForFaction.put(Faction.YELLOW_COMET, 3);
      _spriteRowForFaction.put(Faction.BLACK_HOLE, 4);
      _spriteColumnForBuildingType = new HashMap<TerrainType, Integer>(5);
      _spriteColumnForBuildingType.put(TerrainType.HQ, 0);
      _spriteColumnForBuildingType.put(TerrainType.CITY, 1);
      _spriteColumnForBuildingType.put(TerrainType.BASE, 3);
      _spriteColumnForBuildingType.put(TerrainType.AIRPORT, 5);
      _spriteColumnForBuildingType.put(TerrainType.PORT, 7);
      _allowedTypes = new ArrayList<TerrainType>(5);
      _allowedTypes.add(TerrainType.HQ);
      _allowedTypes.add(TerrainType.CITY);
      _allowedTypes.add(TerrainType.BASE);
      _allowedTypes.add(TerrainType.AIRPORT);
      _allowedTypes.add(TerrainType.PORT);
   }

   public void init(ResourceLoader resourceLoader) {
      _spriteSheet = new SpriteSheet(resourceLoader.getBuildingsSpriteSheet(), 19, 31);
   }

   public Renderable getBuildingSprite(TerrainType buildingType, Faction faction) {
      if (!_allowedTypes.contains(buildingType)) {
         throw new SpriteNotFoundException();
      }
      int row = _spriteRowForFaction.get(faction);
      int column = _spriteColumnForBuildingType.get(buildingType);
      if (faction.equals(Faction.NEUTRAL)) {
         if (buildingType.equals(TerrainType.HQ)) {
            throw new SpriteNotFoundException();
         }
         return _spriteSheet.getSubImage(column, row);
      }
      if (buildingType.equals(TerrainType.HQ)) {
         return _spriteSheet.getSubImage(column, row);
      }
      return new Animation(_spriteSheet, column, row, column + 1, row, true, 1000, false);
   }
}
