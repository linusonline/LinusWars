package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.enums.Faction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Linus on 2015-11-26.
 */
class ModuleTurnOrder {
   private final List<Faction> _factionsInTurnOrder;
   private Faction _currentlyActiveFaction;

   ModuleTurnOrder(List<Faction> factionsInTurnOrder) {
      _factionsInTurnOrder = new ArrayList<>(factionsInTurnOrder);
      _currentlyActiveFaction = _factionsInTurnOrder.get(0);
   }

   boolean factionIsInGame(Faction faction) {
      return _factionsInTurnOrder.contains(faction);
   }

   int numberOfFactions() {
      return _factionsInTurnOrder.size();
   }

   Faction currentlyActiveFaction() {
      return _currentlyActiveFaction;
   }

   void advanceToNextFactionInTurn() {
      _currentlyActiveFaction = getNextFactionInTurn();
   }

   List<Faction> getFactionsInTurnOrder() {
      return new ArrayList<>(_factionsInTurnOrder);
   }

   private Faction getNextFactionInTurn() {
      for (Iterator<Faction> iterator = _factionsInTurnOrder.iterator(); iterator.hasNext();) {
         if (iterator.next() == _currentlyActiveFaction) {
            if (iterator.hasNext()) {
               return iterator.next();
            } else {
               return _factionsInTurnOrder.get(0);
            }
         }
      }
//      for (int i = 0; i < _factionsInTurnOrder.size(); i++) {
//         if (_factionsInTurnOrder.get(i).equals(_currentlyActiveFaction)) {
//            int indexOfNextFaction = i + 1;
//            if (indexOfNextFaction == _factionsInTurnOrder.size()) {
//               indexOfNextFaction = 0;
//            }
//            return _factionsInTurnOrder.get(indexOfNextFaction);
//         }
//      }
      throw new RuntimeException("Not gonna happen");
   }
}
