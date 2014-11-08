package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import se.lolektivet.linus.linuswars.graphics.ResourceLoader;
import se.lolektivet.linus.linuswars.logic.LogicalUnit;
import se.lolektivet.linus.linuswars.logic.WarGameMoves;
import se.lolektivet.linus.linuswars.logic.WarGameQueries;
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
      _interactiveWarGame.setMovementArrowController(null);
      return new StartingState(_interactiveWarGame, _warGameQueries, _warGameMoves);
   }

   @Override
   public void setResourceLoader(ResourceLoader loader) {
      if (_theLoadMenu == null) {
         _theLoadMenu = new GraphicalMenu(loader.getMenuCursorImage());
      }
      _theLoadMenu.addItem(ActionMenuItem.LOAD.getName(), null);
   }

   @Override
   public void draw(Graphics g, Font font, int x, int y) {
      _interactiveWarGame.draw(g, font, 0, 0);
      _theLoadMenu.draw(g, font);
   }
}
