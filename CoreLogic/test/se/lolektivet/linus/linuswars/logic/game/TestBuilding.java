package se.lolektivet.linus.linuswars.logic.game;

import org.junit.Assert;
import org.junit.Test;
import se.lolektivet.linus.linuswars.logic.LogicException;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;

/**
 * Created by Linus on 2015-11-29.
 */
public class TestBuilding {

   private Building createBlueMoonBuilding() {
      return Building.create(new Position(2, 2), TerrainType.BASE, Faction.BLUE_MOON);
   }

   @Test
   public void testNewBuildingIsNotBeingCaptured() {
      Building building = createBlueMoonBuilding();
      Assert.assertFalse(building.isCapturing());
   }

   @Test
   public void testBuildingIsBeingCapturedAfterDoCapture() {
      Building building = createBlueMoonBuilding();
      building.doCapture(10, Faction.GREEN_EARTH);
      Assert.assertTrue(building.isCapturing());
   }

   @Test (expected = LogicException.class)
   public void testThrowOnCaptureByOwningFaction() {
      Building building = createBlueMoonBuilding();
      building.doCapture(10, Faction.BLUE_MOON);
   }

   @Test
   public void testCompleteCaptureChangesFaction() {
      Building building = createBlueMoonBuilding();
      building.doCapture(20, Faction.GREEN_EARTH);
      Assert.assertTrue(building.getFaction() == Faction.GREEN_EARTH);
   }

   @Test
   public void testIsNotBeingCapturedAfterReset() {
      Building building = createBlueMoonBuilding();
      building.doCapture(10, Faction.GREEN_EARTH);
      building.resetCapture();
      Assert.assertFalse(building.isCapturing());
   }

   @Test (expected = LogicException.class)
   public void throwOnCaptureByOtherFactionThanCapturing() {
      Building building = createBlueMoonBuilding();
      building.doCapture(10, Faction.GREEN_EARTH);
      building.doCapture(10, Faction.ORANGE_STAR);
   }

   @Test
   public void cannotKeepCapturingAfterReset() {
      Building building = createBlueMoonBuilding();
      building.doCapture(10, Faction.GREEN_EARTH);
      building.resetCapture();
      building.doCapture(10, Faction.GREEN_EARTH);
      Assert.assertTrue(building.getFaction() == Faction.BLUE_MOON);
   }

   @Test
   public void testIsNotCapturingAfterCompleteCapture() {
      Building building = createBlueMoonBuilding();
      building.doCapture(20, Faction.GREEN_EARTH);
      Assert.assertFalse(building.isCapturing());
   }

}
