package se.lolektivet.linus.linuswars.graphics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Linus on 2014-09-26.
 */
public class Units {
   private ResourceLoader _resourceLoader;
   private final Map<Faction, Map<UnitType, SpriteSheet>> _unitSpriteSheetCache;

   public Units() {
      _unitSpriteSheetCache = new HashMap<Faction, Map<UnitType, SpriteSheet>>();
      for (Faction faction : Faction.values()) {
         _unitSpriteSheetCache.put(faction, new HashMap<UnitType, SpriteSheet>());
      }
   }

   public void init(ResourceLoader resourceLoader) {
      _resourceLoader = resourceLoader;
   }

   public UnitSprite getUnitSprite(Faction unitFaction, UnitType unitType) {
      UnitSprite unitSprite = new UnitSprite();
      unitSprite.init(getSpriteSheet(unitFaction, unitType));
      return unitSprite;
   }

   private SpriteSheet getSpriteSheet(Faction unitFaction, UnitType unitType) {
      Map<UnitType, SpriteSheet> map = _unitSpriteSheetCache.get(unitFaction);
      SpriteSheet unitSprite = map.get(unitType);
      if (unitSprite == null) {
         unitSprite = loadSpriteSheet(unitFaction, unitType);
         map.put(unitType, unitSprite);
      }
      return unitSprite;
   }

   private SpriteSheet loadSpriteSheet(Faction unitFaction, UnitType unitType) {
      Image rawSpriteSheet = _resourceLoader.getUnitSpriteSheet(unitType.getName(), unitFaction.getName());
      return new SpriteSheet(rawSpriteSheet, 16, 20);
   }
}
