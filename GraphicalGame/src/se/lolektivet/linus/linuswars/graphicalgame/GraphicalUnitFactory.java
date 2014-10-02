package se.lolektivet.linus.linuswars.graphicalgame;

import se.lolektivet.linus.linuswars.graphics.ResourceLoader;
import se.lolektivet.linus.linuswars.graphics.UnitSprite;
import se.lolektivet.linus.linuswars.graphics.Units;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Linus on 2014-09-26.
 */
public class GraphicalUnitFactory {

   private Units _unitSpriteLoader;
   private final Map<Faction, Map<UnitType, UnitSprite>> _unitSpriteCache;

   public GraphicalUnitFactory() {
      _unitSpriteCache = new HashMap<Faction, Map<UnitType, UnitSprite>>();
      for (Faction faction : Faction.values()) {
         _unitSpriteCache.put(faction, new HashMap<UnitType, UnitSprite>());
      }
   }

   public void init(ResourceLoader resourceLoader) {
      _unitSpriteLoader = new Units();
      _unitSpriteLoader.init(resourceLoader);
   }

   public GraphicalUnit getGraphicalUnit(Faction unitFaction, UnitType unitType) {
      return new GraphicalUnit(getUnitSprite(unitFaction, unitType));
   }

   private UnitSprite getUnitSprite(Faction unitFaction, UnitType unitType) {
      Map<UnitType, UnitSprite> map = _unitSpriteCache.get(unitFaction);
      UnitSprite unitSprite = map.get(unitType);
      if (unitSprite == null) {
         unitSprite = loadUnitSprite(unitFaction, unitType);
         map.put(unitType, unitSprite);
      }
      return unitSprite;
   }

   private UnitSprite loadUnitSprite(Faction unitFaction, UnitType unitType) {
      return _unitSpriteLoader.getUnitSprite(unitFaction, unitType);
   }
}
