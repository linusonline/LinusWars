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
   private final InteractiveWarGame _interactiveWarGame;
   private WarGameQueries _warGameQueries;
   private WarGameMoves _warGameMoves;
   private final LogicalUnit _logicalUnit;
   private final MovementArrow _movementArrow;
   private final List<LogicalUnit> _attackableUnits;
   private final GameState _previousState;
   private int _currentlySelectedTargetIndex;
   private GraphicalMenu _fireOrNothingMenu;
   private DamageCounter _damageCounter;

   public StateSelectAttack(InteractiveWarGame interactiveWarGame,
                            WarGameQueries warGameQueries,
                            WarGameMoves warGameMoves,
                            LogicalUnit logicalUnit,
                            MovementArrow movementArrow,
                            Set<LogicalUnit> attackableUnits,
                            GameState previousState) {
      _interactiveWarGame = interactiveWarGame;
      _warGameQueries = warGameQueries;
      _warGameMoves = warGameMoves;
      _logicalUnit = logicalUnit;
      _movementArrow = movementArrow;
      _attackableUnits = new ArrayList<>(attackableUnits);
      _previousState = previousState;
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
   public GameState handleExecuteDown() {
      LogicalUnit defendingUnit = getTargetUnit();
      _warGameMoves.executeAttackMove(_logicalUnit, _movementArrow.getPath(), defendingUnit);
      _interactiveWarGame.stopIndicatingPositions();
      _interactiveWarGame.hideAttackCursor();
      _interactiveWarGame.hideMovementArrow();
      // TODO: Check if game was won!
      return new StateStarting(_interactiveWarGame, _warGameQueries, _warGameMoves);
   }

   @Override
   public GameState handleExecuteUp() {
      return this;
   }

   @Override
   public GameState handleCancel() {
      _interactiveWarGame.hideAttackCursor();
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
      _interactiveWarGame.showAttackCursorOnUnit(getTargetUnit());
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
      _interactiveWarGame.draw(gc, 0, 0);
      _fireOrNothingMenu.draw(gc.getGraphics(), font);
      // Possible optimization to do here.
      Position position = _warGameQueries.getPositionOfUnit(getTargetUnit());
      int damage = _warGameQueries.calculateDamageInPercent(_logicalUnit, getTargetUnit());
      _damageCounter.draw(position.getX(), position.getY(), _interactiveWarGame.getTileView(), damage);
   }
}
