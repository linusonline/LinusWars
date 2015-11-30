package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.logic.game.LogicalUnit;
import se.lolektivet.linus.linuswars.logic.game.WarGameMoves;
import se.lolektivet.linus.linuswars.logic.game.WarGameQueries;
import se.lolektivet.linus.linuswars.logic.enums.Direction;

/**
 * Created by Linus on 2014-09-29.
 */
public class LoadMenuState implements InteractiveGameState {
   private GraphicalMenu _theLoadMenu;
   private final InteractiveWarGame _interactiveWarGame;
   private final WarGameQueries _warGameQueries;
   private final WarGameMoves _warGameMoves;
   private final LogicalUnit _logicalUnit;
   private final MovementArrow _movementArrow;

   public LoadMenuState(InteractiveWarGame interactiveWarGame,
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
      _warGameMoves.executeLoadMove(_logicalUnit, _movementArrow.getPath());
      _interactiveWarGame.hideGraphicForUnit(_logicalUnit);
      return endMoveAndGoToStartingState();
   }

   @Override
   public InteractiveGameState handleExecuteUp() {
      return this;
   }

   @Override
   public InteractiveGameState handleCancel() {
      return new SelectMovementState(_interactiveWarGame, _warGameQueries, _warGameMoves, _logicalUnit, _movementArrow);
   }

   @Override
   public InteractiveGameState handleDirection(Direction direction) {
      return this;
   }

   private InteractiveGameState endMoveAndGoToStartingState() {
      _interactiveWarGame.stopIndicatingPositions();
      _interactiveWarGame.hideMovementArrow();
      return new StartingState(_interactiveWarGame, _warGameQueries, _warGameMoves);
   }

   @Override
   public void setSprites(Sprites sprites) {
      if (_theLoadMenu == null) {
         _theLoadMenu = new GraphicalMenu(sprites.getMenuCursor());
      }
      _theLoadMenu.addItem(ActionMenuItem.LOAD.getName());
   }

   @Override
   public void draw(GameContainer gc, Font font, int x, int y) {
      _interactiveWarGame.draw(gc, 0, 0);
      _theLoadMenu.draw(gc.getGraphics(), font);
   }
}
