package se.lolektivet.linus.linuswars.graphicalgame;

import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.logic.game.LogicalUnit;
import se.lolektivet.linus.linuswars.logic.game.LogicalWarGame;
import se.lolektivet.linus.linuswars.logic.enums.Faction;

/**
 * Created by Linus on 2014-09-22.
 */
public class GraphicalGamePreDeployer {
   private final GraphicalUnitFactory _graphicalUnitFactory;

   public GraphicalGamePreDeployer() {
      _graphicalUnitFactory = new GraphicalUnitFactory();
   }

   public void init(Sprites sprites) {
      _graphicalUnitFactory.init(sprites);
   }

   public void preDeploy(LogicalWarGame logicalWarGame, GraphicalWarGame graphicalWarGame) {
      for (LogicalUnit logicalUnit : logicalWarGame.getAllUnits()) {
         Faction faction = logicalWarGame.getFactionForUnit(logicalUnit);
         GraphicalUnit graphicalUnit = _graphicalUnitFactory.getGraphicalUnit(faction, logicalUnit.getType());
         graphicalWarGame.addUnit(graphicalUnit, logicalUnit, logicalWarGame.getPositionOfUnit(logicalUnit));
      }
   }
}
