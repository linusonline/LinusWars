package se.lolektivet.linus.linuswars.logic;

/**
 * Created by Linus on 2014-09-19.
 */
public enum Faction {
   ORANGE_STAR("Orange"),
   BLUE_MOON("Blue"),
   GREEN_EARTH("Green"),
   YELLOW_COMET("Yellow"),
   BLACK_HOLE("Black"),
   NEUTRAL("Neutral");

   private final String _name;

   Faction(String name) {
      _name = name;
   }

   public String getName() {
      return _name;
   }
}
