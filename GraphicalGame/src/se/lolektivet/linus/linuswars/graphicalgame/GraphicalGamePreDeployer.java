package se.lolektivet.linus.linuswars.graphicalgame;

import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.logic.GamePredeployer;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;

/**
 * Created by Linus on 2014-09-22.
 */
public class GraphicalGamePreDeployer implements GamePredeployer {
   private final GraphicalWarGame _graphicalWarGame;
   private final GraphicalUnitFactory _graphicalUnitFactory;

   public GraphicalGamePreDeployer(GraphicalWarGame graphicalWarGame) {
      _graphicalWarGame = graphicalWarGame;
      _graphicalUnitFactory = new GraphicalUnitFactory();
   }

   public void init(Sprites sprites) {
      _graphicalUnitFactory.init(sprites);
   }

   @Override
   public void addNewUnit(UnitType type, Faction faction, int x, int y) {
      GraphicalUnit graphicalUnit = _graphicalUnitFactory.getGraphicalUnit(faction, type);
      _graphicalWarGame.addUnit(graphicalUnit, new Position(x, y));
   }

   @Override
   public void addNewUnit(UnitType type, Faction faction, int x, int y, int hpPercent) {
      addNewUnit(type, faction, x, y);
   }
}
