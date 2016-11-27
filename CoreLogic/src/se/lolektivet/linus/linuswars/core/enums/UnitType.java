package se.lolektivet.linus.linuswars.core.enums;

/**
 * Created by Linus on 2014-09-18.
 */
public enum UnitType {
   INFANTRY("Infantry"),
   MECH("Mech"),
   RECON("Recon"),
   TANK("Tank"),
   MD_TANK("MdTank"),
   APC("Apc"),
   ARTILLERY("Artillery"),
   ROCKETS("Rockets"),
   ANTI_AIR("AntiAir"),
   MISSILES("Missiles"),
   B_COPTER("BCopter"),
   T_COPTER("TCopter"),
   FIGHTER("Fighter"),
   BOMBER("Bomber"),
   LANDER("Lander"),
   CRUISER("Cruiser"),
   SUB("Sub"),
   B_SHIP("BShip");

   private final String _name;

   UnitType(String name) {
      _name = name;
   }

   public String getName() {
      return _name;
   }

   public String toString() {
      return getName();
   }
}
