package se.lolektivet.linus.linuswars.core.pathfinding;

import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.game.LogicalUnit;
import se.lolektivet.linus.linuswars.core.game.WarGameQueries;
import se.lolektivet.linus.linuswars.core.pathfinding.Cost;
import se.lolektivet.linus.linuswars.core.pathfinding.CostCalculator;
import se.lolektivet.linus.linuswars.core.pathfinding.PathWithCost;

import java.util.*;

/**
 * Created by Linus on 2014-09-23.
 */
public class PathFinder {
   private final LogicalUnit _travellingUnit;
   private final WarGameQueries _warGameQueries;
   private final CostCalculator _costCalculator;
   private Cost _limit;

   private final List<PathWithCost> _pathsToExplore = new ArrayList<PathWithCost>(0);
   private final Map<Position, List<PathWithCost>> _optimalPathsFound = new HashMap<Position, List<PathWithCost>>(1);

   public PathFinder(LogicalUnit travellingUnit, WarGameQueries warGameQueries, CostCalculator costCalculator) {
      _travellingUnit = travellingUnit;
      _warGameQueries = warGameQueries;
      _costCalculator = costCalculator;
   }

   public Map<Position, PathWithCost> getOptimalPathsToAllReachablePoints(Cost limit) {
      _limit = limit;
      calculateOptimalPathsToAllReachablePoints(_warGameQueries.getPositionOfUnit(_travellingUnit));
      Map<Position, PathWithCost> optimalPaths = new HashMap<Position, PathWithCost>(_optimalPathsFound.size());
      for (Map.Entry<Position, List<PathWithCost>> entry : _optimalPathsFound.entrySet()) {
         optimalPaths.put(entry.getKey(), getBestPathInATie(entry.getValue()));
      }
      return optimalPaths;
   }

   private PathWithCost getBestPathInATie(List<PathWithCost> paths) {
      PathWithCost candidate = paths.get(0);
      for (PathWithCost path : paths) {
         if (path.isBetterInATieThan(candidate)) {
            candidate = path;
         }
      }
      return candidate;
   }

   private void calculateOptimalPathsToAllReachablePoints(Position origin) {
      // Initialize A* algorithm.
      PathWithCost theTrivialPath = new PathWithCost(origin, false);
      addUncontestedCandidatePath(theTrivialPath, origin);

      // Run loop of A* algorithm
      while (!_pathsToExplore.isEmpty()) {
         PathWithCost pathToExploreNext = popPathList(_pathsToExplore);
         Collection<PathWithCost> nextStepPaths = getPathsThatContinueThisPathOneStep(pathToExploreNext);
         for (PathWithCost candidate : nextStepPaths) {
            evaluateNewPath(candidate);
         }
      }
   }

   private PathWithCost popPathList(List<PathWithCost> pathList) {
      PathWithCost path = pathList.get(0);
      pathList.remove(0);
      return path;
   }

   private Collection<PathWithCost> getPathsThatContinueThisPathOneStep(PathWithCost path) {
      Collection<PathWithCost> nextStepPaths = new HashSet<PathWithCost>(4);
      Collection<Position> nextSteps = _warGameQueries.getAdjacentPositions(path.getFinalPosition());
      for (Position step : nextSteps) {
         PathWithCost nextStepPath = new PathWithCost(path);
         Cost additionalCost = _costCalculator.getCostForPosition(step);
         nextStepPath.addPoint(step, additionalCost);
         nextStepPaths.add(nextStepPath);
      }
      return nextStepPaths;
   }

   private void evaluateNewPath(PathWithCost newPath) {
      if (!newPath.getTotalCost().isEqualOrBetterThan(_limit)) {
         return;
      }

      Position destination = newPath.getFinalPosition();
      List<PathWithCost> previousCandidates = _optimalPathsFound.get(destination);
      if (previousCandidates == null) {
         addUncontestedCandidatePath(newPath, destination);
      } else if (!someOldCandidateIsBetterThan(previousCandidates, newPath)) {
         removeAnyCandidatesWorseThan(previousCandidates, newPath);
         addNewCandidatePath(previousCandidates, newPath);
      }
   }

   private boolean someOldCandidateIsBetterThan(List<PathWithCost> previousCandidates, PathWithCost newPath) {
      for (PathWithCost oldPath : previousCandidates) {
         if (oldPath.isBetterThan(newPath)) {
            return true;
         }
      }
      return false;
   }

   private void removeAnyCandidatesWorseThan(List<PathWithCost> previousCandidates, PathWithCost newPath) {
      List<PathWithCost> pathsToRemove = new ArrayList<PathWithCost>(0);
      for (PathWithCost oldPath : previousCandidates) {
         if (newPath.isBetterThan(oldPath)) {
            pathsToRemove.add(oldPath);
         }
      }
      for (PathWithCost pathToRemove : pathsToRemove) {
         previousCandidates.remove(pathToRemove);
      }
   }

   private void addUncontestedCandidatePath(PathWithCost newPath, Position destination) {
      List<PathWithCost> candidateList = new ArrayList<PathWithCost>(1);
      _optimalPathsFound.put(destination, candidateList);
      addNewCandidatePath(candidateList, newPath);
   }

   private void addNewCandidatePath(List<PathWithCost> candidateList, PathWithCost path) {
      candidateList.add(path);
      _pathsToExplore.add(path);
   }
}
