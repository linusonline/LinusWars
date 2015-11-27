package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.LogicException;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Linus on 2015-11-27.
 */
public class ModuleBases {
   private final Map<Faction, Collection<Base>> _basesForFaction;
   private final Map<Position, Base> _baseAtPosition;
   private final Map<Faction, Position> _hqsOfFactions;

   public ModuleBases() {
      _basesForFaction = new HashMap<>(2);
      _baseAtPosition = new HashMap<>(2);
      _hqsOfFactions = new HashMap<>(2);
   }

   public void addBase(Position position, TerrainType buildingType, Faction faction) {
      if (buildingType == TerrainType.HQ) {
         if (faction == Faction.NEUTRAL) {
            throw new LogicException("HQs cannot be neutral!");
         }
         _hqsOfFactions.put(faction, position);
      }
      Base newBase = Base.create(position, buildingType, faction);
      getBaseListForFaction(faction).add(newBase);
      _baseAtPosition.put(position, newBase);
   }

   private Collection<Base> getBaseListForFaction(Faction faction) {
      Collection<Base> list = _basesForFaction.get(faction);
      if (list == null) {
         list = new ArrayList<>();
         _basesForFaction.put(faction, list);
      }
      return list;
   }

   public Collection<Base> getBasesForFaction(Faction faction) {
      return new ArrayList<>(getBaseListForFaction(faction));
   }

   public Base getBaseAtPosition(Position position) {
      return _baseAtPosition.get(position);
   }

   public boolean hasBaseAtPosition(Position position) {
      return _baseAtPosition.containsKey(position);
   }

   public Position getHqPosition(Faction faction) {
      return _hqsOfFactions.get(faction);
   }

   public int getNumberOfHqs() {
      return _hqsOfFactions.size();
   }
}
