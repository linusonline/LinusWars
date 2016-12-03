package se.lolektivet.linus.linuswars.core.map;

import se.lolektivet.linus.linuswars.core.enums.Faction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Linus on 2016-12-01.
 */
public class MapFile implements WarMap {

   public static class MapFileException extends RuntimeException {}

   private static final Logger _logger = Logger.getLogger(MapFile.class.getName());

   private static final String NO_FILE = "<No file>";
   private final String _fileName;
   private boolean _headerRead = false;
   private boolean _fileRead = false;
   private int _nrFactions = 0;
   private List<String> _rowsInFile = new ArrayList<>();

   public MapFile() {
      _fileName = NO_FILE;
   }

   public MapFile(String fileName) {
      _fileName = fileName;
   }

   public void readFileFully() throws IOException {
      if (NO_FILE.equals(_fileName)) {
         throw new MapFileException();
      }
      try (BufferedReader bufferedReader = new BufferedReader(new FileReader(_fileName))) {
         readBufferFully(bufferedReader);
      }
   }

   public void readBufferFully(BufferedReader bufferedReader) throws IOException {
      String line = bufferedReader.readLine();
      while (line != null) {
         if (!line.isEmpty() && !line.startsWith("#")) {
            if (!_headerRead) {
               readHeader(line);
            } else {
               _rowsInFile.add(line);
            }
         }
         line = bufferedReader.readLine();
      }
      _fileRead = true;
   }

   private void readHeader(String line) throws IOException {
      String headerStart = "LINUSMAP:";
      if (!line.startsWith(headerStart)) {
         throw new IOException(_fileName + " is not a valid LinusWars map file!");
      }
      _nrFactions = Integer.valueOf(line.substring(headerStart.length()));
      _headerRead = true;
   }

   private void throwIfFileNotRead() {
      if (!_fileRead) {
         throw new MapFileException();
      }
   }

   @Override
   public int getNrOfFactions() {
      throwIfFileNotRead();
      return _nrFactions;
   }

   @Override
   public void create(MapMaker mapMaker) {
      throwIfFileNotRead();
      readWithMapMaker(new StringMapMaker(mapMaker));
   }

   @Override
   public void create(MapMaker mapMaker, List<Faction> factions) {
      throwIfFileNotRead();
      readWithMapMaker(new StringMapMaker(mapMaker, factions));
   }

   private void readWithMapMaker(StringMapMaker stringMapMaker) {
      for (String row : _rowsInFile) {
         stringMapMaker.readRow(row);
      }
      stringMapMaker.finish();
   }

}
