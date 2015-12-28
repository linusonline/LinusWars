package se.lolektivet.linus.linuswars.logic;

import se.lolektivet.linus.linuswars.logic.enums.Faction;

import java.util.List;

/**
 * Created by Linus on 2015-12-18.
 */
public interface WarMap {
   int getNrOfFactions();
   void create(MapMaker mapMaker);
   void create(MapMaker mapMaker, List<Faction> factions);
}
