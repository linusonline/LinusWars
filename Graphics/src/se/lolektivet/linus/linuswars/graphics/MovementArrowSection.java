package se.lolektivet.linus.linuswars.graphics;

/**
 * Created by Linus on 2015-11-22.
 */
public enum MovementArrowSection {
   HORIZONTAL(9),
   VERTICAL(0),
   BEND_S_TO_E(1),
   BEND_S_TO_W(2),
   BEND_N_TO_E(3),
   BEND_N_TO_W(4),
   END_N(7),
   END_S(5),
   END_E(8),
   END_W(6);

   private int _sheetIndex;

   MovementArrowSection(int sheetIndex) {
      _sheetIndex = sheetIndex;
   }

   public int getSheetIndex() {
      return _sheetIndex;
   }
}
