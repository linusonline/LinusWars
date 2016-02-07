package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.enums.TerrainType;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Linus on 2015-12-27.
 */
public class DeployLogic {
   private final Map<TerrainType, List<UnitType>> _deployableTypes;
   private final LogicalUnitFactory _unitFactory;

   DeployLogic() {
      // TODO: Create sprite sheets for commented-out types.
      _unitFactory = new LogicalUnitFactory();
      _deployableTypes = new HashMap<>();

      List<UnitType> typesDeployableFromBase = new ArrayList<>();
//      typesDeployableFromBase.add(UnitType.ANTI_AIR);
      typesDeployableFromBase.add(UnitType.APC);
      typesDeployableFromBase.add(UnitType.ARTILLERY);
      typesDeployableFromBase.add(UnitType.INFANTRY);
      typesDeployableFromBase.add(UnitType.MD_TANK);
      typesDeployableFromBase.add(UnitType.MECH);
//      typesDeployableFromBase.add(UnitType.MISSILES);
//      typesDeployableFromBase.add(UnitType.RECON);
//      typesDeployableFromBase.add(UnitType.ROCKETS);
//      typesDeployableFromBase.add(UnitType.TANK);
      _deployableTypes.put(TerrainType.BASE, typesDeployableFromBase);

      List<UnitType> typesDeployableFromAirport = new ArrayList<>();
//      typesDeployableFromAirport.add(UnitType.B_COPTER);
//      typesDeployableFromAirport.add(UnitType.BOMBER);
//      typesDeployableFromAirport.add(UnitType.FIGHTER);
//      typesDeployableFromAirport.add(UnitType.T_COPTER);
      _deployableTypes.put(TerrainType.AIRPORT, typesDeployableFromAirport);

      List<UnitType> typesDeployableFromPort = new ArrayList<>();
//      typesDeployableFromPort.add(UnitType.B_SHIP);
//      typesDeployableFromPort.add(UnitType.CRUISER);
//      typesDeployableFromPort.add(UnitType.LANDER);
//      typesDeployableFromPort.add(UnitType.SUB);
      _deployableTypes.put(TerrainType.PORT, typesDeployableFromPort);
   }

   List<UnitType> getTypesDeployableFromBuilding(TerrainType buildingType) {
      if (!_deployableTypes.containsKey(buildingType)) {
         return new ArrayList<>();
      }
      return new ArrayList<>(_deployableTypes.get(buildingType));
   }

   boolean isTypeDeployableFromBuilding(TerrainType buildingType, UnitType unitType) {
      if (!_deployableTypes.containsKey(buildingType)) {
         return false;
      }
      return _deployableTypes.get(buildingType).contains(unitType);
   }

   int getCostForUnitType(UnitType unitType) {
      // TODO: Cost may vary for different COs.
      LogicalUnit unit = createUnit(unitType);
      return unit.getCost();
   }

   public LogicalUnit createUnit(UnitType unitType) {
      switch (unitType) {
         case ANTI_AIR:
            return _unitFactory.createAntiAir();
         case APC:
            return _unitFactory.createAPC();
         case ARTILLERY:
            return _unitFactory.createArtillery();
         case B_COPTER:
            return _unitFactory.createBattleHelicopter();
         case BOMBER:
            return _unitFactory.createBomber();
         case B_SHIP:
            return _unitFactory.createBattleShip();
         case CRUISER:
            return _unitFactory.createCruiser();
         case FIGHTER:
            return _unitFactory.createFighter();
         case INFANTRY:
            return _unitFactory.createInfantry();
         case LANDER:
            return _unitFactory.createLander();
         case MD_TANK:
            return _unitFactory.createMdTank();
         case MECH:
            return _unitFactory.createMech();
         case MISSILES:
            return _unitFactory.createMissiles();
         case RECON:
            return _unitFactory.createRecon();
         case ROCKETS:
            return _unitFactory.createRocket();
         case SUB:
            return _unitFactory.createSub();
         case T_COPTER:
            return _unitFactory.createTransportHelicopter();
         case TANK:
            return _unitFactory.createTank();
         default:
            throw new RuntimeException();
      }
   }
}
