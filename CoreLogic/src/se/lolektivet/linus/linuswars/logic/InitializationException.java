package se.lolektivet.linus.linuswars.logic;

/**
 * Created by Linus on 2015-11-23.
 */
public class InitializationException extends RuntimeException {
   public InitializationException() {}

   public InitializationException(String s) {
      super(s);
   }
}
