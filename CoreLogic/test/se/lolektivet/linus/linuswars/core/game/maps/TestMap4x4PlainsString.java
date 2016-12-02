package se.lolektivet.linus.linuswars.core.game.maps;

import se.lolektivet.linus.linuswars.core.StringMap;

/**
 * Created by Linus on 2016-12-02.
 */
public class TestMap4x4PlainsString {

   public static final int BLUE_MOON_STARTING_FUNDS = 2000;

   public static StringMap create() {

      String theMap =
            "QbLLL\n" +
            "BbLLL\n" +
            "L LLL\n" +
            "QoLLL\n";

      return new StringMap(2, theMap);
   }
}
