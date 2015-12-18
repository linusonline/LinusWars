package se.lolektivet.linus.linuswars.logic.pathfinding;

import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.Direction;

/**
 * Created by Linus on 2015-12-07.
 */
public class PathFactory {

   public static Path create(Position origin) {
      return create(origin, false);
   }

   public static Path create(Position origin, boolean isAutoBackTrack) {
      return new Path(origin, isAutoBackTrack);
   }

   public static Path create(Path otherPath) {
      return new Path(otherPath);
   }

   public static Path create(Path otherPath, boolean isAutoBackTrack) {
      return new Path(otherPath, isAutoBackTrack);
   }


   public static Path create(Position origin, Direction direction) {
      Path thePath = create(origin, false);
      addStepToPath(thePath, direction);
      return thePath;
   }

   public static Path create(Position origin, Direction dir1, Direction dir2) {
      Path thePath = create(origin, dir1);
      addStepToPath(thePath, dir2);
      return thePath;
   }

   public static Path create(Position origin, Direction dir1, Direction dir2, Direction dir3) {
      Path thePath = create(origin, dir1, dir2);
      addStepToPath(thePath, dir3);
      return thePath;
   }

   public static Path create(Position origin, Direction dir1, Direction dir2, Direction dir3, Direction dir4) {
      Path thePath = create(origin, dir1, dir2, dir3);
      addStepToPath(thePath, dir4);
      return thePath;
   }

   public static Path create(Position origin, Direction dir1, Direction dir2, Direction dir3, Direction dir4, Direction dir5) {
      Path thePath = create(origin, dir1, dir2, dir3, dir4);
      addStepToPath(thePath, dir5);
      return thePath;
   }

   public static Path create(Position origin, Direction dir1, Direction dir2, Direction dir3, Direction dir4, Direction dir5, Direction dir6) {
      Path thePath = create(origin, dir1, dir2, dir3, dir4, dir5);
      addStepToPath(thePath, dir6);
      return thePath;
   }

   private static void addStepToPath(Path path, Direction direction) {
      path.addPoint(path.getFinalPosition().getPositionAfterStep(direction));
   }

}
