package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.core.enums.Direction;
import se.lolektivet.linus.linuswars.core.enums.UnitType;
import se.lolektivet.linus.linuswars.core.game.Building;

import java.util.List;

/**
 * Created by Linus on 2016-01-29.
 */
public class StateDeployMenu implements GameState {
   private final GameStateContext _context;
   private final GameState _previousState;
   private final Building _deployingBase;
   private GraphicalMenu<UnitType> _theMenu;

   public StateDeployMenu(GameStateContext context, GameState previousState, Building base) {
      _context = context;
      _previousState = previousState;
      _deployingBase = base;
   }

   @Override
   public GameState handleExecuteDown() {
      if (_theMenu.hasObjectForSelectedItem()) {
         int money = _context.warGameQueries.getMoneyForFaction(_context.warGameQueries.getCurrentlyActiveFaction());
         UnitType unitType = _theMenu.getObjectForSelectedItem();
         if (_context.warGameQueries.getCostForNewUnit(unitType) > money) {
            return this;
         }
         _context.warGameMoves.executeDeployMove(_deployingBase.getPosition(), unitType);
      }
      return _previousState;
   }

   @Override
   public GameState handleExecuteUp() {
      return this;
   }

   @Override
   public GameState handleCancel() {
      return _previousState;
   }

   @Override
   public GameState handleDirection(Direction direction) {
      _theMenu.moveCursor(direction);
      return this;
   }

   @Override
   public GameState update() {
      return this;
   }

   @Override
   public void init(Sprites sprites) {
      if (_theMenu == null) {
         List<UnitType> unitTypes = _context.warGameQueries.getTypesDeployableFromBuilding(_deployingBase.getBuildingType());
         _theMenu = new GraphicalMenu<>(sprites.getMenuCursor());
         for (UnitType unitType : unitTypes) {
            Image icon = sprites.getUnitSprite(_context.warGameQueries.getCurrentlyActiveFaction(), unitType).getUnitSprite(Direction.RIGHT, false).getCurrentFrame();
            _theMenu.addItem(unitType.getName(), icon, unitType);
         }
         _theMenu.addItem("Cancel");
      }
   }

   @Override
   public void draw(GameContainer gc, Font font, int x, int y) {
      _context.interactiveWarGame.draw(gc, 0, 0);
      if (_theMenu != null) {
         _theMenu.draw(gc.getGraphics(), font);
      }
   }
}
