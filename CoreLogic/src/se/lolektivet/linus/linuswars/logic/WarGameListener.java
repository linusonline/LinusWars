package se.lolektivet.linus.linuswars.logic;

/**
* Created by Linus on 2014-11-08.
*/
public interface WarGameListener {
   void unitWasDestroyed(LogicalUnit logicalUnit);
   void transportedUnitWasDestroyed(LogicalUnit logicalUnit);
}
