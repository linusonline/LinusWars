package se.lolektivet.linus.linuswars.graphicalgame;

/**
 * Created by Linus on 2014-09-19.
 */
public interface TileView {
   boolean isTileVisible(int tileX, int tileY);
   int tileToPixelX(int tileX);
   int tileToPixelY(int tileY);
}
