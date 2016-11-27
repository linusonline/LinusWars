package se.lolektivet.linus.linuswars.core.game;

import se.lolektivet.linus.linuswars.core.enums.Faction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Linus on 2015-11-26.
 */
class ModuleMoney {
   private final Map<Faction, Integer> _moneyForFactions;

   ModuleMoney() {
      _moneyForFactions = new HashMap<>();
   }

   void init(List<Faction> factions) {
      for (Faction faction : factions) {
         _moneyForFactions.put(faction, 0);
      }
   }

   void addMoneyForFaction(Faction faction, int money) {
      _moneyForFactions.put(faction, _moneyForFactions.get(faction) + money);
   }

   void subtractMoneyForFaction(Faction faction, int money) {
      _moneyForFactions.put(faction, _moneyForFactions.get(faction) - money);
   }

   int getMoneyForFaction(Faction faction) {
      return _moneyForFactions.get(faction);
   }
}
