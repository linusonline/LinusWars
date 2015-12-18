package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.enums.MovementType;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;
import se.lolektivet.linus.linuswars.logic.game.LogicalUnit;

/**
 * Created by Linus on 2014-09-18.
 */
public class LogicalUnitFactory
{
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

    LogicalUnit createInfantry() {
        return LogicalUnit.createCombatUnit(UnitType.INFANTRY, MovementType.FOOT, 1000, 99, 2, 3, 0);
    }

    LogicalUnit createMech() {
        return LogicalUnit.createCombatUnit(UnitType.MECH, MovementType.MECH, 3000, 70, 2, 2, 3);
    }

    LogicalUnit createAPC() {
        return LogicalUnit.createTransportUnit(UnitType.APC, MovementType.TREADS, 5000, 70, 1, 6);
    }

    LogicalUnit createTank() {
        return LogicalUnit.createCombatUnit(UnitType.TANK, MovementType.TREADS, 7000, 70, 3, 6, 9);
    }

    LogicalUnit createMdTank() {
        return LogicalUnit.createCombatUnit(UnitType.MD_TANK, MovementType.TREADS, 16000, 50, 1, 5, 8);
    }

    LogicalUnit createAntiAir() {
        return LogicalUnit.createCombatUnit(UnitType.ANTI_AIR, MovementType.TREADS, 8000, 60, 2, 6, 9);
    }

    LogicalUnit createArtillery() {
        return LogicalUnit.createRangedUnit(UnitType.ARTILLERY, MovementType.TREADS, 6000, 99, 1, 5, 9, 2, 3);
    }

    LogicalUnit createRecon() {
        return LogicalUnit.createCombatUnit(UnitType.RECON, MovementType.TIRE, 4000, 80, 5, 8, 0);
    }

    LogicalUnit createRocket() {
        return LogicalUnit.createRangedUnit(UnitType.ROCKETS, MovementType.TIRE, 15000, 50, 1, 5, 6, 3, 5);
    }

    LogicalUnit createMissiles() {
        return LogicalUnit.createRangedUnit(UnitType.MISSILES, MovementType.TIRE, 12000, 50, 5, 4, 6, 3, 5);
    }

    LogicalUnit createLander() {
        return LogicalUnit.createTransportUnit(UnitType.LANDER, MovementType.SEA_TRANSPORT, 12000, 99, 1, 6);
    }

    LogicalUnit createCruiser() {
        return LogicalUnit.createCombatUnit(UnitType.CRUISER, MovementType.SEA, 18000, 99, 3, 6, 9);
    }

    LogicalUnit createBattleShip() {
        return LogicalUnit.createRangedUnit(UnitType.B_SHIP, MovementType.SEA, 28000, 99, 2, 5, 9, 2, 6);
    }

    LogicalUnit createSub() {
        return LogicalUnit.createCombatUnit(UnitType.SUB, MovementType.SEA, 20000, 60, 5, 5, 6);
    }

    LogicalUnit createBattleHelicopter() {
        return LogicalUnit.createCombatUnit(UnitType.B_COPTER, MovementType.AIR, 9000, 99, 3, 6, 6);
    }

    LogicalUnit createTransportHelicopter() {
        return LogicalUnit.createTransportUnit(UnitType.T_COPTER, MovementType.AIR, 5000, 99, 2, 6);
    }

    LogicalUnit createFighter() {
        return LogicalUnit.createCombatUnit(UnitType.FIGHTER, MovementType.AIR, 20000, 99, 2, 9, 9);
    }

    LogicalUnit createBomber() {
        return LogicalUnit.createCombatUnit(UnitType.BOMBER, MovementType.AIR, 22000, 99, 2, 7, 9);
    }

}
