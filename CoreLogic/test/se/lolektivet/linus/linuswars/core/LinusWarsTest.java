package se.lolektivet.linus.linuswars.core;

import se.lolektivet.linus.linuswars.app.LoggingConfiguration;

import java.util.logging.Level;

/**
 * Created by Linus on 2016-11-28.
 */
public class LinusWarsTest {
   public LinusWarsTest() {
      new LoggingConfiguration();
      LoggingConfiguration.setGlobalLevel(Level.OFF);
      LoggingConfiguration.logToFile(false);
   }
}
