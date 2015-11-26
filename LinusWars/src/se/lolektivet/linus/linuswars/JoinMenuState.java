package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.logic.LogicalUnit;
import se.lolektivet.linus.linuswars.logic.WarGameMoves;
import se.lolektivet.linus.linuswars.logic.WarGameQueries;
import se.lolektivet.linus.linuswars.logic.enums.Direction;

/**
 * Created by Linus on 2015-11-19.
 */
public class JoinMenuState implements InteractiveGameState  {
   private GraphicalMenu _theJoinMenu;
   private final InteractiveWarGame _interactiveWarGame;
   private final WarGameQueries _warGameQueries;
   private final WarGameMoves _warGameMoves;
   private final LogicalUnit _logicalUnit;
   private final MovementArrow _movementArrow;

   public JoinMenuState(
         InteractiveWarGame interactiveWarGame,
         WarGameQueries warGameQueries,
         WarGameMoves warGameMoves,
         LogicalUnit logicalUnit,
         MovementArrow movementArrow) {
      _interactiveWarGame = interactiveWarGame;
      _warGameQueries = warGameQueries;
      _warGameMoves = warGameMoves;
      _logicalUnit = logicalUnit;
      _movementArrow = movementArrow;
   }

   @Override
   public InteractiveGameState handleExecuteDown() {
      _warGameMoves.executeJoinMove(_logicalUnit, _movementArrow.getPath());
      return endMoveAndGoToStartingState();
   }

   @Override
   public InteractiveGameState handleExecuteUp() {
      return this;
   }

   private InteractiveGameState endMoveAndGoToStartingState() {
      _interactiveWarGame.stopIndicatingPositions();
      _interactiveWarGame.setMovementArrowController(null);
      return new StartingState(_interactiveWarGame, _warGameQueries, _warGameMoves);
   }

   @Override
   public InteractiveGameState handleCancel() {
      return new SelectMovementState(_interactiveWarGame, _warGameQueries, _warGameMoves, _logicalUnit, _movementArrow);
   }

   @Override
   public InteractiveGameState handleDirection(Direction direction) {
      return this;
   }

   @Override
   public void setSprites(Sprites sprites) {
      if (_theJoinMenu == null) {
         _theJoinMenu = new GraphicalMenu(sprites.getMenuCursor());
      }
      _theJoinMenu.addItem(ActionMenuItem.JOIN.getName());

   }

   @Override
   public void draw(GameContainer gc, Font font, int x, int y) {
      _interactiveWarGame.draw(gc, 0, 0);
      _theJoinMenu.draw(gc.getGraphics(), font);
   }
}
