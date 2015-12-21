package se.lolektivet.linus.linuswars.logic;

/**
 * Created by Linus on 2015-12-21.
 */
public abstract class WarMapAdapter implements WarMap {

   @Override
   public void create(MapMaker mapMaker) {
      create(mapMaker, new DefaultFactions().getDefaultFactions());
   }
}
