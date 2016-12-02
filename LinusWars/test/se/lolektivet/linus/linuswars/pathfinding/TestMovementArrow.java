package se.lolektivet.linus.linuswars.pathfinding;

import org.junit.Test;
import se.lolektivet.linus.linuswars.MovementArrow;
import se.lolektivet.linus.linuswars.core.LinusWarsTest;
import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.pathfinding.Path;
import se.lolektivet.linus.linuswars.core.pathfinding.PathFactory;
import se.lolektivet.linus.linuswars.graphics.MovementArrowSection;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Linus on 2014-09-25.
 */
   public class TestMovementArrow extends LinusWarsTest {

   @Test
   public void testTrivial() {
      Path path = PathFactory.create(new Position(0, 0), true);
      MovementArrow movementArrow = new MovementArrow(path);
      movementArrow.build();
      assertTrue(movementArrow.getSpriteList().isEmpty());
   }

   @Test
   public void testOneStepSouth() {
      Path path = PathFactory.create(new Position(0, 0), true);
      path.addPoint(new Position(0, 1));
      MovementArrow movementArrow = new MovementArrow(path);
      movementArrow.build();
      List<MovementArrowSection> arrowList =  movementArrow.getSpriteList();
      assertTrue(arrowList.size() == 1);
      assertTrue(arrowList.get(0) == MovementArrowSection.END_S);
   }

   @Test
   public void testOneStepNorth() {
      Path path = PathFactory.create(new Position(1, 1), true);
      path.addPoint(new Position(1, 0));
      MovementArrow movementArrow = new MovementArrow(path);
      movementArrow.build();
      List<MovementArrowSection> arrowList =  movementArrow.getSpriteList();
      assertTrue(arrowList.size() == 1);
      assertTrue(arrowList.get(0) == MovementArrowSection.END_N);
   }

   @Test
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

   @Test
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

   @Test
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

   @Test
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

   @Test
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

   @Test
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

   @Test
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
