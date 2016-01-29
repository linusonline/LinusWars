package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;

import java.util.Collection;

/**
 * Created by Linus on 2015-12-27.
 */
public interface BuildingsSetup {
   void addBuilding(Position position, TerrainType buildingType, Faction faction);
   Collection<Building> getAllBuildings();
   void validateSetup();
}
