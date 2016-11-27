package se.lolektivet.linus.linuswars.graphics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.UnitType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Linus on 2014-09-26.
 */
class Units {
   private ResourceLoader _resourceLoader;
   private final Map<Faction, Map<UnitType, SpriteSheet>> _unitSpriteSheetCache;

   Units() {
      _unitSpriteSheetCache = new HashMap<>();
      for (Faction faction : Faction.values()) {
         _unitSpriteSheetCache.put(faction, new HashMap<>());
      }
   }

   void init(ResourceLoader resourceLoader) {
      _resourceLoader = resourceLoader;
   }

   UnitSprite getUnitSprite(Faction unitFaction, UnitType unitType) {
      UnitSprite unitSprite = new UnitSprite();
      unitSprite.init(getSpriteSheet(unitFaction, unitType));
      return unitSprite;
   }

   private SpriteSheet getSpriteSheet(Faction unitFaction, UnitType unitType) {
      Map<UnitType, SpriteSheet> map = _unitSpriteSheetCache.get(unitFaction);
      SpriteSheet unitSpriteSheet = map.get(unitType);
      if (unitSpriteSheet == null) {
         unitSpriteSheet = loadSpriteSheet(unitFaction, unitType);
         map.put(unitType, unitSpriteSheet);
      }
      return unitSpriteSheet;
   }

   private SpriteSheet loadSpriteSheet(Faction unitFaction, UnitType unitType) {
      Image rawSpriteSheet = _resourceLoader.getUnitSpriteSheet(unitType.getName(), unitFaction.getName());
      return new SpriteSheet(rawSpriteSheet, 16, 20);
   }
}
