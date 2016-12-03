package se.lolektivet.linus.linuswars.core;

/**
 * Created by Linus on 2015-11-23.
 */
public class IllegalMapException extends RuntimeException {
   public IllegalMapException() {}

   public IllegalMapException(String s) {
      super(s);
   }
}
