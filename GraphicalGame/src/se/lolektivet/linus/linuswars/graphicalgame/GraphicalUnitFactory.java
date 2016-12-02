package se.lolektivet.linus.linuswars.graphicalgame;

import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.graphics.UnitSprite;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.UnitType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Linus on 2014-09-26.
 */
public class GraphicalUnitFactory {

   private Sprites _sprites;
   private final Map<Faction, Map<UnitType, UnitSprite>> _unitSpriteCache;

   public GraphicalUnitFactory() {
      _unitSpriteCache = new HashMap<>();
      for (Faction faction : Faction.values()) {
         _unitSpriteCache.put(faction, new HashMap<>());
      }
   }

   public void init(Sprites sprites) {
      _sprites = sprites;
   }

   public GraphicalUnit getGraphicalUnit(Faction unitFaction, UnitType unitType) {
      return new GraphicalUnit(getUnitSprite(unitFaction, unitType));
   }

   private UnitSprite getUnitSprite(Faction unitFaction, UnitType unitType) {
      Map<UnitType, UnitSprite> map = _unitSpriteCache.get(unitFaction);
      return map.computeIfAbsent(unitType, k -> loadUnitSprite(unitFaction, unitType));
   }

   private UnitSprite loadUnitSprite(Faction unitFaction, UnitType unitType) {
      return _sprites.getUnitSprite(unitFaction, unitType);
   }
}
