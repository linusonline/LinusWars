package se.lolektivet.linus.linuswars.core.game;

import org.junit.Assert;
import org.junit.Test;
import se.lolektivet.linus.linuswars.core.InitializationException;
import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;

import java.util.Collection;

/**
 * Created by Linus on 2015-11-27.
 */
public class TestModuleBuildings {

   @Test
   public void getBuildingsForFactionReturnsEmptyByDefault() {
      ModuleBuildings buildingsModule = new ModuleBuildings();
      Collection<Building> buildings =  buildingsModule.getBuildingsForFaction(Faction.BLUE_MOON);
      Assert.assertTrue(buildings.isEmpty());
   }

   @Test
   public void getBuildingsForFactionReturnsEmptyForWrongFaction() {
      ModuleBuildings buildingsModule = new ModuleBuildings();
      buildingsModule.addBuilding(new Position(2, 2), TerrainType.CITY, Faction.BLUE_MOON);
      Collection<Building> buildings =  buildingsModule.getBuildingsForFaction(Faction.GREEN_EARTH);
      Assert.assertTrue(buildings.isEmpty());
   }

   @Test
   public void getBuildingsForFactionReturnsAddedBuilding() {
      ModuleBuildings buildingsModule = new ModuleBuildings();
      buildingsModule.addBuilding(new Position(2, 2), TerrainType.CITY, Faction.BLUE_MOON);
      Collection<Building> buildings =  buildingsModule.getBuildingsForFaction(Faction.BLUE_MOON);
      Assert.assertTrue(buildings.size() == 1);
   }

   @Test
   public void keepsTrackOfHq() {
      ModuleBuildings buildingsModule = new ModuleBuildings();
      Position hqPosition = new Position(2, 2);
      buildingsModule.addBuilding(hqPosition, TerrainType.HQ, Faction.BLUE_MOON);
      Position position = buildingsModule.getHqPosition(Faction.BLUE_MOON);
      Assert.assertTrue(position != null);
      Assert.assertEquals(position, hqPosition);
   }

   @Test
   public void hasBuildingAtPositionWorks() {
      ModuleBuildings buildingsModule = new ModuleBuildings();
      Position hqPosition = new Position(2, 2);
      buildingsModule.addBuilding(hqPosition, TerrainType.HQ, Faction.BLUE_MOON);

      Assert.assertTrue(buildingsModule.hasBuildingAtPosition(hqPosition));
   }

   @Test (expected = InitializationException.class)
   public void throwsOnTwoHqsForSameFaction() {
      ModuleBuildings buildingsModule = new ModuleBuildings();
      buildingsModule.addBuilding(new Position(1, 1), TerrainType.HQ, Faction.BLUE_MOON);
      buildingsModule.addBuilding(new Position(1, 2), TerrainType.HQ, Faction.BLUE_MOON);
   }

   @Test (expected = InitializationException.class)
   public void throwsOnNeutralHq() {
      ModuleBuildings buildingsModule = new ModuleBuildings();
      buildingsModule.addBuilding(new Position(1, 1), TerrainType.HQ, Faction.NEUTRAL);
   }

   @Test
   public void validationAcceptsMinimumSetup() {
      ModuleBuildings buildingsModule = new ModuleBuildings();
      buildingsModule.addBuilding(new Position(1, 2), TerrainType.HQ, Faction.BLUE_MOON);
      buildingsModule.addBuilding(new Position(1, 2), TerrainType.HQ, Faction.GREEN_EARTH);
      buildingsModule.validateSetup();
   }

   @Test (expected = InitializationException.class)
   public void validationRejectsSingleHq() {
      ModuleBuildings buildingsModule = new ModuleBuildings();
      buildingsModule.addBuilding(new Position(1, 2), TerrainType.HQ, Faction.BLUE_MOON);
      buildingsModule.validateSetup();
   }

   @Test (expected = InitializationException.class)
   public void validationRejectsOrphanBuilding() {
      ModuleBuildings basesModule = new ModuleBuildings();
      basesModule.addBuilding(new Position(1, 1), TerrainType.HQ, Faction.BLUE_MOON);
      basesModule.addBuilding(new Position(2, 2), TerrainType.HQ, Faction.ORANGE_STAR);
      basesModule.addBuilding(new Position(2, 2), TerrainType.BASE, Faction.GREEN_EARTH);
      basesModule.validateSetup();
   }

}
