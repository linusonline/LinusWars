package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import se.lolektivet.linus.linuswars.graphics.ResourceLoader;
import se.lolektivet.linus.linuswars.logic.WarGameMoves;
import se.lolektivet.linus.linuswars.logic.WarGameQueries;
import se.lolektivet.linus.linuswars.logic.enums.Direction;
import se.lolektivet.linus.linuswars.logic.LogicalUnit;
import se.lolektivet.linus.linuswars.logic.LogicalWarGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Linus on 2014-09-20.
 */
public class SelectAttackState implements InteractiveGameState {
   private final InteractiveWarGame _interactiveWarGame;
   private WarGameQueries _warGameQueries;
   private WarGameMoves _warGameMoves;
   private final LogicalUnit _logicalUnit;
   private final MovementArrow _movementArrow;
   private final List<LogicalUnit> _attackableUnits;
   private int _currentlySelectedTargetIndex;
   private GraphicalMenu _fireOrNothingMenu;

   public SelectAttackState(InteractiveWarGame interactiveWarGame,
                            WarGameQueries warGameQueries,
                            WarGameMoves warGameMoves,
                            LogicalUnit logicalUnit,
                            MovementArrow movementArrow,
                            Set<LogicalUnit> attackableUnits) {
      _interactiveWarGame = interactiveWarGame;
      _warGameQueries = warGameQueries;
      _warGameMoves = warGameMoves;
      _logicalUnit = logicalUnit;
      _movementArrow = movementArrow;
      _attackableUnits = new ArrayList<LogicalUnit>(attackableUnits);
      _currentlySelectedTargetIndex = 0;
      _interactiveWarGame.showAttackCursorOnUnit(getTargetUnit());
      printAttackInfo();
   }

   private void printAttackInfo() {
      int damage = _warGameQueries.calculateDamageInPercent(_logicalUnit, getTargetUnit());
      System.out.println("Damage: " + damage + "%");
   }

   private LogicalUnit getTargetUnit() {
      return _attackableUnits.get(_currentlySelectedTargetIndex);
   }

   @Override
   public InteractiveGameState handleExecuteDown() {
      LogicalUnit defendingUnit = getTargetUnit();
      _warGameMoves.executeAttackMove(_logicalUnit, _movementArrow.getPath(), defendingUnit);
      _interactiveWarGame.stopIndicatingPositions();
      _interactiveWarGame.hideAttackCursor();
      _interactiveWarGame.setMovementArrowController(null);
      // TODO: Check if game was won!
      return new StartingState(_interactiveWarGame, _warGameQueries, _warGameMoves);
   }

   @Override
   public InteractiveGameState handleExecuteUp() {
      return this;
   }

   @Override
   public InteractiveGameState handleCancel() {
      _interactiveWarGame.hideAttackCursor();
      return new ActionMenuState(_interactiveWarGame, _warGameQueries, _warGameMoves, _logicalUnit, _movementArrow);
   }

   @Override
   public InteractiveGameState handleDirection(Direction direction) {
      int indexStep;
      switch (direction) {
         case LEFT:
         case UP:
            indexStep = -1;
            break;
         case RIGHT:
         case DOWN:
         default:
            indexStep = 1;
      }
      _currentlySelectedTargetIndex += indexStep;
      if (_currentlySelectedTargetIndex < 0) {
         _currentlySelectedTargetIndex += _attackableUnits.size();
      } else if (_currentlySelectedTargetIndex >= _attackableUnits.size()) {
         _currentlySelectedTargetIndex -= _attackableUnits.size();
      }
      _interactiveWarGame.showAttackCursorOnUnit(getTargetUnit());
      printAttackInfo();
      return this;
   }

   @Override
   public void setResourceLoader(ResourceLoader loader) {
      if (_fireOrNothingMenu == null) {
         _fireOrNothingMenu = new GraphicalMenu(loader.getMenuCursorImage());
         _fireOrNothingMenu.addItem(ActionMenuItem.FIRE.getName(), null);
      }
   }

   @Override
   public String toString() {
      return "Select Attack State";
   }

   @Override
   public void draw(Graphics graphics, Font font, int x, int y) {
      _interactiveWarGame.draw(graphics, font, 0, 0);
      _fireOrNothingMenu.draw(graphics, font);
   }
}
