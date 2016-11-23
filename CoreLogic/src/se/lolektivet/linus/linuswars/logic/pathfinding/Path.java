package se.lolektivet.linus.linuswars.logic.pathfinding;

import se.lolektivet.linus.linuswars.logic.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linus on 2014-09-22.
 */
public class Path {

   private final Position _origin;
   private final List<Position> _pointsOnPath;
   private final boolean _isAutoBackTrack;

   private Path(Position origin, List<Position> pointsOnPath, boolean isAutoBackTrack) {
      _origin = origin;
      _pointsOnPath = new ArrayList<>(pointsOnPath);
      _isAutoBackTrack = isAutoBackTrack;
   }

   Path(Position origin, boolean isAutoBackTrack) {
      this(origin, new ArrayList<>(0), isAutoBackTrack);
   }

   Path(Path otherPath) {
      this(otherPath._origin, otherPath._pointsOnPath, otherPath._isAutoBackTrack);
   }

   Path(Path otherPath, boolean isAutoBackTrack) {
      this(otherPath._origin, otherPath._pointsOnPath, isAutoBackTrack);
   }

   public Position getFinalPosition() {
      return getLastPoint();
   }

   public Position getPosition(int index) {
      return _pointsOnPath.get(index);
   }

   public Position getOrigin() {
      return _origin;
   }

   public List<Position> getPositionList() {
      return new ArrayList<>(_pointsOnPath);
   }

   public int getLength() {
      return _pointsOnPath.size();
   }

   public boolean isEmpty() {
      return getLength() == 0;
   }

   public Position getBacktrackPosition() {
      return getPointBeforeLast();
   }

   public void addPoint(Position newPoint) {
      if (!getLastPoint().isAdjacentTo(newPoint)) {
         throw new IllegalPathException();
      }
      if (_isAutoBackTrack) {
         if (!isEmpty()) {
            //noinspection ConstantConditions
            if (getPointBeforeLast().equals(newPoint)) {
               backUpOneStep();
               return;
            }
         }
      }
      _pointsOnPath.add(new Position(newPoint));
   }

   private Position getLastPoint() {
      if (isEmpty()) {
         return _origin;
      } else {
         return _pointsOnPath.get(getLength() - 1);
      }
   }

   private Position getPointBeforeLast() {
      if (isEmpty()) {
         throw new IllegalStateException();
      } else if (getLength() == 1) {
         return _origin;
      } else {
         return _pointsOnPath.get(getLength() - 2);
      }
   }

   private void backUpOneStep() {
      if (!isEmpty()) {
         _pointsOnPath.remove(_pointsOnPath.size() - 1);
      }
   }

   static class IllegalPathException extends RuntimeException {
   }
}
