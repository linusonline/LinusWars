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
   private int _length;

   private Path(Position origin, List<Position> pointsOnPath, boolean isAutoBackTrack) {
      _origin = origin;
      _pointsOnPath = new ArrayList<>(pointsOnPath);
      _length = _pointsOnPath.size();
      _isAutoBackTrack = isAutoBackTrack;
   }

   public Path(Position origin) {
      this(origin, false);
   }

   public Path(Position origin, boolean isAutoBackTrack) {
      this(origin, new ArrayList<>(0), isAutoBackTrack);
   }

   public Path(Path otherPath) {
      this(otherPath._origin, otherPath._pointsOnPath, otherPath._isAutoBackTrack);
   }

   public Path(Path otherPath, boolean isAutoBackTrack) {
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
      return new ArrayList<Position>(_pointsOnPath);
   }

   public int getLength() {
      return _length;
   }

   public boolean isEmpty() {
      return getLength() == 0;
   }

   private void backUpOneStep() {
      if (_length > 0) {
         _pointsOnPath.remove(_pointsOnPath.size() - 1);
         _length--;
      }
   }

   private Position getLastPoint() {
      if (_length == 0) {
         return _origin;
      } else {
         return _pointsOnPath.get(_length - 1);
      }
   }

   private Position getPointBeforeLast() {
      if (_length == 0) {
         return null;
      } else if (_length == 1) {
         return _origin;
      } else {
         return _pointsOnPath.get(_length - 2);
      }
   }

   public void addPoint(Position newPoint) {
      if (!getLastPoint().isAdjacentTo(newPoint)) {
         throw new IllegalPathException();
      }
      if (_isAutoBackTrack) {
         if (_length > 0) {
            //noinspection ConstantConditions
            if (getPointBeforeLast().equals(newPoint)) {
               backUpOneStep();
               return;
            }
         }
      }
      _pointsOnPath.add(new Position(newPoint));
      _length++;
   }

   public Position getBacktrackPosition() {
      if (isEmpty()){
         throw new IllegalStateException();
      } else if (getLength() == 1) {
         return _origin;
      } else {
         return _pointsOnPath.get(_pointsOnPath.size() - 2);
      }
   }

   static class IllegalPathException extends RuntimeException {
   }
}
