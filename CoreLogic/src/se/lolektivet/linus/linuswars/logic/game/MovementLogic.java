package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.enums.MovementType;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;
import se.lolektivet.linus.linuswars.logic.pathfinding.InfiniteInteger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Linus on 2014-09-19.
 */
class MovementLogic {
   private final Map<MovementType, Map<TerrainType, InfiniteInteger>> _movementCosts;

   MovementLogic() {
      _movementCosts = initializeMovementCosts();
   }

   private Map<MovementType, Map<TerrainType, InfiniteInteger>> initializeMovementCosts() {
      Map<TerrainType, InfiniteInteger> costsForTreads = new HashMap<TerrainType, InfiniteInteger>(TerrainType.values().length);
      costsForTreads.put(TerrainType.ROAD, InfiniteInteger.create(1));
      costsForTreads.put(TerrainType.RIVER, InfiniteInteger.infinite());
      costsForTreads.put(TerrainType.SHOAL, InfiniteInteger.create(1));
      costsForTreads.put(TerrainType.PLAINS, InfiniteInteger.create(1));
      costsForTreads.put(TerrainType.WOODS, InfiniteInteger.create(2));
      costsForTreads.put(TerrainType.MOUNTAINS, InfiniteInteger.infinite());
      costsForTreads.put(TerrainType.BRIDGE, InfiniteInteger.create(1));
      costsForTreads.put(TerrainType.CITY, InfiniteInteger.create(1));
      costsForTreads.put(TerrainType.BASE, InfiniteInteger.create(1));
      costsForTreads.put(TerrainType.AIRPORT, InfiniteInteger.create(1));
      costsForTreads.put(TerrainType.PORT, InfiniteInteger.create(1));
      costsForTreads.put(TerrainType.HQ, InfiniteInteger.create(1));
      costsForTreads.put(TerrainType.SEA, InfiniteInteger.infinite());
      costsForTreads.put(TerrainType.REEF, InfiniteInteger.infinite());

      Map<TerrainType, InfiniteInteger> costsForTires = new HashMap<TerrainType, InfiniteInteger>(TerrainType.values().length);
      costsForTires.put(TerrainType.ROAD, InfiniteInteger.create(1));
      costsForTires.put(TerrainType.RIVER, InfiniteInteger.infinite());
      costsForTires.put(TerrainType.SHOAL, InfiniteInteger.create(1));
      costsForTires.put(TerrainType.PLAINS, InfiniteInteger.create(2));
      costsForTires.put(TerrainType.WOODS, InfiniteInteger.create(3));
      costsForTires.put(TerrainType.MOUNTAINS, InfiniteInteger.infinite());
      costsForTires.put(TerrainType.BRIDGE, InfiniteInteger.create(1));
      costsForTires.put(TerrainType.CITY, InfiniteInteger.create(1));
      costsForTires.put(TerrainType.BASE, InfiniteInteger.create(1));
      costsForTires.put(TerrainType.AIRPORT, InfiniteInteger.create(1));
      costsForTires.put(TerrainType.PORT, InfiniteInteger.create(1));
      costsForTires.put(TerrainType.HQ, InfiniteInteger.create(1));
      costsForTires.put(TerrainType.SEA, InfiniteInteger.infinite());
      costsForTires.put(TerrainType.REEF, InfiniteInteger.infinite());

      Map<TerrainType, InfiniteInteger> costsForFoot = new HashMap<TerrainType, InfiniteInteger>(TerrainType.values().length);
      costsForFoot.put(TerrainType.ROAD, InfiniteInteger.create(1));
      costsForFoot.put(TerrainType.RIVER, InfiniteInteger.create(2));
      costsForFoot.put(TerrainType.SHOAL, InfiniteInteger.create(1));
      costsForFoot.put(TerrainType.PLAINS, InfiniteInteger.create(1));
      costsForFoot.put(TerrainType.WOODS, InfiniteInteger.create(1));
      costsForFoot.put(TerrainType.MOUNTAINS, InfiniteInteger.create(2));
      costsForFoot.put(TerrainType.BRIDGE, InfiniteInteger.create(1));
      costsForFoot.put(TerrainType.CITY, InfiniteInteger.create(1));
      costsForFoot.put(TerrainType.BASE, InfiniteInteger.create(1));
      costsForFoot.put(TerrainType.AIRPORT, InfiniteInteger.create(1));
      costsForFoot.put(TerrainType.PORT, InfiniteInteger.create(1));
      costsForFoot.put(TerrainType.HQ, InfiniteInteger.create(1));
      costsForFoot.put(TerrainType.SEA, InfiniteInteger.infinite());
      costsForFoot.put(TerrainType.REEF, InfiniteInteger.infinite());

      Map<TerrainType, InfiniteInteger> costsForMech = new HashMap<TerrainType, InfiniteInteger>(TerrainType.values().length);
      costsForMech.put(TerrainType.ROAD, InfiniteInteger.create(1));
      costsForMech.put(TerrainType.RIVER, InfiniteInteger.create(1));
      costsForMech.put(TerrainType.SHOAL, InfiniteInteger.create(1));
      costsForMech.put(TerrainType.PLAINS, InfiniteInteger.create(1));
      costsForMech.put(TerrainType.WOODS, InfiniteInteger.create(1));
      costsForMech.put(TerrainType.MOUNTAINS, InfiniteInteger.create(1));
      costsForMech.put(TerrainType.BRIDGE, InfiniteInteger.create(1));
      costsForMech.put(TerrainType.CITY, InfiniteInteger.create(1));
      costsForMech.put(TerrainType.BASE, InfiniteInteger.create(1));
      costsForMech.put(TerrainType.AIRPORT, InfiniteInteger.create(1));
      costsForMech.put(TerrainType.PORT, InfiniteInteger.create(1));
      costsForMech.put(TerrainType.HQ, InfiniteInteger.create(1));
      costsForMech.put(TerrainType.SEA, InfiniteInteger.infinite());
      costsForMech.put(TerrainType.REEF, InfiniteInteger.infinite());

      Map<TerrainType, InfiniteInteger> costsForSea = new HashMap<TerrainType, InfiniteInteger>(TerrainType.values().length);
      costsForSea.put(TerrainType.ROAD, InfiniteInteger.infinite());
      costsForSea.put(TerrainType.RIVER, InfiniteInteger.infinite());
      costsForSea.put(TerrainType.SHOAL, InfiniteInteger.infinite());
      costsForSea.put(TerrainType.PLAINS, InfiniteInteger.infinite());
      costsForSea.put(TerrainType.WOODS, InfiniteInteger.infinite());
      costsForSea.put(TerrainType.MOUNTAINS, InfiniteInteger.infinite());
      costsForSea.put(TerrainType.BRIDGE, InfiniteInteger.infinite());
      costsForSea.put(TerrainType.CITY, InfiniteInteger.infinite());
      costsForSea.put(TerrainType.BASE, InfiniteInteger.infinite());
      costsForSea.put(TerrainType.AIRPORT, InfiniteInteger.infinite());
      costsForSea.put(TerrainType.PORT, InfiniteInteger.infinite());
      costsForSea.put(TerrainType.HQ, InfiniteInteger.infinite());
      costsForSea.put(TerrainType.SEA, InfiniteInteger.create(1));
      costsForSea.put(TerrainType.REEF, InfiniteInteger.create(2));

      Map<TerrainType, InfiniteInteger> costsForSeaTransport = new HashMap<TerrainType, InfiniteInteger>(TerrainType.values().length);
      costsForSeaTransport.put(TerrainType.ROAD, InfiniteInteger.infinite());
      costsForSeaTransport.put(TerrainType.RIVER, InfiniteInteger.infinite());
      costsForSeaTransport.put(TerrainType.SHOAL, InfiniteInteger.create(1));
      costsForSeaTransport.put(TerrainType.PLAINS, InfiniteInteger.infinite());
      costsForSeaTransport.put(TerrainType.WOODS, InfiniteInteger.infinite());
      costsForSeaTransport.put(TerrainType.MOUNTAINS, InfiniteInteger.infinite());
      costsForSeaTransport.put(TerrainType.BRIDGE, InfiniteInteger.infinite());
      costsForSeaTransport.put(TerrainType.CITY, InfiniteInteger.infinite());
      costsForSeaTransport.put(TerrainType.BASE, InfiniteInteger.infinite());
      costsForSeaTransport.put(TerrainType.AIRPORT, InfiniteInteger.infinite());
      costsForSeaTransport.put(TerrainType.PORT, InfiniteInteger.infinite());
      costsForSeaTransport.put(TerrainType.HQ, InfiniteInteger.infinite());
      costsForSeaTransport.put(TerrainType.SEA, InfiniteInteger.create(1));
      costsForSeaTransport.put(TerrainType.REEF, InfiniteInteger.create(2));

      Map<TerrainType, InfiniteInteger> costsForAir = new HashMap<TerrainType, InfiniteInteger>(TerrainType.values().length);
      costsForAir.put(TerrainType.ROAD, InfiniteInteger.create(1));
      costsForAir.put(TerrainType.RIVER, InfiniteInteger.create(1));
      costsForAir.put(TerrainType.SHOAL, InfiniteInteger.create(1));
      costsForAir.put(TerrainType.PLAINS, InfiniteInteger.create(1));
      costsForAir.put(TerrainType.WOODS, InfiniteInteger.create(1));
      costsForAir.put(TerrainType.MOUNTAINS, InfiniteInteger.create(1));
      costsForAir.put(TerrainType.BRIDGE, InfiniteInteger.create(1));
      costsForAir.put(TerrainType.CITY, InfiniteInteger.create(1));
      costsForAir.put(TerrainType.BASE, InfiniteInteger.create(1));
      costsForAir.put(TerrainType.AIRPORT, InfiniteInteger.create(1));
      costsForAir.put(TerrainType.PORT, InfiniteInteger.create(1));
      costsForAir.put(TerrainType.HQ, InfiniteInteger.create(1));
      costsForAir.put(TerrainType.SEA, InfiniteInteger.create(1));
      costsForAir.put(TerrainType.REEF, InfiniteInteger.create(1));

      Map<MovementType, Map<TerrainType, InfiniteInteger>> movementCosts =
            new HashMap<MovementType, Map<TerrainType, InfiniteInteger>>(MovementType.values().length);
      movementCosts.put(MovementType.FOOT, costsForFoot);
      movementCosts.put(MovementType.MECH, costsForMech);
      movementCosts.put(MovementType.TREADS, costsForTreads);
      movementCosts.put(MovementType.TIRE, costsForTires);
      movementCosts.put(MovementType.SEA, costsForSea);
      movementCosts.put(MovementType.SEA_TRANSPORT, costsForSeaTransport);
      movementCosts.put(MovementType.AIR, costsForAir);

      return movementCosts;
   }

   InfiniteInteger getTravelCostForMovementTypeOnTerrainType(MovementType movement, TerrainType terrain) {
      InfiniteInteger result = _movementCosts.get(movement).get(terrain);
      if (result == null) {
         throw new MovementCostMissingException();
      }
      return result;
   }

   private static class MovementCostMissingException extends RuntimeException {
   }
}
