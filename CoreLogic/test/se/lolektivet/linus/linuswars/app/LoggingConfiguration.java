package se.lolektivet.linus.linuswars.app;

import java.io.IOException;
import java.util.Date;
import java.util.logging.*;

/**
 * Created by Linus on 2016-11-21.
 */
public class LoggingConfiguration {

   private static Logger _lolektivetLogger;
   private static Handler _fileHandler;
   private static boolean _loggingToFile;

   public LoggingConfiguration() {
      _lolektivetLogger = Logger.getLogger("se.lolektivet");
      _lolektivetLogger.setLevel(Level.ALL);
      _lolektivetLogger.addHandler(new ConsoleHandler());

      _loggingToFile = true;
      try {
         _fileHandler = new FileHandler("linuswars-log.%u.%g.txt", true);
         _fileHandler.setFormatter(SIMPLER_FORMATTER);
         _lolektivetLogger.addHandler(_fileHandler);
      } catch (IOException e) {
         _lolektivetLogger.log(Level.WARNING, "Could not create file handler for logging!", e);
      }
   }

   public static void setGlobalLevel(Level level) {
      _lolektivetLogger.setLevel(level);
   }

   public static void logToFile(boolean log) {
      if (_loggingToFile == log) {
         return;
      }
      if (log) {
         _lolektivetLogger.addHandler(_fileHandler);
      } else {
         _lolektivetLogger.removeHandler(_fileHandler);
      }
      _loggingToFile = log;
   }

   private static final Formatter SIMPLER_FORMATTER = new Formatter() {
      @Override
      public String format(LogRecord record) {
         // TODO: Proper date format.
         return new Date(record.getMillis()).toString() + " - " + record.getLevel() + ": " + record.getMessage() + "\n";
      }
   };
}
