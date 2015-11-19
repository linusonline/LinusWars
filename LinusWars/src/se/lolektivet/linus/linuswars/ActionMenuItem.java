package se.lolektivet.linus.linuswars;

/**
 * Created by Linus on 2014-09-22.
 */
public enum ActionMenuItem {
   WAIT("Wait"),
   FIRE("Fire"),
   SUPPLY("Supply"),
   CAPTURE("Capture"),
   LOAD("Load"),
   JOIN("Join"),
   UNLOAD("Unload"),
   DIVE("Dive"),
   SURFACE("Surface");

   private final String _name;

   ActionMenuItem(String name) {
      _name = name;
   }

   public String getName() {
      return _name;
   }

   public static ActionMenuItem fromName(String name) {
      for (ActionMenuItem menuItem : ActionMenuItem.values()) {
         if (menuItem.getName().equals(name)) {
            return menuItem;
         }
      }
      throw new IllegalArgumentException("No ActionMenuItem with that name!");
   }
}
