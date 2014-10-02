package se.lolektivet.linus.linuswars.graphics;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SpriteSheet;
import se.lolektivet.linus.linuswars.logic.enums.Direction;

/**
 * Created by Linus on 2014-09-26.
 */
public class UnitSprite {
   Animation _standingRightAnimation;
   Animation _standingLeftAnimation;
   Animation _movingRightAnimation;
   Animation _movingLeftAnimation;
   Animation _movingUpAnimation;
   Animation _movingDownAnimation;
   Image _inactiveRight;

   public void init(SpriteSheet unitSpriteSheet) {
      // All Standing sprites should ping pong
      _standingRightAnimation = new Animation(unitSpriteSheet, 0, 0, 2, 0, true, 500, false);
      _standingRightAnimation.setPingPong(true);
      _standingLeftAnimation = new Animation(unitSpriteSheet, 3, 0, 5, 0, true, 500, false);
      _standingLeftAnimation.setPingPong(true);
      // Infantry moving sprites must ping pong
      // Vehicle moving sprites MAY ping pong (?)
      _movingRightAnimation = new Animation(unitSpriteSheet, 0, 1, 2, 1, true, 500, false);
      _movingLeftAnimation = new Animation(unitSpriteSheet, 3, 1, 5, 1, true, 500, false);
      _movingDownAnimation = new Animation(unitSpriteSheet, 0, 2, 2, 2, true, 500, false);
      _movingUpAnimation = new Animation(unitSpriteSheet, 3, 2, 5, 2, true, 500, false);
   }

   public Renderable getSprite(Direction direction, boolean moving) {
      if (!moving && (direction.equals(Direction.UP) || direction.equals(Direction.DOWN))) {
         throw new SpriteNotFoundException();
      }
      switch (direction) {
         case LEFT:
            return moving ? _movingLeftAnimation : _standingLeftAnimation;
         case RIGHT:
            return moving ? _movingRightAnimation : _standingRightAnimation;
         case UP:
            return _movingUpAnimation;
         case DOWN:
         default:
            return _movingDownAnimation;
      }
   }
}
