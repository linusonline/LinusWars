package se.lolektivet.linus.linuswars.logic;

import se.lolektivet.linus.linuswars.logic.enums.MovementType;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;
import se.lolektivet.linus.linuswars.logic.pathfinding.PotentiallyInfiniteInteger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Linus on 2014-09-19.
 */
public class MovementLogic {
   private final Map<MovementType, Map<TerrainType, PotentiallyInfiniteInteger>> _movementCosts;

   public MovementLogic() {
      _movementCosts = initializeMovementCosts();
   }

   private Map<MovementType, Map<TerrainType, PotentiallyInfiniteInteger>> initializeMovementCosts() {
      Map<TerrainType, PotentiallyInfiniteInteger> costsForTreads = new HashMap<TerrainType, PotentiallyInfiniteInteger>(TerrainType.values().length);
      costsForTreads.put(TerrainType.ROAD, PotentiallyInfiniteInteger.create(1));
      costsForTreads.put(TerrainType.RIVER, PotentiallyInfiniteInteger.infinite());
      costsForTreads.put(TerrainType.SHOAL, PotentiallyInfiniteInteger.create(1));
      costsForTreads.put(TerrainType.PLAINS, PotentiallyInfiniteInteger.create(1));
      costsForTreads.put(TerrainType.WOODS, PotentiallyInfiniteInteger.create(2));
      costsForTreads.put(TerrainType.MOUNTAINS, PotentiallyInfiniteInteger.infinite());
      costsForTreads.put(TerrainType.CITY, PotentiallyInfiniteInteger.create(1));
      costsForTreads.put(TerrainType.BASE, PotentiallyInfiniteInteger.create(1));
      costsForTreads.put(TerrainType.AIRPORT, PotentiallyInfiniteInteger.create(1));
      costsForTreads.put(TerrainType.PORT, PotentiallyInfiniteInteger.create(1));
      costsForTreads.put(TerrainType.HQ, PotentiallyInfiniteInteger.create(1));
      costsForTreads.put(TerrainType.SEA, PotentiallyInfiniteInteger.infinite());
      costsForTreads.put(TerrainType.REEF, PotentiallyInfiniteInteger.infinite());

      Map<TerrainType, PotentiallyInfiniteInteger> costsForTires = new HashMap<TerrainType, PotentiallyInfiniteInteger>(TerrainType.values().length);
      costsForTires.put(TerrainType.ROAD, PotentiallyInfiniteInteger.create(1));
      costsForTires.put(TerrainType.RIVER, PotentiallyInfiniteInteger.infinite());
      costsForTires.put(TerrainType.SHOAL, PotentiallyInfiniteInteger.create(1));
      costsForTires.put(TerrainType.PLAINS, PotentiallyInfiniteInteger.create(2));
      costsForTires.put(TerrainType.WOODS, PotentiallyInfiniteInteger.create(3));
      costsForTires.put(TerrainType.MOUNTAINS, PotentiallyInfiniteInteger.infinite());
      costsForTires.put(TerrainType.CITY, PotentiallyInfiniteInteger.create(1));
      costsForTires.put(TerrainType.BASE, PotentiallyInfiniteInteger.create(1));
      costsForTires.put(TerrainType.AIRPORT, PotentiallyInfiniteInteger.create(1));
      costsForTires.put(TerrainType.PORT, PotentiallyInfiniteInteger.create(1));
      costsForTires.put(TerrainType.HQ, PotentiallyInfiniteInteger.create(1));
      costsForTires.put(TerrainType.SEA, PotentiallyInfiniteInteger.infinite());
      costsForTires.put(TerrainType.REEF, PotentiallyInfiniteInteger.infinite());

      Map<TerrainType, PotentiallyInfiniteInteger> costsForFoot = new HashMap<TerrainType, PotentiallyInfiniteInteger>(TerrainType.values().length);
      costsForFoot.put(TerrainType.ROAD, PotentiallyInfiniteInteger.create(1));
      costsForFoot.put(TerrainType.RIVER, PotentiallyInfiniteInteger.create(2));
      costsForFoot.put(TerrainType.SHOAL, PotentiallyInfiniteInteger.create(1));
      costsForFoot.put(TerrainType.PLAINS, PotentiallyInfiniteInteger.create(1));
      costsForFoot.put(TerrainType.WOODS, PotentiallyInfiniteInteger.create(1));
      costsForFoot.put(TerrainType.MOUNTAINS, PotentiallyInfiniteInteger.create(2));
      costsForFoot.put(TerrainType.CITY, PotentiallyInfiniteInteger.create(1));
      costsForFoot.put(TerrainType.BASE, PotentiallyInfiniteInteger.create(1));
      costsForFoot.put(TerrainType.AIRPORT, PotentiallyInfiniteInteger.create(1));
      costsForFoot.put(TerrainType.PORT, PotentiallyInfiniteInteger.create(1));
      costsForFoot.put(TerrainType.HQ, PotentiallyInfiniteInteger.create(1));
      costsForFoot.put(TerrainType.SEA, PotentiallyInfiniteInteger.infinite());
      costsForFoot.put(TerrainType.REEF, PotentiallyInfiniteInteger.infinite());

      Map<TerrainType, PotentiallyInfiniteInteger> costsForMech = new HashMap<TerrainType, PotentiallyInfiniteInteger>(TerrainType.values().length);
      costsForMech.put(TerrainType.ROAD, PotentiallyInfiniteInteger.create(1));
      costsForMech.put(TerrainType.RIVER, PotentiallyInfiniteInteger.create(1));
      costsForMech.put(TerrainType.SHOAL, PotentiallyInfiniteInteger.create(1));
      costsForMech.put(TerrainType.PLAINS, PotentiallyInfiniteInteger.create(1));
      costsForMech.put(TerrainType.WOODS, PotentiallyInfiniteInteger.create(1));
      costsForMech.put(TerrainType.MOUNTAINS, PotentiallyInfiniteInteger.create(1));
      costsForMech.put(TerrainType.CITY, PotentiallyInfiniteInteger.create(1));
      costsForMech.put(TerrainType.BASE, PotentiallyInfiniteInteger.create(1));
      costsForMech.put(TerrainType.AIRPORT, PotentiallyInfiniteInteger.create(1));
      costsForMech.put(TerrainType.PORT, PotentiallyInfiniteInteger.create(1));
      costsForMech.put(TerrainType.HQ, PotentiallyInfiniteInteger.create(1));
      costsForMech.put(TerrainType.SEA, PotentiallyInfiniteInteger.infinite());
      costsForMech.put(TerrainType.REEF, PotentiallyInfiniteInteger.infinite());

      Map<TerrainType, PotentiallyInfiniteInteger> costsForSea = new HashMap<TerrainType, PotentiallyInfiniteInteger>(TerrainType.values().length);
      costsForSea.put(TerrainType.ROAD, PotentiallyInfiniteInteger.infinite());
      costsForSea.put(TerrainType.RIVER, PotentiallyInfiniteInteger.infinite());
      costsForSea.put(TerrainType.SHOAL, PotentiallyInfiniteInteger.infinite());
      costsForSea.put(TerrainType.PLAINS, PotentiallyInfiniteInteger.infinite());
      costsForSea.put(TerrainType.WOODS, PotentiallyInfiniteInteger.infinite());
      costsForSea.put(TerrainType.MOUNTAINS, PotentiallyInfiniteInteger.infinite());
      costsForSea.put(TerrainType.CITY, PotentiallyInfiniteInteger.infinite());
      costsForSea.put(TerrainType.BASE, PotentiallyInfiniteInteger.infinite());
      costsForSea.put(TerrainType.AIRPORT, PotentiallyInfiniteInteger.infinite());
      costsForSea.put(TerrainType.PORT, PotentiallyInfiniteInteger.infinite());
      costsForSea.put(TerrainType.HQ, PotentiallyInfiniteInteger.infinite());
      costsForSea.put(TerrainType.SEA, PotentiallyInfiniteInteger.create(1));
      costsForSea.put(TerrainType.REEF, PotentiallyInfiniteInteger.create(2));

      Map<TerrainType, PotentiallyInfiniteInteger> costsForSeaTransport = new HashMap<TerrainType, PotentiallyInfiniteInteger>(TerrainType.values().length);
      costsForSeaTransport.put(TerrainType.ROAD, PotentiallyInfiniteInteger.infinite());
      costsForSeaTransport.put(TerrainType.RIVER, PotentiallyInfiniteInteger.infinite());
      costsForSeaTransport.put(TerrainType.SHOAL, PotentiallyInfiniteInteger.create(1));
      costsForSeaTransport.put(TerrainType.PLAINS, PotentiallyInfiniteInteger.infinite());
      costsForSeaTransport.put(TerrainType.WOODS, PotentiallyInfiniteInteger.infinite());
      costsForSeaTransport.put(TerrainType.MOUNTAINS, PotentiallyInfiniteInteger.infinite());
      costsForSeaTransport.put(TerrainType.CITY, PotentiallyInfiniteInteger.infinite());
      costsForSeaTransport.put(TerrainType.BASE, PotentiallyInfiniteInteger.infinite());
      costsForSeaTransport.put(TerrainType.AIRPORT, PotentiallyInfiniteInteger.infinite());
      costsForSeaTransport.put(TerrainType.PORT, PotentiallyInfiniteInteger.infinite());
      costsForSeaTransport.put(TerrainType.HQ, PotentiallyInfiniteInteger.infinite());
      costsForSeaTransport.put(TerrainType.SEA, PotentiallyInfiniteInteger.create(1));
      costsForSeaTransport.put(TerrainType.REEF, PotentiallyInfiniteInteger.create(2));

      Map<TerrainType, PotentiallyInfiniteInteger> costsForAir = new HashMap<TerrainType, PotentiallyInfiniteInteger>(TerrainType.values().length);
      costsForAir.put(TerrainType.ROAD, PotentiallyInfiniteInteger.create(1));
      costsForAir.put(TerrainType.RIVER, PotentiallyInfiniteInteger.create(1));
      costsForAir.put(TerrainType.SHOAL, PotentiallyInfiniteInteger.create(1));
      costsForAir.put(TerrainType.PLAINS, PotentiallyInfiniteInteger.create(1));
      costsForAir.put(TerrainType.WOODS, PotentiallyInfiniteInteger.create(1));
      costsForAir.put(TerrainType.MOUNTAINS, PotentiallyInfiniteInteger.create(1));
      costsForAir.put(TerrainType.CITY, PotentiallyInfiniteInteger.create(1));
      costsForAir.put(TerrainType.BASE, PotentiallyInfiniteInteger.create(1));
      costsForAir.put(TerrainType.AIRPORT, PotentiallyInfiniteInteger.create(1));
      costsForAir.put(TerrainType.PORT, PotentiallyInfiniteInteger.create(1));
      costsForAir.put(TerrainType.HQ, PotentiallyInfiniteInteger.create(1));
      costsForAir.put(TerrainType.SEA, PotentiallyInfiniteInteger.create(1));
      costsForAir.put(TerrainType.REEF, PotentiallyInfiniteInteger.create(1));

      Map<MovementType, Map<TerrainType, PotentiallyInfiniteInteger>> movementCosts =
            new HashMap<MovementType, Map<TerrainType, PotentiallyInfiniteInteger>>(MovementType.values().length);
      movementCosts.put(MovementType.FOOT, costsForFoot);
      movementCosts.put(MovementType.MECH, costsForMech);
      movementCosts.put(MovementType.TREADS, costsForTreads);
      movementCosts.put(MovementType.TIRE, costsForTires);
      movementCosts.put(MovementType.SEA, costsForSea);
      movementCosts.put(MovementType.SEA_TRANSPORT, costsForSeaTransport);
      movementCosts.put(MovementType.AIR, costsForAir);

      return movementCosts;
   }

   PotentiallyInfiniteInteger getTravelCostForMovementTypeOnTerrainType(MovementType movement, TerrainType terrain) {
      PotentiallyInfiniteInteger result = _movementCosts.get(movement).get(terrain);
      if (result == null) {
         throw new MovementCostMissingException();
      }
      return result;
   }

   private static class MovementCostMissingException extends RuntimeException {
   }
}
