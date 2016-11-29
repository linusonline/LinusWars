package se.lolektivet.linus.linuswars.core.game;

import static org.junit.Assert.*;
import org.junit.Test;
import se.lolektivet.linus.linuswars.core.LinusWarsTest;
import se.lolektivet.linus.linuswars.core.LogicException;
import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;

/**
 * Created by Linus on 2015-11-29.
 */
public class TestBuilding extends LinusWarsTest {

   private BuildingImpl createBlueMoonBuilding() {
      return BuildingImpl.create(new Position(2, 2), TerrainType.BASE, Faction.BLUE_MOON);
   }

   @Test
   public void testNewBuildingIsNotBeingCaptured() {
      BuildingImpl building = createBlueMoonBuilding();
      assertFalse(building.isCapturing());
   }

   @Test
   public void testBuildingIsBeingCapturedAfterDoCapture() {
      BuildingImpl building = createBlueMoonBuilding();
      building.doCapture(10, Faction.GREEN_EARTH);
      assertTrue(building.isCapturing());
   }

   @Test (expected = LogicException.class)
   public void testThrowOnCaptureByOwningFaction() {
      BuildingImpl building = createBlueMoonBuilding();
      building.doCapture(10, Faction.BLUE_MOON);
   }

   @Test
   public void testCompleteCaptureChangesFaction() {
      BuildingImpl building = createBlueMoonBuilding();
      building.doCapture(20, Faction.GREEN_EARTH);
      assertTrue(building.getFaction() == Faction.GREEN_EARTH);
   }

   @Test
   public void testIsNotBeingCapturedAfterReset() {
      BuildingImpl building = createBlueMoonBuilding();
      building.doCapture(10, Faction.GREEN_EARTH);
      building.resetCapture();
      assertFalse(building.isCapturing());
   }

   @Test (expected = LogicException.class)
   public void throwOnCaptureByOtherFactionThanCapturing() {
      BuildingImpl building = createBlueMoonBuilding();
      building.doCapture(10, Faction.GREEN_EARTH);
      building.doCapture(10, Faction.ORANGE_STAR);
   }

   @Test
   public void cannotKeepCapturingAfterReset() {
      BuildingImpl building = createBlueMoonBuilding();
      building.doCapture(10, Faction.GREEN_EARTH);
      building.resetCapture();
      building.doCapture(10, Faction.GREEN_EARTH);
      assertTrue(building.getFaction() == Faction.BLUE_MOON);
   }

   @Test
   public void testIsNotCapturingAfterCompleteCapture() {
      BuildingImpl building = createBlueMoonBuilding();
      building.doCapture(20, Faction.GREEN_EARTH);
      assertFalse(building.isCapturing());
   }
}
