package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.Direction;
import se.lolektivet.linus.linuswars.logic.game.LogicalUnit;
import se.lolektivet.linus.linuswars.logic.game.WarGameMoves;
import se.lolektivet.linus.linuswars.logic.game.WarGameQueries;

import java.util.Set;

/**
 * Created by Linus on 2014-09-20.
 */
public class StateActionMenu implements InteractiveGameState {
   private final InteractiveWarGame _interactiveWarGame;
   private final WarGameQueries _warGameQueries;
   private final WarGameMoves _warGameMoves;
   private final LogicalUnit _logicalUnit;
   private final MovementArrow _movementArrow;
   private final MoveAnalyzer _moveAnalyzer;
   private GraphicalMenu _theActionMenu;

   public StateActionMenu(
         InteractiveWarGame interactiveWarGame,
         WarGameQueries warGameQueries,
         WarGameMoves warGameMoves,
         LogicalUnit logicalUnit,
         MovementArrow movementArrow,
         MoveAnalyzer moveAnalyzer) {
      _interactiveWarGame = interactiveWarGame;
      _warGameQueries = warGameQueries;
      _warGameMoves = warGameMoves;
      _logicalUnit = logicalUnit;
      _movementArrow = movementArrow;
      _moveAnalyzer = moveAnalyzer;
   }

   @Override
   public InteractiveGameState handleExecuteDown() {
      String selectedItemText = _theActionMenu.getTextForSelectedItem();
      ActionMenuItem selectedItem = ActionMenuItem.fromName(selectedItemText);
      switch (selectedItem) {
         case FIRE:
            // TODO: Animate travel.
            return new StateSelectAttack(_interactiveWarGame, _warGameQueries, _warGameMoves, _logicalUnit, _movementArrow, _moveAnalyzer.getAttackableUnits(), this);
         case SUPPLY:
            // TODO: Animate supply
            _warGameMoves.executeSupplyMove(_logicalUnit, _movementArrow.getPath());
            return endMoveAndGoToStartingState();
         case CAPTURE:
            _warGameMoves.executeCaptureMove(_logicalUnit, _movementArrow.getPath());
            return endMoveAndGoToStartingState();
         case LOAD:
            _warGameMoves.executeLoadMove(_logicalUnit, _movementArrow.getPath());
            _interactiveWarGame.hideGraphicForUnit(_logicalUnit);
            return endMoveAndGoToStartingState();
         case UNLOAD:
            // TODO: Extra state for selecting which unit to unload (for lander and cruiser)
            return new StateSelectUnloadPosition(_interactiveWarGame, _warGameQueries, _warGameMoves, _logicalUnit, _movementArrow, _moveAnalyzer.getAdjacentVacantPositions(), this);
         case JOIN:
            _warGameMoves.executeJoinMove(_logicalUnit, _movementArrow.getPath());
            return endMoveAndGoToStartingState();
         case DIVE:
            break;
         case SURFACE:
            break;
         case WAIT:
            _warGameMoves.executeMove(_logicalUnit, _movementArrow.getPath());
            return endMoveAndGoToStartingState();
         default:
      }
      return this;
   }

   private InteractiveGameState endMoveAndGoToStartingState() {
      _interactiveWarGame.stopIndicatingPositions();
      _interactiveWarGame.hideMovementArrow();
      return new StateStarting(_interactiveWarGame, _warGameQueries, _warGameMoves);
   }

   @Override
   public InteractiveGameState handleExecuteUp() {
      return this;
   }

   @Override
   public InteractiveGameState handleCancel() {
      return new StateSelectMove(_interactiveWarGame, _warGameQueries, _warGameMoves, _logicalUnit, _movementArrow);
   }

   @Override
   public InteractiveGameState handleDirection(Direction direction) {
      _theActionMenu.moveCursor(direction);
      return this;
   }

   @Override
   public InteractiveGameState update() {
      return this;
   }

   @Override
   public void init(Sprites sprites) {
      if (_theActionMenu == null) {
         _theActionMenu = new GraphicalMenu(sprites.getMenuCursor());
         // Note: The first three moves are logically mutually exclusive.
         if (_moveAnalyzer.canDoCapture()) {
            _theActionMenu.addItem(ActionMenuItem.CAPTURE.getName());
         } else if (_moveAnalyzer.canDoJoin()) {
            _theActionMenu.addItem(ActionMenuItem.JOIN.getName());
         } else if (_moveAnalyzer.canDoLoad()) {
            _theActionMenu.addItem(ActionMenuItem.LOAD.getName());
         }
         if (_moveAnalyzer.canDoUnload()) {
            _theActionMenu.addItem(ActionMenuItem.UNLOAD.getName());
         }
         if (_moveAnalyzer.canDoSupply()) {
            _theActionMenu.addItem(ActionMenuItem.SUPPLY.getName());
         }
         if (_moveAnalyzer.canDoAttack()) {
            _theActionMenu.addItem(ActionMenuItem.FIRE.getName());
         }
         if (_moveAnalyzer.canDoWait()) {
            _theActionMenu.addItem(ActionMenuItem.WAIT.getName());
         }
         // TODO: "if Sub" -> add "Dive" or "Surface"
      }
   }

   @Override
   public void draw(GameContainer gc, Font font, int x, int y) {
      _interactiveWarGame.draw(gc, 0, 0);
      _theActionMenu.draw(gc.getGraphics(), font);
   }

   @Override
   public String toString() {
      return "Action Menu";
   }
}
