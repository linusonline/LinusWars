package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.InitializationException;
import se.lolektivet.linus.linuswars.logic.LogicException;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;

import java.util.*;

/**
 * Created by Linus on 2015-11-27.
 */
public class ModuleBases {
   private final Map<Faction, Collection<Base>> _basesForFaction;
   private final Map<Position, Base> _baseAtPosition;
   private final Map<Faction, Position> _hqsOfFactions;

   private List<Faction> _listOfFactions;

   public ModuleBases() {
      _basesForFaction = new HashMap<>(2);
      _baseAtPosition = new HashMap<>(2);
      _hqsOfFactions = new HashMap<>(2);
   }

   public void addBase(Position position, TerrainType buildingType, Faction faction) {
      if (buildingType == TerrainType.HQ) {
         if (faction == Faction.NEUTRAL) {
            throw new InitializationException("HQs cannot be neutral!");
         }
         if (_hqsOfFactions.get(faction) != null) {
            throw new InitializationException("Only one HQ per faction!");
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

   public List<Faction> getFactions() {
      if (_listOfFactions == null) {
         _listOfFactions = new ArrayList<>(_basesForFaction.keySet());
         _listOfFactions.remove(Faction.NEUTRAL);
      }
      return new ArrayList<>(_listOfFactions);
   }

   void validateSetup() {
      if (getFactions().size() != _hqsOfFactions.size()) {
         throw new InitializationException("All participating factions must have an HQ!");
      }
      if (getFactions().size() < 2) {
         throw new InitializationException("Setup must have at least two factions with one HQ each!");
      }
   }
}
