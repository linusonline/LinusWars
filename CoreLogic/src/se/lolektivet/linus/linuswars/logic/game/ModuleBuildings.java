package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.InitializationException;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Linus on 2015-11-27.
 */
public class ModuleBuildings implements BuildingsSetup {
   private static final Logger _logger = Logger.getLogger(ModuleBuildings.class.getName());

   private final Map<Faction, Collection<Building>> _buildingsForFaction;
   private final Map<Position, Building> _buildingAtPosition;
   private final Map<Faction, Position> _hqsOfFactions;

   private List<Faction> _listOfFactions;

   public ModuleBuildings() {
      _buildingsForFaction = new HashMap<>(2);
      _buildingAtPosition = new HashMap<>(2);
      _hqsOfFactions = new HashMap<>(2);
   }

   @Override
   public void addBuilding(Position position, TerrainType buildingType, Faction faction) {
      _logger.info("Adding " + buildingType + " for " + faction + " at " + position);
      if (buildingType == TerrainType.HQ) {
         if (faction == Faction.NEUTRAL) {
            throw new InitializationException("HQs cannot be neutral!");
         }
         if (_hqsOfFactions.get(faction) != null) {
            throw new InitializationException("Only one HQ per faction!");
         }
         _hqsOfFactions.put(faction, position);
      }
      Building newBuilding = Building.create(position, buildingType, faction);
      getBuildingListForFaction(faction).add(newBuilding);
      _buildingAtPosition.put(position, newBuilding);
   }

   private Collection<Building> getBuildingListForFaction(Faction faction) {
      Collection<Building> list = _buildingsForFaction.get(faction);
      if (list == null) {
         list = new ArrayList<>();
         _buildingsForFaction.put(faction, list);
      }
      return list;
   }

   public Collection<Building> getBuildingsForFaction(Faction faction) {
      return new ArrayList<>(getBuildingListForFaction(faction));
   }

   @Override
   public Collection<Building> getAllBuildings() {
      Collection<Building> allBuildings = new HashSet<>();
      for (Collection<Building> factionBuildings : _buildingsForFaction.values()) {
         allBuildings.addAll(factionBuildings);
      }
      return allBuildings;
   }

   public Building getBuildingAtPosition(Position position) {
      return _buildingAtPosition.get(position);
   }

   public boolean hasBuildingAtPosition(Position position) {
      return _buildingAtPosition.containsKey(position);
   }

   public Position getHqPosition(Faction faction) {
      return _hqsOfFactions.get(faction);
   }

   public List<Faction> getFactions() {
      if (_listOfFactions == null) {
         _listOfFactions = new ArrayList<>(_buildingsForFaction.keySet());
         _listOfFactions.remove(Faction.NEUTRAL);
      }
      return new ArrayList<>(_listOfFactions);
   }

   @Override
   public void validateSetup() {
      if (getFactions().size() != _hqsOfFactions.size()) {
         throw new InitializationException("All participating factions must have an HQ!");
      }
      if (getFactions().size() < 2) {
         throw new InitializationException("Setup must have at least two factions with one HQ each!");
      }
   }
}
