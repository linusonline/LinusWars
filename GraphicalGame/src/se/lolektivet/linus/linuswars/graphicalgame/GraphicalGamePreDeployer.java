package se.lolektivet.linus.linuswars.graphicalgame;

import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.logic.GamePredeployer;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;
import se.lolektivet.linus.linuswars.logic.game.LogicalUnit;
import se.lolektivet.linus.linuswars.logic.game.LogicalWarGame;
import se.lolektivet.linus.linuswars.logic.enums.Faction;

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
   public void addNewUnit(UnitType type, Position position, Faction faction, int hpPercent) {
      addNewUnit(type, position, faction);
   }

   @Override
   public void addNewUnit(UnitType type, Position position, Faction faction) {
      GraphicalUnit graphicalUnit = _graphicalUnitFactory.getGraphicalUnit(faction, type);
      _graphicalWarGame.addUnit(graphicalUnit, position);
   }
}
