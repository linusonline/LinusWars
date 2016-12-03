package se.lolektivet.linus.linuswars.core.map;

import se.lolektivet.linus.linuswars.core.enums.Faction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linus on 2015-12-21.
 */
public class DefaultFactions {

   public List<Faction> getDefaultFactions() {
      List<Faction> defaultFactions = new ArrayList<>(5);
      defaultFactions.add(Faction.ORANGE_STAR);
      defaultFactions.add(Faction.BLUE_MOON);
      defaultFactions.add(Faction.GREEN_EARTH);
      defaultFactions.add(Faction.YELLOW_COMET);
      defaultFactions.add(Faction.BLACK_HOLE);
      return defaultFactions;
   }
}
