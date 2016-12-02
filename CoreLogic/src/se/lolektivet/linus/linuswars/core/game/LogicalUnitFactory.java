package se.lolektivet.linus.linuswars.core.game;

import se.lolektivet.linus.linuswars.core.enums.MovementType;
import se.lolektivet.linus.linuswars.core.enums.UnitType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Linus on 2014-09-18.
 */
public class LogicalUnitFactory {

    private final FuelLogic _fuelLogic;
    private static final Map<UnitType, Integer> _costForUnitTypes = new HashMap<>();

    public LogicalUnitFactory(FuelLogic fuelLogic) {
        _fuelLogic = fuelLogic;
        initCostForUnitTypes();
    }

    private void initCostForUnitTypes() {
        _costForUnitTypes.put(UnitType.INFANTRY, 1000);
        _costForUnitTypes.put(UnitType.MECH, 3000);
        _costForUnitTypes.put(UnitType.RECON, 4000);
        _costForUnitTypes.put(UnitType.TANK, 7000);
        _costForUnitTypes.put(UnitType.MD_TANK, 16000);
        _costForUnitTypes.put(UnitType.APC, 5000);
        _costForUnitTypes.put(UnitType.ARTILLERY, 6000);
        _costForUnitTypes.put(UnitType.ROCKETS, 15000);
        _costForUnitTypes.put(UnitType.ANTI_AIR, 8000);
        _costForUnitTypes.put(UnitType.MISSILES, 12000);
        _costForUnitTypes.put(UnitType.B_COPTER, 9000);
        _costForUnitTypes.put(UnitType.T_COPTER, 5000);
        _costForUnitTypes.put(UnitType.FIGHTER, 20000);
        _costForUnitTypes.put(UnitType.BOMBER, 22000);
        _costForUnitTypes.put(UnitType.LANDER, 12000);
        _costForUnitTypes.put(UnitType.CRUISER, 18000);
        _costForUnitTypes.put(UnitType.SUB, 20000);
        _costForUnitTypes.put(UnitType.B_SHIP, 28000);
    }

    public int getUnitCost(UnitType type) {
        return _costForUnitTypes.get(type);
    }

    public LogicalUnit createLogicalUnit(UnitType unitType) {
        switch (unitType) {
            case INFANTRY:
                return createInfantry();
            case MECH:
                return createMech();
            case RECON:
                return createRecon();
            case TANK:
                return createTank();
            case MD_TANK:
                return createMdTank();
            case APC:
                return createAPC();
            case ARTILLERY:
                return createArtillery();
            case ROCKETS:
                return createRocket();
            case ANTI_AIR:
                return createAntiAir();
            case MISSILES:
                return createMissiles();
            case B_COPTER:
                return createBattleHelicopter();
            case T_COPTER:
                return createTransportHelicopter();
            case FIGHTER:
                return createFighter();
            case BOMBER:
                return createBomber();
            case LANDER:
                return createLander();
            case CRUISER:
                return createCruiser();
            case SUB:
                return createSub();
            case B_SHIP:
                return createBattleShip();
            default:
                throw new RuntimeException();
        }
    }

    private LogicalUnit createCombatUnit(UnitType type, MovementType movement, int baseVision, int baseMovementRange, int maxAmmo) {
        int maxFuel = _fuelLogic.getMaxFuelForUnitType(type);
        int cost = _costForUnitTypes.get(type);
        return LogicalUnit.createCombatUnit(type, movement, cost, maxFuel, baseVision, baseMovementRange, maxAmmo);
    }

    private LogicalUnit createTransportUnit(UnitType type, MovementType movement, int baseVision, int baseMovementRange) {
        int maxFuel = _fuelLogic.getMaxFuelForUnitType(type);
        int cost = _costForUnitTypes.get(type);
        return LogicalUnit.createTransportUnit(type, movement, cost, maxFuel, baseVision, baseMovementRange);
    }

    private LogicalUnit createRangedUnit(UnitType type, MovementType movement, int baseVision, int baseMovementRange, int maxAmmo, int baseMinAttackRange, int baseMaxAttackRange) {
        int maxFuel = _fuelLogic.getMaxFuelForUnitType(type);
        int cost = _costForUnitTypes.get(type);
        return LogicalUnit.createRangedUnit(type, movement, cost, maxFuel, baseVision, baseMovementRange, maxAmmo, baseMinAttackRange, baseMaxAttackRange);
    }

    private LogicalUnit createInfantry() {
        return createCombatUnit(UnitType.INFANTRY, MovementType.FOOT, 2, 3, 0);
    }

    private LogicalUnit createMech() {
        return createCombatUnit(UnitType.MECH, MovementType.MECH, 2, 2, 3);
    }

    private LogicalUnit createAPC() {
        return createTransportUnit(UnitType.APC, MovementType.TREADS, 1, 6);
    }

    private LogicalUnit createTank() {
        return createCombatUnit(UnitType.TANK, MovementType.TREADS, 3, 6, 9);
    }

    private LogicalUnit createMdTank() {
        return createCombatUnit(UnitType.MD_TANK, MovementType.TREADS, 1, 5, 8);
    }

    private LogicalUnit createAntiAir() {
        return createCombatUnit(UnitType.ANTI_AIR, MovementType.TREADS, 2, 6, 9);
    }

    private LogicalUnit createArtillery() {
        return createRangedUnit(UnitType.ARTILLERY, MovementType.TREADS, 1, 5, 9, 2, 3);
    }

    private LogicalUnit createRecon() {
        return createCombatUnit(UnitType.RECON, MovementType.TIRE, 5, 8, 0);
    }

    private LogicalUnit createRocket() {
        return createRangedUnit(UnitType.ROCKETS, MovementType.TIRE, 1, 5, 6, 3, 5);
    }

    private LogicalUnit createMissiles() {
        return createRangedUnit(UnitType.MISSILES, MovementType.TIRE, 5, 4, 6, 3, 5);
    }

    private LogicalUnit createLander() {
        return createTransportUnit(UnitType.LANDER, MovementType.SEA_TRANSPORT, 1, 6);
    }

    private LogicalUnit createCruiser() {
        return createCombatUnit(UnitType.CRUISER, MovementType.SEA, 3, 6, 9);
    }

    private LogicalUnit createBattleShip() {
        return createRangedUnit(UnitType.B_SHIP, MovementType.SEA, 2, 5, 9, 2, 6);
    }

    private LogicalUnit createSub() {
        return createCombatUnit(UnitType.SUB, MovementType.SEA, 5, 5, 6);
    }

    private LogicalUnit createBattleHelicopter() {
        return createCombatUnit(UnitType.B_COPTER, MovementType.AIR, 3, 6, 6);
    }

    private LogicalUnit createTransportHelicopter() {
        return createTransportUnit(UnitType.T_COPTER, MovementType.AIR, 2, 6);
    }

    private LogicalUnit createFighter() {
        return createCombatUnit(UnitType.FIGHTER, MovementType.AIR, 2, 9, 9);
    }

    private LogicalUnit createBomber() {
        return createCombatUnit(UnitType.BOMBER, MovementType.AIR, 2, 7, 9);
    }
}
