package se.lolektivet.linus.linuswars;

import se.lolektivet.linus.linuswars.graphics.MovementArrowSection;
import se.lolektivet.linus.linuswars.logic.enums.Direction;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.pathfinding.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linus on 2014-09-24.
 */
public class MovementArrow {
   private final Path _arrowPath;
   private final List<MovementArrowSection> _spriteList;
   private final List<Direction> _listOfDirections;

   public MovementArrow(Position origin) {
      this(new Path(new Position(origin), true));
   }

   public MovementArrow(Path arrowPath) {
      _spriteList = new ArrayList<>(arrowPath.getLength());
      _arrowPath = new Path(arrowPath, true);
      _listOfDirections = new ArrayList<>(_spriteList.size());
   }

   public void build() {
      List<Position> positionList = _arrowPath.getPositionList();
      if (positionList.isEmpty()) {
         return;
      }
      Position fromPosition = _arrowPath.getOrigin();
      for (Position nextPosition : positionList) {
         _listOfDirections.add(getDirection(fromPosition, nextPosition));
         fromPosition = nextPosition;
      }

      Direction fromDirection = _listOfDirections.get(0);
      Direction toDirection;
      for (int i = 1; i < _listOfDirections.size(); i++) {
         toDirection = _listOfDirections.get(i);
         _spriteList.add(getArrowSectionForDirections(fromDirection, toDirection));
         fromDirection = toDirection;
      }
      _spriteList.add(getArrowEndSectionForDirection(fromDirection));
   }

   private MovementArrowSection getArrowEndSectionForDirection(Direction direction) {
      switch (direction) {
         case LEFT:
            return MovementArrowSection.END_W;
         case RIGHT:
            return MovementArrowSection.END_E;
         case UP:
            return MovementArrowSection.END_N;
         case DOWN:
         default:
            return MovementArrowSection.END_S;
      }
   }

   private MovementArrowSection getArrowSectionForDirections(Direction inDirection, Direction outDirection) {
      assert !(getMirror(inDirection) == outDirection);
      assert inDirection != null;
      assert outDirection != null;
      if (directionPairsYieldSameArrowSection(inDirection, outDirection, Direction.UP, Direction.UP)) {
         return MovementArrowSection.VERTICAL;
      }
      if (directionPairsYieldSameArrowSection(inDirection, outDirection, Direction.RIGHT, Direction.RIGHT)) {
         return MovementArrowSection.HORIZONTAL;
      }
      if (directionPairsYieldSameArrowSection(inDirection, outDirection, Direction.UP, Direction.LEFT)) {
         return MovementArrowSection.BEND_S_TO_W;
      }
      if (directionPairsYieldSameArrowSection(inDirection, outDirection, Direction.UP, Direction.RIGHT)) {
         return MovementArrowSection.BEND_S_TO_E;
      }
      if (directionPairsYieldSameArrowSection(inDirection, outDirection, Direction.DOWN, Direction.LEFT)) {
         return MovementArrowSection.BEND_N_TO_W;
      }
      if (directionPairsYieldSameArrowSection(inDirection, outDirection, Direction.DOWN, Direction.RIGHT)) {
         return MovementArrowSection.BEND_N_TO_E;
      }
      throw new IllegalArgumentException();
   }

   private boolean directionPairsYieldSameArrowSection(
         Direction firstInPairOne,
         Direction secondInPairOne,
         Direction firstInPairTwo,
         Direction secondInPairTwo) {
      return directionsEqualPairWise(firstInPairOne, secondInPairOne, firstInPairTwo, secondInPairTwo) ||
            (getMirror(firstInPairOne) == secondInPairTwo &&
                  getMirror(secondInPairOne) == firstInPairTwo);
   }

   private Direction getMirror(Direction direction) {
      switch (direction) {
         case LEFT:
            return Direction.RIGHT;
         case RIGHT:
            return Direction.LEFT;
         case UP:
            return Direction.DOWN;
         case DOWN:
         default:
            return Direction.UP;
      }
   }

   private boolean directionsEqualPairWise(
         Direction firstInPairOne,
         Direction secondInPairOne,
         Direction firstInPairTwo,
         Direction secondInPairTwo) {
      return (firstInPairOne == firstInPairTwo &&
            secondInPairOne == secondInPairTwo);
   }

   private Direction getDirection(Position fromPosition, Position toPosition) {
      if (fromPosition.getX() == toPosition.getX()) {
         if (fromPosition.getY() < toPosition.getY()) {
            return Direction.DOWN;
         } else {
            return Direction.UP;
         }
      } else {
         if (fromPosition.getX() < toPosition.getX()) {
            return Direction.RIGHT;
         } else {
            return Direction.LEFT;
         }
      }
   }

   public Path getPath() {
      return new Path(_arrowPath);
   }

   public int getLength() {
      return _arrowPath.getLength();
   }

   public boolean isEmpty() {
      return _arrowPath.isEmpty();
   }

   public MovementArrowSection getSection(int index) {
      return _spriteList.get(index);
   }

   public Position getFinalPosition() {
      return _arrowPath.getFinalPosition();
   }

   public Position getBacktrackPosition() {
      return _arrowPath.getBacktrackPosition();
   }

   public Position getPosition(int index) {
      return _arrowPath.getPosition(index);
   }

   public List<MovementArrowSection> getSpriteList() {
      return new ArrayList<>(_spriteList);
   }

   public void addPoint(Position newPoint) {
      Position oldLastPosition = _arrowPath.getFinalPosition();
      int oldLength = _arrowPath.getLength();
      _arrowPath.addPoint(newPoint);
      if (oldLength < _arrowPath.getLength()) {
         lengthenArrowOneStep(oldLastPosition, _arrowPath.getFinalPosition());
      } else {
         shortenArrowOneStep();
      }
   }

   private void lengthenArrowOneStep(Position oldLastPosition, Position newPosition) {
      Direction newLastDirection = getDirection(oldLastPosition, newPosition);
      if (!_listOfDirections.isEmpty()) {
         removeLast(_spriteList);
         Direction oldLastDirection = _listOfDirections.get(_listOfDirections.size() - 1);
         _spriteList.add(getArrowSectionForDirections(oldLastDirection, newLastDirection));
      }
      _listOfDirections.add(newLastDirection);
      _spriteList.add(getArrowEndSectionForDirection(newLastDirection));
   }

   private void shortenArrowOneStep() {
      removeLast(_listOfDirections);
      removeLast(_spriteList);
      if (!_spriteList.isEmpty()) {
         removeLast(_spriteList);
         _spriteList.add(getArrowEndSectionForDirection(_listOfDirections.get(_listOfDirections.size() - 1)));
      }
   }

   private void removeLast(List list) {
      list.remove(list.size() - 1);
   }
}
