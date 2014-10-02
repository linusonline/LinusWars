package se.lolektivet.linus.linuswars;

/**
 * Created by Linus on 2014-09-22.
 */
public enum QuickMenuItem {
   END_TURN("End turn"),
   NOTHING("Nothing");

   private final String _name;

   QuickMenuItem(String name) {
      _name = name;
   }

   public String getName() {
      return _name;
   }

   public static QuickMenuItem fromName(String name) {
      for (QuickMenuItem menuItem : QuickMenuItem.values()) {
         if (menuItem.getName().equals(name)) {
            return menuItem;
         }
      }
      throw new IllegalArgumentException("No QuickMenuItem with that name!");
   }
}
