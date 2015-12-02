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
   }

   public void replaceFactions(List<Faction> newFactions) {
      List<Faction> oldFactions = getFactions();
      if (newFactions.size() != oldFactions.size()) {
         throw new InitializationException("Map has " + _basesForFaction.size() + " factions, tried to initialize with " + newFactions.size());
      }

      Map<Faction, Faction> factionReplacementMap = new HashMap<>(_basesForFaction.size());
      for (int i = 0; i < newFactions.size(); i++) {
         factionReplacementMap.put(oldFactions.get(i), newFactions.get(i));
      }
      factionReplacementMap.put(Faction.NEUTRAL, Faction.NEUTRAL);

      {
         Map<Faction, Collection<Base>> newBasesForFaction = new HashMap<>(_basesForFaction.size());
         for (Map.Entry<Faction, Collection<Base>> entry : _basesForFaction.entrySet()) {
            Faction newFaction = factionReplacementMap.get(entry.getKey());
            for (Base base : entry.getValue()) {
               base.setFaction(newFaction);
            }
            newBasesForFaction.put(newFaction, entry.getValue());
         }
         _basesForFaction.clear();
         _basesForFaction.putAll(newBasesForFaction);
      }

      {
         Map<Faction, Position> newHqsOfFactions = new HashMap<>(_basesForFaction.size());
         for (Map.Entry<Faction, Position> entry : _hqsOfFactions.entrySet()) {
            newHqsOfFactions.put(factionReplacementMap.get(entry.getKey()), entry.getValue());
         }
         _hqsOfFactions.clear();
         _hqsOfFactions.putAll(newHqsOfFactions);
      }
   }
}
