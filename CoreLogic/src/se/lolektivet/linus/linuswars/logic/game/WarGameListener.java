package se.lolektivet.linus.linuswars.logic.game;

/**
* Created by Linus on 2014-11-08.
*/
public interface WarGameListener {
   void unitWasDestroyed(LogicalUnit logicalUnit);
   void unitJoined(LogicalUnit logicalUnit);
   void transportedUnitWasDestroyed(LogicalUnit logicalUnit);
}
