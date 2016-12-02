package se.lolektivet.linus.linuswars.core.pathfinding;

import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.Direction;

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

   public static Path create(Position origin, Direction... dirs) {
      Path thePath = create(origin, false);
      for (Direction direction : dirs) {
         addStepToPath(thePath, direction);
      }
      return thePath;
   }

   private static void addStepToPath(Path path, Direction direction) {
      path.addPoint(path.getFinalPosition().getPositionAfterStep(direction));
   }
}
