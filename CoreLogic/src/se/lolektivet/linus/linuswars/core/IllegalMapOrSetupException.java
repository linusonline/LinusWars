package se.lolektivet.linus.linuswars.core;

/**
 * Created by Linus on 2015-11-23.
 */
public class IllegalMapOrSetupException extends RuntimeException {
   public IllegalMapOrSetupException() {}

   public IllegalMapOrSetupException(String s) {
      super(s);
   }
}
