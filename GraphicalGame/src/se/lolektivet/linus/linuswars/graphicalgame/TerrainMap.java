package se.lolektivet.linus.linuswars.graphicalgame;

import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.TerrainTile;

import java.util.HashMap;

/**
 * Created by Linus on 2015-12-18.
 */
public class TerrainMap extends HashMap<Position, TerrainTile> {

   @Override
   public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      for (Entry<Position, TerrainTile> entry : this.entrySet()) {
         stringBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
      }
      return stringBuilder.toString();
   }
}
