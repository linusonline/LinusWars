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
      this(true, Level.ALL);
   }

   public LoggingConfiguration(boolean logToFile, Level logLevel) {
      _lolektivetLogger = Logger.getLogger("se.lolektivet");
      _lolektivetLogger.setLevel(logLevel);
      ConsoleHandler consoleHandler = new ConsoleHandler();
      consoleHandler.setFormatter(SIMPLER_FORMATTER);
      _lolektivetLogger.addHandler(consoleHandler);

      _loggingToFile = logToFile;
      if (_loggingToFile) {
         setFileHandler();
      }
   }

   private void setFileHandler() {
      if (_fileHandler == null) {
         try {
            _fileHandler = new FileHandler("log/linuswars-log.%u.%g.txt", true);
            _fileHandler.setFormatter(SIMPLER_FORMATTER);
         } catch (IOException e) {
            _lolektivetLogger.log(Level.WARNING, "Could not create file handler for logging!", e);
         }
      }
      _lolektivetLogger.addHandler(_fileHandler);
   }

   private void unsetFileHandler() {
      _lolektivetLogger.removeHandler(_fileHandler);
   }

   private static final Formatter SIMPLER_FORMATTER = new Formatter() {
      @Override
      public String format(LogRecord record) {
         // TODO: Proper date format.
         return new Date(record.getMillis()).toString() + " - " + record.getLevel() + ": " + record.getMessage() + "\n";
      }
   };
}
