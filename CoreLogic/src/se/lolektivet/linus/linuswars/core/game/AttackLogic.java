package se.lolektivet.linus.linuswars.core.game;

import se.lolektivet.linus.linuswars.core.LogicException;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;
import se.lolektivet.linus.linuswars.core.enums.UnitType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Linus on 2014-09-18.
 */
class AttackLogic {
   private final Map<TerrainType, Integer> _defenseRatingForTerrainTypes;
   private final Map<UnitType, Map<UnitType, BaseDamage>> _baseDamageChart;

   AttackLogic() {
      _defenseRatingForTerrainTypes = createDefenseRatingsForTerrain();
      _baseDamageChart = createBaseDamageChart();
   }

   private Map<TerrainType, Integer> createDefenseRatingsForTerrain() {
      Map<TerrainType, Integer> defenseRatingForTerrainTypes = new HashMap<TerrainType, Integer>(TerrainType.values().length);
      defenseRatingForTerrainTypes.put(TerrainType.ROAD, 0);
      defenseRatingForTerrainTypes.put(TerrainType.RIVER, 0);
      defenseRatingForTerrainTypes.put(TerrainType.SHOAL, 0);
      defenseRatingForTerrainTypes.put(TerrainType.PLAINS, 1);
      defenseRatingForTerrainTypes.put(TerrainType.WOODS, 2);
      defenseRatingForTerrainTypes.put(TerrainType.MOUNTAINS, 4);
      defenseRatingForTerrainTypes.put(TerrainType.BRIDGE, 0);
      defenseRatingForTerrainTypes.put(TerrainType.CITY, 3);
      defenseRatingForTerrainTypes.put(TerrainType.BASE, 3);
      defenseRatingForTerrainTypes.put(TerrainType.AIRPORT, 3);
      defenseRatingForTerrainTypes.put(TerrainType.PORT, 3);
      defenseRatingForTerrainTypes.put(TerrainType.HQ, 4);
      defenseRatingForTerrainTypes.put(TerrainType.REEF, 1);
      defenseRatingForTerrainTypes.put(TerrainType.SEA, 0);
      return defenseRatingForTerrainTypes;
   }

   private Map<UnitType, Map<UnitType, BaseDamage>> createBaseDamageChart() {
      Map<UnitType, BaseDamage> damageFromInfantry = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromInfantry.put(UnitType.INFANTRY, BaseDamage.create(55));
      damageFromInfantry.put(UnitType.MECH, BaseDamage.create(45));
      damageFromInfantry.put(UnitType.RECON, BaseDamage.create(12));
      damageFromInfantry.put(UnitType.TANK, BaseDamage.create(5));
      damageFromInfantry.put(UnitType.MD_TANK, BaseDamage.create(1));
      damageFromInfantry.put(UnitType.APC, BaseDamage.create(14));
      damageFromInfantry.put(UnitType.ARTILLERY, BaseDamage.create(15));
      damageFromInfantry.put(UnitType.ROCKETS, BaseDamage.create(25));
      damageFromInfantry.put(UnitType.ANTI_AIR, BaseDamage.create(5));
      damageFromInfantry.put(UnitType.MISSILES, BaseDamage.create(25));
      damageFromInfantry.put(UnitType.B_COPTER, BaseDamage.create(7));
      damageFromInfantry.put(UnitType.T_COPTER, BaseDamage.create(30));
      damageFromInfantry.put(UnitType.FIGHTER, BaseDamage.cannotAttack());
      damageFromInfantry.put(UnitType.BOMBER, BaseDamage.cannotAttack());
      damageFromInfantry.put(UnitType.LANDER, BaseDamage.cannotAttack());
      damageFromInfantry.put(UnitType.CRUISER, BaseDamage.cannotAttack());
      damageFromInfantry.put(UnitType.SUB, BaseDamage.cannotAttack());
      damageFromInfantry.put(UnitType.B_SHIP, BaseDamage.cannotAttack());

      Map<UnitType, BaseDamage> damageFromMech = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromMech.put(UnitType.INFANTRY, BaseDamage.create(65));
      damageFromMech.put(UnitType.MECH, BaseDamage.create(55));
      damageFromMech.put(UnitType.RECON, BaseDamage.create(85));
      damageFromMech.put(UnitType.TANK, BaseDamage.create(55));
      damageFromMech.put(UnitType.MD_TANK, BaseDamage.create(15));
      damageFromMech.put(UnitType.APC, BaseDamage.create(75));
      damageFromMech.put(UnitType.ARTILLERY, BaseDamage.create(70));
      damageFromMech.put(UnitType.ROCKETS, BaseDamage.create(85));
      damageFromMech.put(UnitType.ANTI_AIR, BaseDamage.create(65));
      damageFromMech.put(UnitType.MISSILES, BaseDamage.create(85));
      damageFromMech.put(UnitType.B_COPTER, BaseDamage.create(9));
      damageFromMech.put(UnitType.T_COPTER, BaseDamage.create(35));
      damageFromMech.put(UnitType.FIGHTER, BaseDamage.cannotAttack());
      damageFromMech.put(UnitType.BOMBER, BaseDamage.cannotAttack());
      damageFromMech.put(UnitType.LANDER, BaseDamage.cannotAttack());
      damageFromMech.put(UnitType.CRUISER, BaseDamage.cannotAttack());
      damageFromMech.put(UnitType.SUB, BaseDamage.cannotAttack());
      damageFromMech.put(UnitType.B_SHIP, BaseDamage.cannotAttack());

      Map<UnitType, BaseDamage> damageFromRecon = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromRecon.put(UnitType.INFANTRY, BaseDamage.create(70));
      damageFromRecon.put(UnitType.MECH, BaseDamage.create(65));
      damageFromRecon.put(UnitType.RECON, BaseDamage.create(35));
      damageFromRecon.put(UnitType.TANK, BaseDamage.create(6));
      damageFromRecon.put(UnitType.MD_TANK, BaseDamage.create(1));
      damageFromRecon.put(UnitType.APC, BaseDamage.create(45));
      damageFromRecon.put(UnitType.ARTILLERY, BaseDamage.create(45));
      damageFromRecon.put(UnitType.ROCKETS, BaseDamage.create(55));
      damageFromRecon.put(UnitType.ANTI_AIR, BaseDamage.create(4));
      damageFromRecon.put(UnitType.MISSILES, BaseDamage.create(28));
      damageFromRecon.put(UnitType.B_COPTER, BaseDamage.create(10));
      damageFromRecon.put(UnitType.T_COPTER, BaseDamage.create(35));
      damageFromRecon.put(UnitType.FIGHTER, BaseDamage.cannotAttack());
      damageFromRecon.put(UnitType.BOMBER, BaseDamage.cannotAttack());
      damageFromRecon.put(UnitType.LANDER, BaseDamage.cannotAttack());
      damageFromRecon.put(UnitType.CRUISER, BaseDamage.cannotAttack());
      damageFromRecon.put(UnitType.SUB, BaseDamage.cannotAttack());
      damageFromRecon.put(UnitType.B_SHIP, BaseDamage.cannotAttack());

      Map<UnitType, BaseDamage> damageFromTank = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromTank.put(UnitType.INFANTRY, BaseDamage.create(75));
      damageFromTank.put(UnitType.MECH, BaseDamage.create(70));
      damageFromTank.put(UnitType.RECON, BaseDamage.create(85));
      damageFromTank.put(UnitType.TANK, BaseDamage.create(55));
      damageFromTank.put(UnitType.MD_TANK, BaseDamage.create(15));
      damageFromTank.put(UnitType.APC, BaseDamage.create(75));
      damageFromTank.put(UnitType.ARTILLERY, BaseDamage.create(70));
      damageFromTank.put(UnitType.ROCKETS, BaseDamage.create(85));
      damageFromTank.put(UnitType.ANTI_AIR, BaseDamage.create(65));
      damageFromTank.put(UnitType.MISSILES, BaseDamage.create(85));
      damageFromTank.put(UnitType.B_COPTER, BaseDamage.create(10));
      damageFromTank.put(UnitType.T_COPTER, BaseDamage.create(40));
      damageFromTank.put(UnitType.FIGHTER, BaseDamage.cannotAttack());
      damageFromTank.put(UnitType.BOMBER, BaseDamage.cannotAttack());
      damageFromTank.put(UnitType.LANDER, BaseDamage.create(10));
      damageFromTank.put(UnitType.CRUISER, BaseDamage.create(5));
      damageFromTank.put(UnitType.SUB, BaseDamage.create(1));
      damageFromTank.put(UnitType.B_SHIP, BaseDamage.create(1));

      Map<UnitType, BaseDamage> damageFromMdTank = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromMdTank.put(UnitType.INFANTRY, BaseDamage.create(105));
      damageFromMdTank.put(UnitType.MECH, BaseDamage.create(95));
      damageFromMdTank.put(UnitType.RECON, BaseDamage.create(105));
      damageFromMdTank.put(UnitType.TANK, BaseDamage.create(85));
      damageFromMdTank.put(UnitType.MD_TANK, BaseDamage.create(55));
      damageFromMdTank.put(UnitType.APC, BaseDamage.create(105));
      damageFromMdTank.put(UnitType.ARTILLERY, BaseDamage.create(105));
      damageFromMdTank.put(UnitType.ROCKETS, BaseDamage.create(105));
      damageFromMdTank.put(UnitType.ANTI_AIR, BaseDamage.create(105));
      damageFromMdTank.put(UnitType.MISSILES, BaseDamage.create(105));
      damageFromMdTank.put(UnitType.B_COPTER, BaseDamage.create(12));
      damageFromMdTank.put(UnitType.T_COPTER, BaseDamage.create(45));
      damageFromMdTank.put(UnitType.FIGHTER, BaseDamage.cannotAttack());
      damageFromMdTank.put(UnitType.BOMBER, BaseDamage.cannotAttack());
      damageFromMdTank.put(UnitType.LANDER, BaseDamage.create(35));
      damageFromMdTank.put(UnitType.CRUISER, BaseDamage.create(55));
      damageFromMdTank.put(UnitType.SUB, BaseDamage.create(10));
      damageFromMdTank.put(UnitType.B_SHIP, BaseDamage.create(10));

      Map<UnitType, BaseDamage> damageFromAPC = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromAPC.put(UnitType.INFANTRY, BaseDamage.cannotAttack());
      damageFromAPC.put(UnitType.MECH, BaseDamage.cannotAttack());
      damageFromAPC.put(UnitType.RECON, BaseDamage.cannotAttack());
      damageFromAPC.put(UnitType.TANK, BaseDamage.cannotAttack());
      damageFromAPC.put(UnitType.MD_TANK, BaseDamage.cannotAttack());
      damageFromAPC.put(UnitType.APC, BaseDamage.cannotAttack());
      damageFromAPC.put(UnitType.ARTILLERY, BaseDamage.cannotAttack());
      damageFromAPC.put(UnitType.ROCKETS, BaseDamage.cannotAttack());
      damageFromAPC.put(UnitType.ANTI_AIR, BaseDamage.cannotAttack());
      damageFromAPC.put(UnitType.MISSILES, BaseDamage.cannotAttack());
      damageFromAPC.put(UnitType.B_COPTER, BaseDamage.cannotAttack());
      damageFromAPC.put(UnitType.T_COPTER, BaseDamage.cannotAttack());
      damageFromAPC.put(UnitType.FIGHTER, BaseDamage.cannotAttack());
      damageFromAPC.put(UnitType.BOMBER, BaseDamage.cannotAttack());
      damageFromAPC.put(UnitType.LANDER, BaseDamage.cannotAttack());
      damageFromAPC.put(UnitType.CRUISER, BaseDamage.cannotAttack());
      damageFromAPC.put(UnitType.SUB, BaseDamage.cannotAttack());
      damageFromAPC.put(UnitType.B_SHIP, BaseDamage.cannotAttack());

      Map<UnitType, BaseDamage> damageFromArtillery = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromArtillery.put(UnitType.INFANTRY, BaseDamage.create(90));
      damageFromArtillery.put(UnitType.MECH, BaseDamage.create(85));
      damageFromArtillery.put(UnitType.RECON, BaseDamage.create(80));
      damageFromArtillery.put(UnitType.TANK, BaseDamage.create(70));
      damageFromArtillery.put(UnitType.MD_TANK, BaseDamage.create(45));
      damageFromArtillery.put(UnitType.APC, BaseDamage.create(70));
      damageFromArtillery.put(UnitType.ARTILLERY, BaseDamage.create(75));
      damageFromArtillery.put(UnitType.ROCKETS, BaseDamage.create(80));
      damageFromArtillery.put(UnitType.ANTI_AIR, BaseDamage.create(75));
      damageFromArtillery.put(UnitType.MISSILES, BaseDamage.create(80));
      damageFromArtillery.put(UnitType.B_COPTER, BaseDamage.cannotAttack());
      damageFromArtillery.put(UnitType.T_COPTER, BaseDamage.cannotAttack());
      damageFromArtillery.put(UnitType.FIGHTER, BaseDamage.cannotAttack());
      damageFromArtillery.put(UnitType.BOMBER, BaseDamage.cannotAttack());
      damageFromArtillery.put(UnitType.LANDER, BaseDamage.create(55));
      damageFromArtillery.put(UnitType.CRUISER, BaseDamage.create(65));
      damageFromArtillery.put(UnitType.SUB, BaseDamage.create(60));
      damageFromArtillery.put(UnitType.B_SHIP, BaseDamage.create(40));

      Map<UnitType, BaseDamage> damageFromRockets = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromRockets.put(UnitType.INFANTRY, BaseDamage.create(95));
      damageFromRockets.put(UnitType.MECH, BaseDamage.create(90));
      damageFromRockets.put(UnitType.RECON, BaseDamage.create(90));
      damageFromRockets.put(UnitType.TANK, BaseDamage.create(85));
      damageFromRockets.put(UnitType.MD_TANK, BaseDamage.create(55));
      damageFromRockets.put(UnitType.APC, BaseDamage.create(80));
      damageFromRockets.put(UnitType.ARTILLERY, BaseDamage.create(80));
      damageFromRockets.put(UnitType.ROCKETS, BaseDamage.create(85));
      damageFromRockets.put(UnitType.ANTI_AIR, BaseDamage.create(85));
      damageFromRockets.put(UnitType.MISSILES, BaseDamage.create(90));
      damageFromRockets.put(UnitType.B_COPTER, BaseDamage.cannotAttack());
      damageFromRockets.put(UnitType.T_COPTER, BaseDamage.cannotAttack());
      damageFromRockets.put(UnitType.FIGHTER, BaseDamage.cannotAttack());
      damageFromRockets.put(UnitType.BOMBER, BaseDamage.cannotAttack());
      damageFromRockets.put(UnitType.LANDER, BaseDamage.create(60));
      damageFromRockets.put(UnitType.CRUISER, BaseDamage.create(85));
      damageFromRockets.put(UnitType.SUB, BaseDamage.create(85));
      damageFromRockets.put(UnitType.B_SHIP, BaseDamage.create(55));

      Map<UnitType, BaseDamage> damageFromAntiAir = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromAntiAir.put(UnitType.INFANTRY, BaseDamage.create(105));
      damageFromAntiAir.put(UnitType.MECH, BaseDamage.create(105));
      damageFromAntiAir.put(UnitType.RECON, BaseDamage.create(60));
      damageFromAntiAir.put(UnitType.TANK, BaseDamage.create(25));
      damageFromAntiAir.put(UnitType.MD_TANK, BaseDamage.create(10));
      damageFromAntiAir.put(UnitType.APC, BaseDamage.create(50));
      damageFromAntiAir.put(UnitType.ARTILLERY, BaseDamage.create(50));
      damageFromAntiAir.put(UnitType.ROCKETS, BaseDamage.create(45));
      damageFromAntiAir.put(UnitType.ANTI_AIR, BaseDamage.create(45));
      damageFromAntiAir.put(UnitType.MISSILES, BaseDamage.create(55));
      damageFromAntiAir.put(UnitType.B_COPTER, BaseDamage.create(120));
      damageFromAntiAir.put(UnitType.T_COPTER, BaseDamage.create(120));
      damageFromAntiAir.put(UnitType.FIGHTER, BaseDamage.create(65));
      damageFromAntiAir.put(UnitType.BOMBER, BaseDamage.create(75));
      damageFromAntiAir.put(UnitType.LANDER, BaseDamage.cannotAttack());
      damageFromAntiAir.put(UnitType.CRUISER, BaseDamage.cannotAttack());
      damageFromAntiAir.put(UnitType.SUB, BaseDamage.cannotAttack());
      damageFromAntiAir.put(UnitType.B_SHIP, BaseDamage.cannotAttack());

      Map<UnitType, BaseDamage> damageFromMissiles = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromMissiles.put(UnitType.INFANTRY, BaseDamage.cannotAttack());
      damageFromMissiles.put(UnitType.MECH, BaseDamage.cannotAttack());
      damageFromMissiles.put(UnitType.RECON, BaseDamage.cannotAttack());
      damageFromMissiles.put(UnitType.TANK, BaseDamage.cannotAttack());
      damageFromMissiles.put(UnitType.MD_TANK, BaseDamage.cannotAttack());
      damageFromMissiles.put(UnitType.APC, BaseDamage.cannotAttack());
      damageFromMissiles.put(UnitType.ARTILLERY, BaseDamage.cannotAttack());
      damageFromMissiles.put(UnitType.ROCKETS, BaseDamage.cannotAttack());
      damageFromMissiles.put(UnitType.ANTI_AIR, BaseDamage.cannotAttack());
      damageFromMissiles.put(UnitType.MISSILES, BaseDamage.cannotAttack());
      damageFromMissiles.put(UnitType.B_COPTER, BaseDamage.create(120));
      damageFromMissiles.put(UnitType.T_COPTER, BaseDamage.create(120));
      damageFromMissiles.put(UnitType.FIGHTER, BaseDamage.create(100));
      damageFromMissiles.put(UnitType.BOMBER, BaseDamage.create(100));
      damageFromMissiles.put(UnitType.LANDER, BaseDamage.cannotAttack());
      damageFromMissiles.put(UnitType.CRUISER, BaseDamage.cannotAttack());
      damageFromMissiles.put(UnitType.SUB, BaseDamage.cannotAttack());
      damageFromMissiles.put(UnitType.B_SHIP, BaseDamage.cannotAttack());

      Map<UnitType, BaseDamage> damageFromBattleCopter = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromBattleCopter.put(UnitType.INFANTRY, BaseDamage.create(75));
      damageFromBattleCopter.put(UnitType.MECH, BaseDamage.create(75));
      damageFromBattleCopter.put(UnitType.RECON, BaseDamage.create(55));
      damageFromBattleCopter.put(UnitType.TANK, BaseDamage.create(55));
      damageFromBattleCopter.put(UnitType.MD_TANK, BaseDamage.create(25));
      damageFromBattleCopter.put(UnitType.APC, BaseDamage.create(60));
      damageFromBattleCopter.put(UnitType.ARTILLERY, BaseDamage.create(65));
      damageFromBattleCopter.put(UnitType.ROCKETS, BaseDamage.create(65));
      damageFromBattleCopter.put(UnitType.ANTI_AIR, BaseDamage.create(25));
      damageFromBattleCopter.put(UnitType.MISSILES, BaseDamage.create(65));
      damageFromBattleCopter.put(UnitType.B_COPTER, BaseDamage.create(65));
      damageFromBattleCopter.put(UnitType.T_COPTER, BaseDamage.create(95));
      damageFromBattleCopter.put(UnitType.FIGHTER, BaseDamage.cannotAttack());
      damageFromBattleCopter.put(UnitType.BOMBER, BaseDamage.cannotAttack());
      damageFromBattleCopter.put(UnitType.LANDER, BaseDamage.create(25));
      damageFromBattleCopter.put(UnitType.CRUISER, BaseDamage.create(55));
      damageFromBattleCopter.put(UnitType.SUB, BaseDamage.create(25));
      damageFromBattleCopter.put(UnitType.B_SHIP, BaseDamage.create(25));

      Map<UnitType, BaseDamage> damageFromTransportCopter = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromTransportCopter.put(UnitType.INFANTRY, BaseDamage.cannotAttack());
      damageFromTransportCopter.put(UnitType.MECH, BaseDamage.cannotAttack());
      damageFromTransportCopter.put(UnitType.RECON, BaseDamage.cannotAttack());
      damageFromTransportCopter.put(UnitType.TANK, BaseDamage.cannotAttack());
      damageFromTransportCopter.put(UnitType.MD_TANK, BaseDamage.cannotAttack());
      damageFromTransportCopter.put(UnitType.APC, BaseDamage.cannotAttack());
      damageFromTransportCopter.put(UnitType.ARTILLERY, BaseDamage.cannotAttack());
      damageFromTransportCopter.put(UnitType.ROCKETS, BaseDamage.cannotAttack());
      damageFromTransportCopter.put(UnitType.ANTI_AIR, BaseDamage.cannotAttack());
      damageFromTransportCopter.put(UnitType.MISSILES, BaseDamage.cannotAttack());
      damageFromTransportCopter.put(UnitType.B_COPTER, BaseDamage.cannotAttack());
      damageFromTransportCopter.put(UnitType.T_COPTER, BaseDamage.cannotAttack());
      damageFromTransportCopter.put(UnitType.FIGHTER, BaseDamage.cannotAttack());
      damageFromTransportCopter.put(UnitType.BOMBER, BaseDamage.cannotAttack());
      damageFromTransportCopter.put(UnitType.LANDER, BaseDamage.cannotAttack());
      damageFromTransportCopter.put(UnitType.CRUISER, BaseDamage.cannotAttack());
      damageFromTransportCopter.put(UnitType.SUB, BaseDamage.cannotAttack());
      damageFromTransportCopter.put(UnitType.B_SHIP, BaseDamage.cannotAttack());

      Map<UnitType, BaseDamage> damageFromFighter = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromFighter.put(UnitType.INFANTRY, BaseDamage.cannotAttack());
      damageFromFighter.put(UnitType.MECH, BaseDamage.cannotAttack());
      damageFromFighter.put(UnitType.RECON, BaseDamage.cannotAttack());
      damageFromFighter.put(UnitType.TANK, BaseDamage.cannotAttack());
      damageFromFighter.put(UnitType.MD_TANK, BaseDamage.cannotAttack());
      damageFromFighter.put(UnitType.APC, BaseDamage.cannotAttack());
      damageFromFighter.put(UnitType.ARTILLERY, BaseDamage.cannotAttack());
      damageFromFighter.put(UnitType.ROCKETS, BaseDamage.cannotAttack());
      damageFromFighter.put(UnitType.ANTI_AIR, BaseDamage.cannotAttack());
      damageFromFighter.put(UnitType.MISSILES, BaseDamage.cannotAttack());
      damageFromFighter.put(UnitType.B_COPTER, BaseDamage.create(100));
      damageFromFighter.put(UnitType.T_COPTER, BaseDamage.create(100));
      damageFromFighter.put(UnitType.FIGHTER, BaseDamage.create(55));
      damageFromFighter.put(UnitType.BOMBER, BaseDamage.create(100));
      damageFromFighter.put(UnitType.LANDER, BaseDamage.cannotAttack());
      damageFromFighter.put(UnitType.CRUISER, BaseDamage.cannotAttack());
      damageFromFighter.put(UnitType.SUB, BaseDamage.cannotAttack());
      damageFromFighter.put(UnitType.B_SHIP, BaseDamage.cannotAttack());

      Map<UnitType, BaseDamage> damageFromBomber = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromBomber.put(UnitType.INFANTRY, BaseDamage.create(110));
      damageFromBomber.put(UnitType.MECH, BaseDamage.create(110));
      damageFromBomber.put(UnitType.RECON, BaseDamage.create(105));
      damageFromBomber.put(UnitType.TANK, BaseDamage.create(105));
      damageFromBomber.put(UnitType.MD_TANK, BaseDamage.create(95));
      damageFromBomber.put(UnitType.APC, BaseDamage.create(105));
      damageFromBomber.put(UnitType.ARTILLERY, BaseDamage.create(105));
      damageFromBomber.put(UnitType.ROCKETS, BaseDamage.create(105));
      damageFromBomber.put(UnitType.ANTI_AIR, BaseDamage.create(95));
      damageFromBomber.put(UnitType.MISSILES, BaseDamage.create(105));
      damageFromBomber.put(UnitType.B_COPTER, BaseDamage.cannotAttack());
      damageFromBomber.put(UnitType.T_COPTER, BaseDamage.cannotAttack());
      damageFromBomber.put(UnitType.FIGHTER, BaseDamage.cannotAttack());
      damageFromBomber.put(UnitType.BOMBER, BaseDamage.cannotAttack());
      damageFromBomber.put(UnitType.LANDER, BaseDamage.create(95));
      damageFromBomber.put(UnitType.CRUISER, BaseDamage.create(85));
      damageFromBomber.put(UnitType.SUB, BaseDamage.create(95));
      damageFromBomber.put(UnitType.B_SHIP, BaseDamage.create(75));

      Map<UnitType, BaseDamage> damageFromLander = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromLander.put(UnitType.INFANTRY, BaseDamage.cannotAttack());
      damageFromLander.put(UnitType.MECH, BaseDamage.cannotAttack());
      damageFromLander.put(UnitType.RECON, BaseDamage.cannotAttack());
      damageFromLander.put(UnitType.TANK, BaseDamage.cannotAttack());
      damageFromLander.put(UnitType.MD_TANK, BaseDamage.cannotAttack());
      damageFromLander.put(UnitType.APC, BaseDamage.cannotAttack());
      damageFromLander.put(UnitType.ARTILLERY, BaseDamage.cannotAttack());
      damageFromLander.put(UnitType.ROCKETS, BaseDamage.cannotAttack());
      damageFromLander.put(UnitType.ANTI_AIR, BaseDamage.cannotAttack());
      damageFromLander.put(UnitType.MISSILES, BaseDamage.cannotAttack());
      damageFromLander.put(UnitType.B_COPTER, BaseDamage.cannotAttack());
      damageFromLander.put(UnitType.T_COPTER, BaseDamage.cannotAttack());
      damageFromLander.put(UnitType.FIGHTER, BaseDamage.cannotAttack());
      damageFromLander.put(UnitType.BOMBER, BaseDamage.cannotAttack());
      damageFromLander.put(UnitType.LANDER, BaseDamage.cannotAttack());
      damageFromLander.put(UnitType.CRUISER, BaseDamage.cannotAttack());
      damageFromLander.put(UnitType.SUB, BaseDamage.cannotAttack());
      damageFromLander.put(UnitType.B_SHIP, BaseDamage.cannotAttack());

      Map<UnitType, BaseDamage> damageFromCruiser = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromCruiser.put(UnitType.INFANTRY, BaseDamage.cannotAttack());
      damageFromCruiser.put(UnitType.MECH, BaseDamage.cannotAttack());
      damageFromCruiser.put(UnitType.RECON, BaseDamage.cannotAttack());
      damageFromCruiser.put(UnitType.TANK, BaseDamage.cannotAttack());
      damageFromCruiser.put(UnitType.MD_TANK, BaseDamage.cannotAttack());
      damageFromCruiser.put(UnitType.APC, BaseDamage.cannotAttack());
      damageFromCruiser.put(UnitType.ARTILLERY, BaseDamage.cannotAttack());
      damageFromCruiser.put(UnitType.ROCKETS, BaseDamage.cannotAttack());
      damageFromCruiser.put(UnitType.ANTI_AIR, BaseDamage.cannotAttack());
      damageFromCruiser.put(UnitType.MISSILES, BaseDamage.cannotAttack());
      damageFromCruiser.put(UnitType.B_COPTER, BaseDamage.create(115));
      damageFromCruiser.put(UnitType.T_COPTER, BaseDamage.create(115));
      damageFromCruiser.put(UnitType.FIGHTER, BaseDamage.create(55));
      damageFromCruiser.put(UnitType.BOMBER, BaseDamage.create(65));
      damageFromCruiser.put(UnitType.LANDER, BaseDamage.cannotAttack());
      damageFromCruiser.put(UnitType.CRUISER, BaseDamage.cannotAttack());
      damageFromCruiser.put(UnitType.SUB, BaseDamage.cannotAttack());
      damageFromCruiser.put(UnitType.B_SHIP, BaseDamage.cannotAttack());

      Map<UnitType, BaseDamage> damageFromSub = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromSub.put(UnitType.INFANTRY, BaseDamage.cannotAttack());
      damageFromSub.put(UnitType.MECH, BaseDamage.cannotAttack());
      damageFromSub.put(UnitType.RECON, BaseDamage.cannotAttack());
      damageFromSub.put(UnitType.TANK, BaseDamage.cannotAttack());
      damageFromSub.put(UnitType.MD_TANK, BaseDamage.cannotAttack());
      damageFromSub.put(UnitType.APC, BaseDamage.cannotAttack());
      damageFromSub.put(UnitType.ARTILLERY, BaseDamage.cannotAttack());
      damageFromSub.put(UnitType.ROCKETS, BaseDamage.cannotAttack());
      damageFromSub.put(UnitType.ANTI_AIR, BaseDamage.cannotAttack());
      damageFromSub.put(UnitType.MISSILES, BaseDamage.cannotAttack());
      damageFromSub.put(UnitType.B_COPTER, BaseDamage.cannotAttack());
      damageFromSub.put(UnitType.T_COPTER, BaseDamage.cannotAttack());
      damageFromSub.put(UnitType.FIGHTER, BaseDamage.cannotAttack());
      damageFromSub.put(UnitType.BOMBER, BaseDamage.cannotAttack());
      damageFromSub.put(UnitType.LANDER, BaseDamage.create(95));
      damageFromSub.put(UnitType.CRUISER, BaseDamage.create(25));
      damageFromSub.put(UnitType.SUB, BaseDamage.create(55));
      damageFromSub.put(UnitType.B_SHIP, BaseDamage.create(55));

      Map<UnitType, BaseDamage> damageFromBattleShip = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromBattleShip.put(UnitType.INFANTRY, BaseDamage.create(95));
      damageFromBattleShip.put(UnitType.MECH, BaseDamage.create(90));
      damageFromBattleShip.put(UnitType.RECON, BaseDamage.create(90));
      damageFromBattleShip.put(UnitType.TANK, BaseDamage.create(85));
      damageFromBattleShip.put(UnitType.MD_TANK, BaseDamage.create(55));
      damageFromBattleShip.put(UnitType.APC, BaseDamage.create(80));
      damageFromBattleShip.put(UnitType.ARTILLERY, BaseDamage.create(80));
      damageFromBattleShip.put(UnitType.ROCKETS, BaseDamage.create(85));
      damageFromBattleShip.put(UnitType.ANTI_AIR, BaseDamage.create(85));
      damageFromBattleShip.put(UnitType.MISSILES, BaseDamage.create(90));
      damageFromBattleShip.put(UnitType.B_COPTER, BaseDamage.cannotAttack());
      damageFromBattleShip.put(UnitType.T_COPTER, BaseDamage.cannotAttack());
      damageFromBattleShip.put(UnitType.FIGHTER, BaseDamage.cannotAttack());
      damageFromBattleShip.put(UnitType.BOMBER, BaseDamage.cannotAttack());
      damageFromBattleShip.put(UnitType.LANDER, BaseDamage.create(95));
      damageFromBattleShip.put(UnitType.CRUISER, BaseDamage.create(95));
      damageFromBattleShip.put(UnitType.SUB, BaseDamage.create(95));
      damageFromBattleShip.put(UnitType.B_SHIP, BaseDamage.create(50));

      Map<UnitType, Map<UnitType, BaseDamage>> baseDamageChart = new HashMap<UnitType, Map<UnitType, BaseDamage>>(UnitType.values().length);
      baseDamageChart.put(UnitType.INFANTRY, damageFromInfantry);
      baseDamageChart.put(UnitType.MECH, damageFromMech);
      baseDamageChart.put(UnitType.RECON, damageFromRecon);
      baseDamageChart.put(UnitType.TANK, damageFromTank);
      baseDamageChart.put(UnitType.MD_TANK, damageFromMdTank);
      baseDamageChart.put(UnitType.APC, damageFromAPC);
      baseDamageChart.put(UnitType.ARTILLERY, damageFromArtillery);
      baseDamageChart.put(UnitType.ROCKETS, damageFromRockets);
      baseDamageChart.put(UnitType.ANTI_AIR, damageFromAntiAir);
      baseDamageChart.put(UnitType.MISSILES, damageFromMissiles);
      baseDamageChart.put(UnitType.B_COPTER, damageFromBattleCopter);
      baseDamageChart.put(UnitType.T_COPTER, damageFromTransportCopter);
      baseDamageChart.put(UnitType.FIGHTER, damageFromFighter);
      baseDamageChart.put(UnitType.BOMBER, damageFromBomber);
      baseDamageChart.put(UnitType.LANDER, damageFromLander);
      baseDamageChart.put(UnitType.CRUISER, damageFromCruiser);
      baseDamageChart.put(UnitType.SUB, damageFromSub);
      baseDamageChart.put(UnitType.B_SHIP, damageFromBattleShip);

      return baseDamageChart;
   }

   // Note: Only those combinations that have different values than standard chart are included here.
   private Map<UnitType, Map<UnitType, BaseDamage>> createOutOfAmmoBaseDamageChart() {
      Map<UnitType, BaseDamage> damageFromInfantry = new HashMap<UnitType, BaseDamage>(0);

      Map<UnitType, BaseDamage> damageFromMech = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromMech.put(UnitType.RECON, BaseDamage.create(18));
      damageFromMech.put(UnitType.TANK, BaseDamage.create(6));
      damageFromMech.put(UnitType.MD_TANK, BaseDamage.create(1));
      damageFromMech.put(UnitType.APC, BaseDamage.create(20));
      damageFromMech.put(UnitType.ARTILLERY, BaseDamage.create(32));
      damageFromMech.put(UnitType.ROCKETS, BaseDamage.create(35));
      damageFromMech.put(UnitType.ANTI_AIR, BaseDamage.create(6));
      damageFromMech.put(UnitType.MISSILES, BaseDamage.create(35));

      Map<UnitType, BaseDamage> damageFromRecon = new HashMap<UnitType, BaseDamage>(UnitType.values().length);

      Map<UnitType, BaseDamage> damageFromTank = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromTank.put(UnitType.RECON, BaseDamage.create(40));
      damageFromTank.put(UnitType.TANK, BaseDamage.create(6));
      damageFromTank.put(UnitType.MD_TANK, BaseDamage.create(1));
      damageFromTank.put(UnitType.APC, BaseDamage.create(45));
      damageFromTank.put(UnitType.ARTILLERY, BaseDamage.create(45));
      damageFromTank.put(UnitType.ROCKETS, BaseDamage.create(55));
      damageFromTank.put(UnitType.ANTI_AIR, BaseDamage.create(5));
      damageFromTank.put(UnitType.MISSILES, BaseDamage.create(30));
      damageFromTank.put(UnitType.LANDER, BaseDamage.cannotAttack());
      damageFromTank.put(UnitType.CRUISER, BaseDamage.cannotAttack());
      damageFromTank.put(UnitType.SUB, BaseDamage.cannotAttack());
      damageFromTank.put(UnitType.B_SHIP, BaseDamage.cannotAttack());

      Map<UnitType, BaseDamage> damageFromMdTank = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromMdTank.put(UnitType.RECON, BaseDamage.cannotAttack()); //?
      damageFromMdTank.put(UnitType.TANK, BaseDamage.create(8));
      damageFromMdTank.put(UnitType.MD_TANK, BaseDamage.cannotAttack()); //?
      damageFromMdTank.put(UnitType.APC, BaseDamage.cannotAttack()); //?
      damageFromMdTank.put(UnitType.ARTILLERY, BaseDamage.create(45));
      damageFromMdTank.put(UnitType.ROCKETS, BaseDamage.cannotAttack()); //?
      damageFromMdTank.put(UnitType.ANTI_AIR, BaseDamage.create(7));
      damageFromMdTank.put(UnitType.MISSILES, BaseDamage.cannotAttack()); //?
      damageFromMdTank.put(UnitType.LANDER, BaseDamage.cannotAttack()); //?
      damageFromMdTank.put(UnitType.CRUISER, BaseDamage.cannotAttack()); //?
      damageFromMdTank.put(UnitType.SUB, BaseDamage.cannotAttack()); //?
      damageFromMdTank.put(UnitType.B_SHIP, BaseDamage.cannotAttack()); //?

      Map<UnitType, BaseDamage> damageFromAPC = new HashMap<UnitType, BaseDamage>(UnitType.values().length);

      Map<UnitType, BaseDamage> damageFromArtillery = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromArtillery.put(UnitType.INFANTRY, BaseDamage.cannotAttack());
      damageFromArtillery.put(UnitType.MECH, BaseDamage.cannotAttack());
      damageFromArtillery.put(UnitType.RECON, BaseDamage.cannotAttack());
      damageFromArtillery.put(UnitType.TANK, BaseDamage.cannotAttack());
      damageFromArtillery.put(UnitType.MD_TANK, BaseDamage.cannotAttack());
      damageFromArtillery.put(UnitType.APC, BaseDamage.cannotAttack());
      damageFromArtillery.put(UnitType.ARTILLERY, BaseDamage.cannotAttack());
      damageFromArtillery.put(UnitType.ROCKETS, BaseDamage.cannotAttack());
      damageFromArtillery.put(UnitType.ANTI_AIR, BaseDamage.cannotAttack());
      damageFromArtillery.put(UnitType.MISSILES, BaseDamage.cannotAttack());
      damageFromArtillery.put(UnitType.LANDER, BaseDamage.cannotAttack());
      damageFromArtillery.put(UnitType.CRUISER, BaseDamage.cannotAttack());
      damageFromArtillery.put(UnitType.SUB, BaseDamage.cannotAttack());
      damageFromArtillery.put(UnitType.B_SHIP, BaseDamage.cannotAttack());

      Map<UnitType, BaseDamage> damageFromRockets = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromRockets.put(UnitType.INFANTRY, BaseDamage.cannotAttack());
      damageFromRockets.put(UnitType.MECH, BaseDamage.cannotAttack());
      damageFromRockets.put(UnitType.RECON, BaseDamage.cannotAttack());
      damageFromRockets.put(UnitType.TANK, BaseDamage.cannotAttack());
      damageFromRockets.put(UnitType.MD_TANK, BaseDamage.cannotAttack());
      damageFromRockets.put(UnitType.APC, BaseDamage.cannotAttack());
      damageFromRockets.put(UnitType.ARTILLERY, BaseDamage.cannotAttack());
      damageFromRockets.put(UnitType.ROCKETS, BaseDamage.cannotAttack());
      damageFromRockets.put(UnitType.ANTI_AIR, BaseDamage.cannotAttack());
      damageFromRockets.put(UnitType.MISSILES, BaseDamage.cannotAttack());
      damageFromRockets.put(UnitType.LANDER, BaseDamage.cannotAttack());
      damageFromRockets.put(UnitType.CRUISER, BaseDamage.cannotAttack());
      damageFromRockets.put(UnitType.SUB, BaseDamage.cannotAttack());
      damageFromRockets.put(UnitType.B_SHIP, BaseDamage.cannotAttack());

      Map<UnitType, BaseDamage> damageFromAntiAir = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromAntiAir.put(UnitType.INFANTRY, BaseDamage.cannotAttack());
      damageFromAntiAir.put(UnitType.MECH, BaseDamage.cannotAttack());
      damageFromAntiAir.put(UnitType.RECON, BaseDamage.cannotAttack());
      damageFromAntiAir.put(UnitType.TANK, BaseDamage.cannotAttack());
      damageFromAntiAir.put(UnitType.MD_TANK, BaseDamage.cannotAttack());
      damageFromAntiAir.put(UnitType.APC, BaseDamage.cannotAttack());
      damageFromAntiAir.put(UnitType.ARTILLERY, BaseDamage.cannotAttack());
      damageFromAntiAir.put(UnitType.ROCKETS, BaseDamage.cannotAttack());
      damageFromAntiAir.put(UnitType.ANTI_AIR, BaseDamage.cannotAttack());
      damageFromAntiAir.put(UnitType.MISSILES, BaseDamage.cannotAttack());
      damageFromAntiAir.put(UnitType.B_COPTER, BaseDamage.cannotAttack());
      damageFromAntiAir.put(UnitType.T_COPTER, BaseDamage.cannotAttack());
      damageFromAntiAir.put(UnitType.FIGHTER, BaseDamage.cannotAttack());
      damageFromAntiAir.put(UnitType.BOMBER, BaseDamage.cannotAttack());

      Map<UnitType, BaseDamage> damageFromMissiles = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromMissiles.put(UnitType.B_COPTER, BaseDamage.cannotAttack());
      damageFromMissiles.put(UnitType.T_COPTER, BaseDamage.cannotAttack());
      damageFromMissiles.put(UnitType.FIGHTER, BaseDamage.cannotAttack());
      damageFromMissiles.put(UnitType.BOMBER, BaseDamage.cannotAttack());

      Map<UnitType, BaseDamage> damageFromBattleCopter = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromBattleCopter.put(UnitType.RECON, BaseDamage.cannotAttack()); //?
      damageFromBattleCopter.put(UnitType.TANK, BaseDamage.create(6));
      damageFromBattleCopter.put(UnitType.MD_TANK, BaseDamage.cannotAttack()); //?
      damageFromBattleCopter.put(UnitType.APC, BaseDamage.cannotAttack()); //?
      damageFromBattleCopter.put(UnitType.ARTILLERY, BaseDamage.create(25));
      damageFromBattleCopter.put(UnitType.ROCKETS, BaseDamage.cannotAttack()); //?
      damageFromBattleCopter.put(UnitType.ANTI_AIR, BaseDamage.create(6));
      damageFromBattleCopter.put(UnitType.MISSILES, BaseDamage.cannotAttack()); //?
      damageFromBattleCopter.put(UnitType.B_COPTER, BaseDamage.cannotAttack()); //?
      damageFromBattleCopter.put(UnitType.T_COPTER, BaseDamage.cannotAttack()); //?
      damageFromBattleCopter.put(UnitType.LANDER, BaseDamage.cannotAttack()); //?
      damageFromBattleCopter.put(UnitType.CRUISER, BaseDamage.cannotAttack()); //?
      damageFromBattleCopter.put(UnitType.SUB, BaseDamage.cannotAttack()); //?
      damageFromBattleCopter.put(UnitType.B_SHIP, BaseDamage.cannotAttack()); //?

      Map<UnitType, BaseDamage> damageFromTransportCopter = new HashMap<UnitType, BaseDamage>(UnitType.values().length);

      Map<UnitType, BaseDamage> damageFromFighter = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromFighter.put(UnitType.B_COPTER, BaseDamage.cannotAttack());
      damageFromFighter.put(UnitType.T_COPTER, BaseDamage.cannotAttack());
      damageFromFighter.put(UnitType.FIGHTER, BaseDamage.cannotAttack());
      damageFromFighter.put(UnitType.BOMBER, BaseDamage.cannotAttack());

      Map<UnitType, BaseDamage> damageFromBomber = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromBomber.put(UnitType.INFANTRY, BaseDamage.cannotAttack());
      damageFromBomber.put(UnitType.MECH, BaseDamage.cannotAttack());
      damageFromBomber.put(UnitType.RECON, BaseDamage.cannotAttack());
      damageFromBomber.put(UnitType.TANK, BaseDamage.cannotAttack());
      damageFromBomber.put(UnitType.MD_TANK, BaseDamage.cannotAttack());
      damageFromBomber.put(UnitType.APC, BaseDamage.cannotAttack());
      damageFromBomber.put(UnitType.ARTILLERY, BaseDamage.cannotAttack());
      damageFromBomber.put(UnitType.ROCKETS, BaseDamage.cannotAttack());
      damageFromBomber.put(UnitType.ANTI_AIR, BaseDamage.cannotAttack());
      damageFromBomber.put(UnitType.MISSILES, BaseDamage.cannotAttack());
      damageFromBomber.put(UnitType.LANDER, BaseDamage.cannotAttack());
      damageFromBomber.put(UnitType.CRUISER, BaseDamage.cannotAttack());
      damageFromBomber.put(UnitType.SUB, BaseDamage.cannotAttack());
      damageFromBomber.put(UnitType.B_SHIP, BaseDamage.cannotAttack());

      Map<UnitType, BaseDamage> damageFromLander = new HashMap<UnitType, BaseDamage>(UnitType.values().length);

      Map<UnitType, BaseDamage> damageFromCruiser = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromCruiser.put(UnitType.B_COPTER, BaseDamage.cannotAttack()); //?
      damageFromCruiser.put(UnitType.T_COPTER, BaseDamage.cannotAttack()); //?
      damageFromCruiser.put(UnitType.FIGHTER, BaseDamage.cannotAttack()); //?
      damageFromCruiser.put(UnitType.BOMBER, BaseDamage.cannotAttack()); //?

      Map<UnitType, BaseDamage> damageFromSub = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromSub.put(UnitType.LANDER, BaseDamage.cannotAttack());
      damageFromSub.put(UnitType.CRUISER, BaseDamage.cannotAttack());
      damageFromSub.put(UnitType.SUB, BaseDamage.cannotAttack());
      damageFromSub.put(UnitType.B_SHIP, BaseDamage.cannotAttack());

      Map<UnitType, BaseDamage> damageFromBattleShip = new HashMap<UnitType, BaseDamage>(UnitType.values().length);
      damageFromBattleShip.put(UnitType.INFANTRY, BaseDamage.cannotAttack());
      damageFromBattleShip.put(UnitType.MECH, BaseDamage.cannotAttack());
      damageFromBattleShip.put(UnitType.RECON, BaseDamage.cannotAttack());
      damageFromBattleShip.put(UnitType.TANK, BaseDamage.cannotAttack());
      damageFromBattleShip.put(UnitType.MD_TANK, BaseDamage.cannotAttack());
      damageFromBattleShip.put(UnitType.APC, BaseDamage.cannotAttack());
      damageFromBattleShip.put(UnitType.ARTILLERY, BaseDamage.cannotAttack());
      damageFromBattleShip.put(UnitType.ROCKETS, BaseDamage.cannotAttack());
      damageFromBattleShip.put(UnitType.ANTI_AIR, BaseDamage.cannotAttack());
      damageFromBattleShip.put(UnitType.MISSILES, BaseDamage.cannotAttack());
      damageFromBattleShip.put(UnitType.LANDER, BaseDamage.cannotAttack());
      damageFromBattleShip.put(UnitType.CRUISER, BaseDamage.cannotAttack());
      damageFromBattleShip.put(UnitType.SUB, BaseDamage.cannotAttack());
      damageFromBattleShip.put(UnitType.B_SHIP, BaseDamage.cannotAttack());

      Map<UnitType, Map<UnitType, BaseDamage>> baseDamageChart = new HashMap<UnitType, Map<UnitType, BaseDamage>>(UnitType.values().length);
      baseDamageChart.put(UnitType.INFANTRY, damageFromInfantry);
      baseDamageChart.put(UnitType.MECH, damageFromMech);
      baseDamageChart.put(UnitType.RECON, damageFromRecon);
      baseDamageChart.put(UnitType.TANK, damageFromTank);
      baseDamageChart.put(UnitType.MD_TANK, damageFromMdTank);
      baseDamageChart.put(UnitType.APC, damageFromAPC);
      baseDamageChart.put(UnitType.ARTILLERY, damageFromArtillery);
      baseDamageChart.put(UnitType.ROCKETS, damageFromRockets);
      baseDamageChart.put(UnitType.ANTI_AIR, damageFromAntiAir);
      baseDamageChart.put(UnitType.MISSILES, damageFromMissiles);
      baseDamageChart.put(UnitType.B_COPTER, damageFromBattleCopter);
      baseDamageChart.put(UnitType.T_COPTER, damageFromTransportCopter);
      baseDamageChart.put(UnitType.FIGHTER, damageFromFighter);
      baseDamageChart.put(UnitType.BOMBER, damageFromBomber);
      baseDamageChart.put(UnitType.LANDER, damageFromLander);
      baseDamageChart.put(UnitType.CRUISER, damageFromCruiser);
      baseDamageChart.put(UnitType.SUB, damageFromSub);
      baseDamageChart.put(UnitType.B_SHIP, damageFromBattleShip);

      return baseDamageChart;
   }

   boolean canAttack(UnitType attackerType, UnitType defenderType) {
      return _baseDamageChart.get(attackerType).get(defenderType).canAttack();
   }

   int calculateDamageInPercent(LogicalUnit attacker, LogicalUnit defender, TerrainType defenderTerrain, float attackerModifiers, float defenderModifiers) {
      float baseDamageInPercent = getBaseDamageForTypes(attacker.getType(), defender.getType());
      float attackerHpModifer = attacker.getHp1To10() / 10f;
      float terrainModifier = 1 - getDefenseRatingForTerrainType(defenderTerrain) * defender.getHp1To10() / 100f;
      float totalDamageInPercent = baseDamageInPercent * attackerHpModifer * terrainModifier * attackerModifiers * defenderModifiers;
      return (int)totalDamageInPercent;
   }

   private float getBaseDamageForTypes(UnitType attackerType, UnitType defenderType) {
      BaseDamage damage = _baseDamageChart.get(attackerType).get(defenderType);
      if (!damage.canAttack()) {
         throw new LogicException();
      }
      return damage.getDamage();
   }

   private int getDefenseRatingForTerrainType(TerrainType terrainType) {
      return _defenseRatingForTerrainTypes.get(terrainType);
   }
}
