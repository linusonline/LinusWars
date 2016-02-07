package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.game.LogicalUnit;
import se.lolektivet.linus.linuswars.logic.game.WarGameMoves;
import se.lolektivet.linus.linuswars.logic.game.WarGameQueries;
import se.lolektivet.linus.linuswars.logic.enums.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Linus on 2014-09-20.
 */
public class StateSelectAttack implements GameState {

   private final GameStateContext _context;

   private final LogicalUnit _logicalUnit;
   private final MovementArrow _movementArrow;
   private final List<LogicalUnit> _attackableUnits;
   private final GameState _previousState;
   private int _currentlySelectedTargetIndex;
   private GraphicalMenu _fireOrNothingMenu;
   private DamageCounter _damageCounter;

   public StateSelectAttack(GameStateContext context,
                            LogicalUnit logicalUnit,
                            MovementArrow movementArrow,
                            Set<LogicalUnit> attackableUnits,
                            GameState previousState) {
      _context = context;
      _logicalUnit = logicalUnit;
      _movementArrow = movementArrow;
      _attackableUnits = new ArrayList<>(attackableUnits);
      _previousState = previousState;
      _currentlySelectedTargetIndex = 0;
      _context.interactiveWarGame.showAttackCursorOnUnit(getTargetUnit());
      printAttackInfo();
   }

   private void printAttackInfo() {
      int damage = _context.warGameQueries.calculateDamageInPercent(_logicalUnit, getTargetUnit());
      System.out.println("Damage: " + damage + "%");
   }

   private LogicalUnit getTargetUnit() {
      return _attackableUnits.get(_currentlySelectedTargetIndex);
   }

   @Override
   public GameState handleExecuteDown() {
      LogicalUnit defendingUnit = getTargetUnit();
      _context.warGameMoves.executeAttackMove(_logicalUnit, _movementArrow.getPath(), defendingUnit);
      _context.interactiveWarGame.stopIndicatingPositions();
      _context.interactiveWarGame.hideAttackCursor();
      _context.interactiveWarGame.hideMovementArrow();
      // TODO: Check if game was won!
      return new StateStarting(_context);
   }

   @Override
   public GameState handleExecuteUp() {
      return this;
   }

   @Override
   public GameState handleCancel() {
      _context.interactiveWarGame.hideAttackCursor();
      return _previousState;
   }

   @Override
   public GameState handleDirection(Direction direction) {
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
      _context.interactiveWarGame.showAttackCursorOnUnit(getTargetUnit());
      printAttackInfo();
      return this;
   }

   @Override
   public GameState update() {
      return this;
   }

   @Override
   public void init(Sprites sprites) {
      if (_fireOrNothingMenu == null) {
         _fireOrNothingMenu = new GraphicalMenu(sprites.getMenuCursor());
         _fireOrNothingMenu.addItem(ActionMenuItem.FIRE.getName());
      }
      if (_damageCounter == null) {
         _damageCounter = new DamageCounter();
         _damageCounter.init(sprites);
      }
   }

   @Override
   public String toString() {
      return "Select Attack State";
   }

   @Override
   public void draw(GameContainer gc, Font font, int x, int y) {
      _context.interactiveWarGame.draw(gc, 0, 0);
      _fireOrNothingMenu.draw(gc.getGraphics(), font);
      // Possible optimization to do here.
      Position position = _context.warGameQueries.getPositionOfUnit(getTargetUnit());
      int damage = _context.warGameQueries.calculateDamageInPercent(_logicalUnit, getTargetUnit());
      _damageCounter.draw(position.getX(), position.getY(), _context.interactiveWarGame.getTileView(), damage);
   }
}
