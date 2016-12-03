package se.lolektivet.linus.linuswars.core;

/**
 * Created by Linus on 2016-12-03.
 */
public class IllegalSetupException extends RuntimeException {
   public IllegalSetupException() {}

   public IllegalSetupException(String s) {
      super(s);
   }
}
