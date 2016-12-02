package se.lolektivet.linus.linuswars.core;

import se.lolektivet.linus.linuswars.core.enums.Faction;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Linus on 2016-12-02.
 */
public class StringMap extends WarMapAdapter {

   private final List<String> _rows;
   private final int _nrOfFactions;

   public StringMap(int nrOfFactions, String fullMap) {
      _rows = Arrays.asList(fullMap.split("\n"));
      _nrOfFactions = nrOfFactions;
   }

   @Override
   public int getNrOfFactions() {
      return _nrOfFactions;
   }

   @Override
   public void create(MapMaker mapMaker, List<Faction> factions) {
      StringMapMaker stringMapMaker = new StringMapMaker(mapMaker, factions);
      for (String row : _rows) {
         stringMapMaker.readRow(row);
      }
      stringMapMaker.finish();
   }
}
