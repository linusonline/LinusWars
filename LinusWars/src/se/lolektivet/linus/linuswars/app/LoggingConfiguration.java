package se.lolektivet.linus.linuswars.app;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Linus on 2016-11-21.
 */
public class LoggingConfiguration {
   public LoggingConfiguration() {
      Logger lolektivetLogger = Logger.getLogger("se.lolektivet");
      lolektivetLogger.setLevel(Level.ALL);
      lolektivetLogger.addHandler(new ConsoleHandler());
   }
}
