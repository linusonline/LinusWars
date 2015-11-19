package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import se.lolektivet.linus.linuswars.graphics.ResourceLoader;
import se.lolektivet.linus.linuswars.logic.*;
import se.lolektivet.linus.linuswars.logic.enums.Direction;

import java.util.Set;

/**
 * Created by Linus on 2014-09-20.
 */
public class ActionMenuState implements InteractiveGameState {
   private final InteractiveWarGame _interactiveWarGame;
   private final WarGameQueries _warGameQueries;
   private final WarGameMoves _warGameMoves;
   private final LogicalUnit _logicalUnit;
   private final MovementArrow _movementArrow;
   private GraphicalMenu _theActionMenu;
   private Set<LogicalUnit> _attackableUnits;

   public ActionMenuState(
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
      String selectedItemText = _theActionMenu.getTextForSelectedItem();
      ActionMenuItem selectedItem = ActionMenuItem.fromName(selectedItemText);
      switch (selectedItem) {
         case WAIT:
            _warGameMoves.executeMove(_logicalUnit, _movementArrow.getPath());
            return endMoveAndGoToStartingState();
         case FIRE:
            // TODO: Animate travel.
            return new SelectAttackState(_interactiveWarGame, _warGameQueries, _warGameMoves, _logicalUnit, _movementArrow, _attackableUnits);
         case SUPPLY:
            // TODO: Animate supply
            _warGameMoves.executeSupplyMove(_logicalUnit, _movementArrow.getPath());
            return endMoveAndGoToStartingState();
         case CAPTURE:
            break;
         case UNLOAD:
            // TODO: Extra state for selecting which unit to unload (for lander and cruiser)
            return new SelectUnloadPositionState(_interactiveWarGame, _warGameQueries, _warGameMoves, _logicalUnit, _movementArrow, _adjacentVacantPositions);
         case DIVE:
            break;
         case SURFACE:
            break;
         default:
      }
      return this;
   }

   private InteractiveGameState endMoveAndGoToStartingState() {
      _interactiveWarGame.stopIndicatingPositions();
      _interactiveWarGame.setMovementArrowController(null);
      return new StartingState(_interactiveWarGame, _warGameQueries, _warGameMoves);
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
      _theActionMenu.moveCursor(direction);
      return this;
   }

   private Set<Position> _adjacentVacantPositions;

   @Override
   public void setResourceLoader(ResourceLoader loader) {
      if (_theActionMenu == null) {
         _theActionMenu = new GraphicalMenu(loader.getMenuCursorImage());
         Set<LogicalUnit> suppliableUnits = _warGameQueries.getSuppliableUnitsAfterMove(_logicalUnit, _movementArrow.getPath());
         _attackableUnits = _warGameQueries.getAttackableUnitsAfterMove(_logicalUnit, _movementArrow.getPath());
         if (isTransportingUnits() && canUnload()) {
            _theActionMenu.addItem(ActionMenuItem.UNLOAD.getName());
         }
         if (!_attackableUnits.isEmpty()) {
            _theActionMenu.addItem(ActionMenuItem.FIRE.getName());
         }
         if (!suppliableUnits.isEmpty()) {
            _theActionMenu.addItem(ActionMenuItem.SUPPLY.getName());
         }
         _theActionMenu.addItem(ActionMenuItem.WAIT.getName());
         // TODO: "if can capture" -> add "Capture"
         // TODO: "if Sub" -> add "Dive" or "Surface"
         // TODO: "if can unload" -> add "Unload"
      }
   }

   private boolean isTransportingUnits() {
      return !_warGameQueries.getTransportedUnits(_logicalUnit).isEmpty();
   }

   private boolean canUnload() {
      _adjacentVacantPositions = _warGameQueries.getAdjacentVacantPositionsAfterMove(_logicalUnit, _movementArrow.getPath());
      return !_adjacentVacantPositions.isEmpty();
   }

   @Override
   public void draw(Graphics graphics, Font font, int x, int y) {
      _interactiveWarGame.draw(graphics, font, 0, 0);
      _theActionMenu.draw(graphics, font);
   }

   @Override
   public String toString() {
      return "Action Menu";
   }
}
