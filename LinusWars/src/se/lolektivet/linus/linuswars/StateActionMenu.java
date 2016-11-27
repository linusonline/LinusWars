package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.core.enums.Direction;
import se.lolektivet.linus.linuswars.core.game.LogicalUnit;

/**
 * Created by Linus on 2014-09-20.
 */
public class StateActionMenu implements GameState {

   private final GameStateContext _context;

   private final LogicalUnit _logicalUnit;
   private final MovementArrow _movementArrow;
   private final MoveAnalyzer _moveAnalyzer;
   private GraphicalMenu<ActionMenuItem> _theActionMenu;

   public StateActionMenu(GameStateContext context,
                          LogicalUnit logicalUnit,
                          MovementArrow movementArrow,
                          MoveAnalyzer moveAnalyzer) {
      _context = context;
      _logicalUnit = logicalUnit;
      _movementArrow = movementArrow;
      _moveAnalyzer = moveAnalyzer;
   }

   @Override
   public GameState handleExecuteDown() {
      ActionMenuItem selectedItem = _theActionMenu.getObjectForSelectedItem();
      switch (selectedItem) {
         case FIRE:
            // TODO: Animate travel.
            return new StateSelectAttack(_context, _logicalUnit, _movementArrow, _moveAnalyzer.getAttackableUnits(), this);
         case SUPPLY:
            // TODO: Animate supply
            _context.warGameMoves.executeSupplyMove(_logicalUnit, _movementArrow.getPath());
            return endMoveAndGoToStartingState();
         case CAPTURE:
            _context.warGameMoves.executeCaptureMove(_logicalUnit, _movementArrow.getPath());
            return endMoveAndGoToStartingState();
         case LOAD:
            _context.warGameMoves.executeLoadMove(_logicalUnit, _movementArrow.getPath());
            _context.interactiveWarGame.hideGraphicForUnit(_logicalUnit);
            return endMoveAndGoToStartingState();
         case UNLOAD:
            // TODO: Extra state for selecting which unit to unload (for lander and cruiser)
            return new StateSelectUnloadPosition(_context, _logicalUnit, _movementArrow, _moveAnalyzer.getAdjacentVacantPositions(), this);
         case JOIN:
            _context.warGameMoves.executeJoinMove(_logicalUnit, _movementArrow.getPath());
            return endMoveAndGoToStartingState();
         case DIVE:
            break;
         case SURFACE:
            break;
         case WAIT:
            _context.warGameMoves.executeMove(_logicalUnit, _movementArrow.getPath());
            return endMoveAndGoToStartingState();
         default:
      }
      return this;
   }

   private GameState endMoveAndGoToStartingState() {
      _context.interactiveWarGame.stopIndicatingPositions();
      _context.interactiveWarGame.hideMovementArrow();
      return new StateStarting(_context);
   }

   @Override
   public GameState handleExecuteUp() {
      return this;
   }

   @Override
   public GameState handleCancel() {
      return new StateSelectMove(_context, _logicalUnit, _movementArrow);
   }

   @Override
   public GameState handleDirection(Direction direction) {
      _theActionMenu.moveCursor(direction);
      return this;
   }

   @Override
   public GameState update() {
      return this;
   }

   @Override
   public void init(Sprites sprites) {
      if (_theActionMenu == null) {
         _theActionMenu = new GraphicalMenu<>(sprites.getMenuCursor());
         // Note: The first three moves are logically mutually exclusive.
         if (_moveAnalyzer.canDoCapture()) {
            addMenuItem(ActionMenuItem.CAPTURE);
         } else if (_moveAnalyzer.canDoJoin()) {
            addMenuItem(ActionMenuItem.JOIN);
         } else if (_moveAnalyzer.canDoLoad()) {
            addMenuItem(ActionMenuItem.LOAD);
         }
         if (_moveAnalyzer.canDoUnload()) {
            addMenuItem(ActionMenuItem.UNLOAD);
         }
         if (_moveAnalyzer.canDoSupply()) {
            addMenuItem(ActionMenuItem.SUPPLY);
         }
         if (_moveAnalyzer.canDoAttack()) {
            addMenuItem(ActionMenuItem.FIRE);
         }
         if (_moveAnalyzer.canDoWait()) {
            addMenuItem(ActionMenuItem.WAIT);
         }
         // TODO: "if Sub" -> add "Dive" or "Surface"
      }
   }

   private void addMenuItem(ActionMenuItem menuItem) {
      _theActionMenu.addItem(menuItem.getName(), menuItem);
   }

   @Override
   public void draw(GameContainer gc, Font font, int x, int y) {
      _context.interactiveWarGame.draw(gc, 0, 0);
      _theActionMenu.draw(gc.getGraphics(), font);
   }

   @Override
   public String toString() {
      return "Action Menu";
   }
}
