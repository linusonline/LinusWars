package se.lolektivet.linus.linuswars.core.game;

import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;

import java.util.Collection;

/**
 * Created by Linus on 2015-12-27.
 */
public interface BuildingsSetup {
   void addBuilding(Position position, TerrainType buildingType, Faction faction);
   Collection<Building> getAllBuildings();
   void validateSetup();
}
