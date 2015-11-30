package se.lolektivet.linus.linuswars;

/**
 * Created by Linus on 2014-09-22.
 */
public enum ActionMenuItem {
   FIRE("Fire"),
   SUPPLY("Supply"),
   CAPTURE("Capture"),
   LOAD("Load"),
   UNLOAD("Unload"),
   JOIN("Join"),
   DIVE("Dive"),
   SURFACE("Surface"),
   WAIT("Wait");

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
