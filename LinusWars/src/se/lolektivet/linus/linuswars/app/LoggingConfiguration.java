package se.lolektivet.linus.linuswars.app;

import java.io.IOException;
import java.util.Date;
import java.util.logging.*;

/**
 * Created by Linus on 2016-11-21.
 */
public class LoggingConfiguration {

   public LoggingConfiguration() {
      Logger lolektivetLogger = Logger.getLogger("se.lolektivet");
      lolektivetLogger.setLevel(Level.ALL);
      lolektivetLogger.addHandler(new ConsoleHandler());

      try {
         FileHandler fileHandler = new FileHandler("linuswars-log.%u.%g.txt", true);
         fileHandler.setFormatter(SIMPLER_FORMATTER);
         lolektivetLogger.addHandler(fileHandler);
      } catch (IOException e) {
         lolektivetLogger.log(Level.WARNING, "Could not create file handler for logging!", e);
      }
   }

   private static final Formatter SIMPLER_FORMATTER = new Formatter() {
      @Override
      public String format(LogRecord record) {
         // TODO: Proper date format.
         return new Date(record.getMillis()).toString() + " - " + record.getLevel() + ": " + record.getMessage() + "\n";
      }
   };
}
