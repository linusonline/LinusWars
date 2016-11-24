package se.lolektivet.linus.linuswars.logic;

import se.lolektivet.linus.linuswars.logic.LogicalGamePredeployer;
import se.lolektivet.linus.linuswars.logic.LogicalMapMaker;
import se.lolektivet.linus.linuswars.logic.MapMaker;
import se.lolektivet.linus.linuswars.logic.WarMap;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.game.*;
import se.lolektivet.linus.linuswars.logic.maps.EmptyGameSetup;
import se.lolektivet.linus.linuswars.logic.maps.GameSetup;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Linus on 2016-11-21.
 */
public class LogicalGameFactory {

   public LogicalWarGame createLogicalWarGame(WarMap map, Faction... factions) {
      return createLogicalWarGame(map, new EmptyGameSetup(), factions);
   }

   public LogicalWarGame createLogicalWarGame(WarMap map, GameSetup gameSetup, Faction... factions) {
      List<Faction> factionList = Arrays.asList(factions);
      LogicalWarMap logicalWarMap = createLogicalMap(map, factionList);
      LogicalWarGame logicalWarGame = createLogicalWarGame(map, logicalWarMap, factionList);
      deployToLogicalGame(logicalWarGame, gameSetup, factionList);
      return logicalWarGame;
   }

   public LogicalWarMap createLogicalMap(WarMap warMap, List<Faction> factions) {
      if (warMap.getNrOfFactions() != factions.size()) {
         throw new RuntimeException("This map needs " + warMap.getNrOfFactions() + " factions, but you supplied " + factions.size());
      }

      LogicalWarMapImpl logicalWarMap = new LogicalWarMapImpl(new ModuleBuildings());
      MapMaker mapMaker = new LogicalMapMaker(logicalWarMap);
      warMap.create(mapMaker, factions);
      return logicalWarMap;
   }

   public LogicalWarGame createLogicalWarGame(WarMap warMap, LogicalWarMap logicalWarMap, List<Faction> factions) {
      LogicalWarGame logicalWarGame = new LogicalWarGame(logicalWarMap, factions);
      MapMaker warGameCreator = new LogicalWarGameCreator(logicalWarGame);
      warMap.create(warGameCreator, factions);
      return logicalWarGame;
   }

   public void deployToLogicalGame(LogicalWarGame logicalWarGame, GameSetup gameSetup, List<Faction> factions) {
      if (gameSetup.getNrOfFactions() > factions.size()) {
         throw new RuntimeException("This setup needs " + gameSetup.getNrOfFactions() + " factions, but you supplied " + factions.size());
      }

      gameSetup.preDeploy(new LogicalGamePredeployer(logicalWarGame, new LogicalUnitFactory(new FuelLogic())), factions);
   }
}