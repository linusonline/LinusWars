package se.lolektivet.linus.linuswars.logic.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;

import static org.junit.Assert.*;

/**
 * Created by Linus on 2016-11-21.
 */
public class TestFuelLogic {

   private final LogicalUnitFactory _unitFactory;
   private final FuelLogic _fuelLogic;

   public TestFuelLogic() {
      _fuelLogic = new FuelLogic();
      _unitFactory = new LogicalUnitFactory(_fuelLogic);
   }

   @Before
   public void setUp() throws Exception {

   }

   @After
   public void tearDown() throws Exception {

   }

   @Test
   public void testLandUnitsHaveNoPerDayFuelConsumption() {
      assertEquals(0, _fuelLogic.getFuelCostPerTurn(_unitFactory.createLogicalUnit(UnitType.ANTI_AIR)));
      assertEquals(0, _fuelLogic.getFuelCostPerTurn(_unitFactory.createLogicalUnit(UnitType.APC)));
      assertEquals(0, _fuelLogic.getFuelCostPerTurn(_unitFactory.createLogicalUnit(UnitType.ARTILLERY)));
      assertEquals(0, _fuelLogic.getFuelCostPerTurn(_unitFactory.createLogicalUnit(UnitType.INFANTRY)));
      assertEquals(0, _fuelLogic.getFuelCostPerTurn(_unitFactory.createLogicalUnit(UnitType.MD_TANK)));
      assertEquals(0, _fuelLogic.getFuelCostPerTurn(_unitFactory.createLogicalUnit(UnitType.MECH)));
      assertEquals(0, _fuelLogic.getFuelCostPerTurn(_unitFactory.createLogicalUnit(UnitType.MISSILES)));
      assertEquals(0, _fuelLogic.getFuelCostPerTurn(_unitFactory.createLogicalUnit(UnitType.RECON)));
      assertEquals(0, _fuelLogic.getFuelCostPerTurn(_unitFactory.createLogicalUnit(UnitType.ROCKETS)));
      assertEquals(0, _fuelLogic.getFuelCostPerTurn(_unitFactory.createLogicalUnit(UnitType.TANK)));
   }

   @Test
   public void testAirUnitsHaveCorrectPerDayFuelConsumption() {
      assertEquals(2, _fuelLogic.getFuelCostPerTurn(_unitFactory.createLogicalUnit(UnitType.T_COPTER)));
      assertEquals(1, _fuelLogic.getFuelCostPerTurn(_unitFactory.createLogicalUnit(UnitType.B_COPTER)));
      assertEquals(5, _fuelLogic.getFuelCostPerTurn(_unitFactory.createLogicalUnit(UnitType.FIGHTER)));
      assertEquals(5, _fuelLogic.getFuelCostPerTurn(_unitFactory.createLogicalUnit(UnitType.BOMBER)));
   }

   @Test
   public void testNavalUnitsHaveCorrectPerDayFuelConsumption() {
      assertEquals(1, _fuelLogic.getFuelCostPerTurn(_unitFactory.createLogicalUnit(UnitType.LANDER)));
      assertEquals(1, _fuelLogic.getFuelCostPerTurn(_unitFactory.createLogicalUnit(UnitType.CRUISER)));
      assertEquals(1, _fuelLogic.getFuelCostPerTurn(_unitFactory.createLogicalUnit(UnitType.SUB)));
      assertEquals(1, _fuelLogic.getFuelCostPerTurn(_unitFactory.createLogicalUnit(UnitType.B_SHIP)));
   }

   @Test
   public void testSubConsumesMoreFuelWhileSubmerged() {
      LogicalUnit sub = _unitFactory.createLogicalUnit(UnitType.SUB);
      sub.setSubSubmerged(true);
      assertEquals(5, _fuelLogic.getFuelCostPerTurn(sub));
   }
}