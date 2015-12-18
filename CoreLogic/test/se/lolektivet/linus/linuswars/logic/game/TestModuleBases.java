package se.lolektivet.linus.linuswars.logic.game;

import org.junit.Assert;
import org.junit.Test;
import se.lolektivet.linus.linuswars.logic.InitializationException;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;
import se.lolektivet.linus.linuswars.logic.game.Base;
import se.lolektivet.linus.linuswars.logic.game.ModuleBases;

import java.util.Collection;

/**
 * Created by Linus on 2015-11-27.
 */
public class TestModuleBases {

   @Test
   public void getBasesForFactionReturnsEmptyByDefault() {
      ModuleBases basesModule = new ModuleBases();
      Collection<Base> bases =  basesModule.getBasesForFaction(Faction.BLUE_MOON);
      Assert.assertTrue(bases.isEmpty());
   }

   @Test
   public void getBasesForFactionReturnsEmptyForWrongFaction() {
      ModuleBases basesModule = new ModuleBases();
      basesModule.addBase(new Position(2, 2), TerrainType.CITY, Faction.BLUE_MOON);
      Collection<Base> bases =  basesModule.getBasesForFaction(Faction.GREEN_EARTH);
      Assert.assertTrue(bases.isEmpty());
   }

   @Test
   public void getBasesForFactionReturnsAddedBase() {
      ModuleBases basesModule = new ModuleBases();
      basesModule.addBase(new Position(2, 2), TerrainType.CITY, Faction.BLUE_MOON);
      Collection<Base> bases =  basesModule.getBasesForFaction(Faction.BLUE_MOON);
      Assert.assertTrue(bases.size() == 1);
   }

   @Test
   public void keepsTrackOfHq() {
      ModuleBases basesModule = new ModuleBases();
      Position hqPosition = new Position(2, 2);
      basesModule.addBase(hqPosition, TerrainType.HQ, Faction.BLUE_MOON);
      Position position = basesModule.getHqPosition(Faction.BLUE_MOON);
      Assert.assertTrue(position != null);
      Assert.assertEquals(position, hqPosition);
   }

   @Test
   public void hasBaseAtPositionWorks() {
      ModuleBases basesModule = new ModuleBases();
      Position hqPosition = new Position(2, 2);
      basesModule.addBase(hqPosition, TerrainType.HQ, Faction.BLUE_MOON);

      Assert.assertTrue(basesModule.hasBaseAtPosition(hqPosition));
   }

   @Test (expected = InitializationException.class)
   public void throwsOnTwoHqsForSameFaction() {
      ModuleBases basesModule = new ModuleBases();
      basesModule.addBase(new Position(1, 1), TerrainType.HQ, Faction.BLUE_MOON);
      basesModule.addBase(new Position(1, 2), TerrainType.HQ, Faction.BLUE_MOON);
   }

   @Test (expected = InitializationException.class)
   public void throwsOnNeutralHq() {
      ModuleBases basesModule = new ModuleBases();
      basesModule.addBase(new Position(1, 1), TerrainType.HQ, Faction.NEUTRAL);
   }

   @Test
   public void validationAcceptsMinimumSetup() {
      ModuleBases basesModule = new ModuleBases();
      basesModule.addBase(new Position(1, 2), TerrainType.HQ, Faction.BLUE_MOON);
      basesModule.addBase(new Position(1, 2), TerrainType.HQ, Faction.GREEN_EARTH);
      basesModule.validateSetup();
   }

   @Test (expected = InitializationException.class)
   public void validationRejectsSingleHq() {
      ModuleBases basesModule = new ModuleBases();
      basesModule.addBase(new Position(1, 2), TerrainType.HQ, Faction.BLUE_MOON);
      basesModule.validateSetup();
   }

   @Test (expected = InitializationException.class)
   public void validationRejectsOrphanBase() {
      ModuleBases basesModule = new ModuleBases();
      basesModule.addBase(new Position(1, 1), TerrainType.HQ, Faction.BLUE_MOON);
      basesModule.addBase(new Position(2, 2), TerrainType.HQ, Faction.ORANGE_STAR);
      basesModule.addBase(new Position(2, 2), TerrainType.BASE, Faction.GREEN_EARTH);
      basesModule.validateSetup();
   }

}
