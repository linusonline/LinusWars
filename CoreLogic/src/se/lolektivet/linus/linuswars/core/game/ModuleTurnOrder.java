package se.lolektivet.linus.linuswars.core.game;

import se.lolektivet.linus.linuswars.core.enums.Faction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Linus on 2015-11-26.
 */
class ModuleTurnOrder {
   private final List<Faction> _factionsInTurnOrder;
   private Faction _currentlyActiveFaction;
   private int _dayNumber;

   ModuleTurnOrder(List<Faction> factionsInTurnOrder) {
      _factionsInTurnOrder = new ArrayList<>(factionsInTurnOrder);
      _currentlyActiveFaction = _factionsInTurnOrder.get(0);
      _dayNumber = 1;
   }

   boolean factionIsInGame(Faction faction) {
      return _factionsInTurnOrder.contains(faction);
   }

   int numberOfFactions() {
      return _factionsInTurnOrder.size();
   }

   int getDayNumber() {
      return _dayNumber;
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
               _dayNumber++;
               return _factionsInTurnOrder.get(0);
            }
         }
      }
      throw new RuntimeException("Not gonna happen");
   }
}
