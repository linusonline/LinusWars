package se.lolektivet.linus.linuswars.pathfinding;

import junit.framework.TestCase;
import se.lolektivet.linus.linuswars.MovementArrow;
import se.lolektivet.linus.linuswars.graphics.MovementArrowSection;
import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.pathfinding.Path;
import se.lolektivet.linus.linuswars.core.pathfinding.PathFactory;

import java.util.List;

/**
 * Created by Linus on 2014-09-25.
 */
public class TestMovementArrow extends TestCase {

   public void testTrivial() {
      Path path = PathFactory.create(new Position(0, 0), true);
      MovementArrow movementArrow = new MovementArrow(path);
      movementArrow.build();
      assertTrue(movementArrow.getSpriteList().isEmpty());
   }

   public void testOneStepSouth() {
      Path path = PathFactory.create(new Position(0, 0), true);
      path.addPoint(new Position(0, 1));
      MovementArrow movementArrow = new MovementArrow(path);
      movementArrow.build();
      List<MovementArrowSection> arrowList =  movementArrow.getSpriteList();
      assertTrue(arrowList.size() == 1);
      assertTrue(arrowList.get(0) == MovementArrowSection.END_S);
   }

   public void testOneStepNorth() {
      Path path = PathFactory.create(new Position(1, 1), true);
      path.addPoint(new Position(1, 0));
      MovementArrow movementArrow = new MovementArrow(path);
      movementArrow.build();
      List<MovementArrowSection> arrowList =  movementArrow.getSpriteList();
      assertTrue(arrowList.size() == 1);
      assertTrue(arrowList.get(0) == MovementArrowSection.END_N);
   }

   public void testTwoDown() {
      Path path = PathFactory.create(new Position(0, 0), true);
      path.addPoint(new Position(0, 1));
      path.addPoint(new Position(0, 2));
      MovementArrow movementArrow = new MovementArrow(path);
      movementArrow.build();
      List<MovementArrowSection> arrowList =  movementArrow.getSpriteList();
      assertTrue(arrowList.size() == 2);
      assertTrue(arrowList.get(0) == MovementArrowSection.VERTICAL);
      assertTrue(arrowList.get(1) == MovementArrowSection.END_S);
   }

   public void testTwoRight() {
      Path path = PathFactory.create(new Position(0, 0), true);
      path.addPoint(new Position(1, 0));
      path.addPoint(new Position(2, 0));
      MovementArrow movementArrow = new MovementArrow(path);
      movementArrow.build();
      List<MovementArrowSection> arrowList =  movementArrow.getSpriteList();
      assertTrue(arrowList.size() == 2);
      assertTrue(arrowList.get(0) == MovementArrowSection.HORIZONTAL);
      assertTrue(arrowList.get(1) == MovementArrowSection.END_E);
   }

   public void testBendSE() {
      Path path = PathFactory.create(new Position(0, 0), true);
      path.addPoint(new Position(1, 0));
      path.addPoint(new Position(1, 1));
      MovementArrow movementArrow = new MovementArrow(path);
      movementArrow.build();
      List<MovementArrowSection> arrowList =  movementArrow.getSpriteList();
      assertTrue(arrowList.size() == 2);
      assertTrue(arrowList.get(0) == MovementArrowSection.BEND_S_TO_W);
      assertTrue(arrowList.get(1) == MovementArrowSection.END_S);
   }

   public void testBendNW() {
      Path path = PathFactory.create(new Position(1, 1), true);
      path.addPoint(new Position(1, 0));
      path.addPoint(new Position(0, 0));
      MovementArrow movementArrow = new MovementArrow(path);
      movementArrow.build();
      List<MovementArrowSection> arrowList =  movementArrow.getSpriteList();
      assertTrue(arrowList.size() == 2);
      assertTrue(arrowList.get(0) == MovementArrowSection.BEND_S_TO_W);
      assertTrue(arrowList.get(1) == MovementArrowSection.END_W);
   }

   public void testBacktrack() {
      Path path = PathFactory.create(new Position(0, 0), true);
      path.addPoint(new Position(1, 0));
      path.addPoint(new Position(2, 0));
      MovementArrow movementArrow = new MovementArrow(path);
      movementArrow.build();
      movementArrow.addPoint(new Position(1, 0));
      List<MovementArrowSection> arrowList =  movementArrow.getSpriteList();
      assertTrue(arrowList.size() == 1);
      assertTrue(arrowList.get(0) == MovementArrowSection.END_E);
   }

   public void testBacktrackBend() {
      Path path = PathFactory.create(new Position(0, 0), true);
      path.addPoint(new Position(1, 0));
      path.addPoint(new Position(1, 1));
      MovementArrow movementArrow = new MovementArrow(path);
      movementArrow.build();
      movementArrow.addPoint(new Position(1, 0));
      List<MovementArrowSection> arrowList =  movementArrow.getSpriteList();
      assertTrue(arrowList.size() == 1);
      assertTrue(arrowList.get(0) == MovementArrowSection.END_E);
   }

   public void testBuildFromScratch() {
      Path path = PathFactory.create(new Position(0, 0), true);
      MovementArrow movementArrow = new MovementArrow(path);
      movementArrow.build();
      movementArrow.addPoint(new Position(1, 0));
      List<MovementArrowSection> arrowList =  movementArrow.getSpriteList();
      assertTrue(arrowList.size() == 1);
      assertTrue(arrowList.get(0) == MovementArrowSection.END_E);
   }

}
