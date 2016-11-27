package se.lolektivet.linus.linuswars.core.pathfinding;

import se.lolektivet.linus.linuswars.core.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linus on 2014-09-22.
 */
public class PathWithCost {

   private final List<Cost> _costsForPoints;
   private Cost _totalCost;
   private final Path _thePath;

   private PathWithCost(Path thePath, List<Cost> costs) {
      _thePath = thePath;
      _costsForPoints = new ArrayList<Cost>(costs);
      calculateTotalCost();
   }

   public PathWithCost(PathWithCost otherPath) {
      this(new Path(otherPath._thePath),
            new ArrayList<Cost>(otherPath._costsForPoints));
   }

   public PathWithCost(Position origin, boolean isAutoBackTrack) {
      this(new Path(origin, isAutoBackTrack),
            new ArrayList<Cost>(0));
   }

   private void calculateTotalCost() {
      setCostToZero();
      for (int i = 0; i < getLength(); i++) {
         _totalCost = Cost.add(_totalCost, _costsForPoints.get(i));
      }
   }

   private void setCostToZero() {
      _totalCost = new Cost();
   }

   public void addPoint(Position newPoint, Cost additionalCost) {
      int oldLength = getLength();
      _thePath.addPoint(newPoint);
      if (oldLength < getLength()) {
         _costsForPoints.add(additionalCost);
      } else {
         _costsForPoints.remove(_costsForPoints.size() - 1);
      }
      calculateTotalCost();
   }

   private int getLength() {
      return _thePath.getLength();
   }

   public Cost getTotalCost() {
      return _totalCost;
   }

   public boolean isBetterThan(PathWithCost otherPath) {
      return _totalCost.isEqualOrBetterThan(otherPath.getTotalCost());
   }

   public boolean isBetterInATieThan(PathWithCost otherPath) {
      return _totalCost.isEqualOrBetterInATieThan(otherPath.getTotalCost());
   }

   public Position getFinalPosition() {
      return _thePath.getFinalPosition();
   }

   public Path getPath(){
      return _thePath;
   }
}
