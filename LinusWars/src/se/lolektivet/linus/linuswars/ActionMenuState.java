package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import se.lolektivet.linus.linuswars.graphics.ResourceLoader;
import se.lolektivet.linus.linuswars.logic.Direction;
import se.lolektivet.linus.linuswars.logic.LogicalUnit;
import se.lolektivet.linus.linuswars.logic.LogicalWarGame;
import se.lolektivet.linus.linuswars.logic.Position;

import java.util.Set;

/**
 * Created by Linus on 2014-09-20.
 */
public class ActionMenuState implements InteractiveGameState {
   private final InteractiveWarGame _interactiveWarGame;
   private final LogicalWarGame _logicalWarGame;
   private final LogicalUnit _logicalUnit;
   private final MovementArrow _movementArrow;
   private GraphicalMenu _theActionMenu;
   private Set<LogicalUnit> _attackableUnits;

   public ActionMenuState(
         InteractiveWarGame interactiveWarGame,
         LogicalWarGame logicalWarGame,
         LogicalUnit logicalUnit,
         MovementArrow movementArrow) {
      _interactiveWarGame = interactiveWarGame;
      _logicalWarGame = logicalWarGame;
      _logicalUnit = logicalUnit;
      _movementArrow = movementArrow;
   }

   @Override
   public InteractiveGameState handleExecuteDown() {
      String selectedItemText = _theActionMenu.getTextForSelectedItem();
      ActionMenuItem selectedItem = ActionMenuItem.fromName(selectedItemText);
      switch (selectedItem) {
         case WAIT:
            _logicalWarGame.executeMove(_logicalUnit, _movementArrow.getPath());
            return endMoveAndGoToStartingState();
         case FIRE:
            // TODO: Animate travel.
            return new SelectAttackState(_interactiveWarGame, _logicalWarGame, _logicalUnit, _movementArrow, _attackableUnits);
         case SUPPLY:
            // TODO: Animate supply
            _logicalWarGame.executeSupplyMove(_logicalUnit, _movementArrow.getPath());
            return endMoveAndGoToStartingState();
         case CAPTURE:
            break;
         case UNLOAD:
            // TODO: Extra state for selecting which unit to unload (for lander and cruiser)
            return new SelectUnloadPositionState(_interactiveWarGame, _logicalWarGame, _logicalUnit, _movementArrow, _adjacentVacantPositions);
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
      return new StartingState(_interactiveWarGame, _logicalWarGame);
   }

   @Override
   public InteractiveGameState handleExecuteUp() {
      return this;
   }

   @Override
   public InteractiveGameState handleCancel() {
      return new SelectMovementState(_interactiveWarGame, _logicalWarGame, _logicalUnit, _movementArrow);
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
         Set<LogicalUnit> suppliableUnits = _logicalWarGame.getSuppliableUnitsAfterMove(_logicalUnit, _movementArrow.getPath());
         _attackableUnits = _logicalWarGame.getAttackableUnitsAfterMove(_logicalUnit, _movementArrow.getPath());
         if (isTransportingUnits() && canUnload()) {
            _theActionMenu.addItem(ActionMenuItem.UNLOAD.getName(), null);
         }
         if (!_attackableUnits.isEmpty()) {
            _theActionMenu.addItem(ActionMenuItem.FIRE.getName(), null);
         }
         if (!suppliableUnits.isEmpty()) {
            _theActionMenu.addItem(ActionMenuItem.SUPPLY.getName(), null);
         }
         _theActionMenu.addItem(ActionMenuItem.WAIT.getName(), null);
         // TODO: "if can capture" -> add "Capture"
         // TODO: "if Sub" -> add "Dive" or "Surface"
         // TODO: "if can unload" -> add "Unload"
      }
   }

   private boolean isTransportingUnits() {
      return !_logicalWarGame.getTransportedUnits(_logicalUnit).isEmpty();
   }

   private boolean canUnload() {
      _adjacentVacantPositions = _logicalWarGame.getAdjacentVacantPositionsAfterMove(_logicalUnit, _movementArrow.getPath());
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
