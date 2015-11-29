import org.junit.Assert;
import org.junit.Test;
import se.lolektivet.linus.linuswars.logic.LogicException;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;
import se.lolektivet.linus.linuswars.logic.game.Base;

/**
 * Created by Linus on 2015-11-29.
 */
public class TestBase {

   private Base createBlueMoonBase() {
      return Base.create(new Position(2, 2), TerrainType.BASE, Faction.BLUE_MOON);
   }

   @Test
   public void testNewBaseIsNotBeingCaptured() {
      Base base = createBlueMoonBase();
      Assert.assertFalse(base.isCapturing());
   }

   @Test
   public void testBaseIsBeingCapturedAfterDoCapture() {
      Base base = createBlueMoonBase();
      base.doCapture(10, Faction.GREEN_EARTH);
      Assert.assertTrue(base.isCapturing());
   }

   @Test (expected = LogicException.class)
   public void testThrowOnCaptureByOwningFaction() {
      Base base = createBlueMoonBase();
      base.doCapture(10, Faction.BLUE_MOON);
   }

   @Test
   public void testCompleteCaptureChangesFaction() {
      Base base = createBlueMoonBase();
      base.doCapture(20, Faction.GREEN_EARTH);
      Assert.assertTrue(base.getFaction() == Faction.GREEN_EARTH);
   }

   @Test
   public void testIsNotBeingCapturedAfterReset() {
      Base base = createBlueMoonBase();
      base.doCapture(10, Faction.GREEN_EARTH);
      base.resetCapture();
      Assert.assertFalse(base.isCapturing());
   }

   @Test (expected = LogicException.class)
   public void throwOnCaptureByOtherFactionThanCapturing() {
      Base base = createBlueMoonBase();
      base.doCapture(10, Faction.GREEN_EARTH);
      base.doCapture(10, Faction.ORANGE_STAR);
   }

   @Test
   public void cannotKeepCapturingAfterReset() {
      Base base = createBlueMoonBase();
      base.doCapture(10, Faction.GREEN_EARTH);
      base.resetCapture();
      base.doCapture(10, Faction.GREEN_EARTH);
      Assert.assertTrue(base.getFaction() == Faction.BLUE_MOON);
   }

   @Test
   public void testIsNotCapturingAfterCompleteCapture() {
      Base base = createBlueMoonBase();
      base.doCapture(20, Faction.GREEN_EARTH);
      Assert.assertFalse(base.isCapturing());
   }

}
