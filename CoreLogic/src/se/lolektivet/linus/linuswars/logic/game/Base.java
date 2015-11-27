package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.LogicException;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;

/**
 * Created by Linus on 2015-11-27.
 */
public class Base {
   private static final int STARTING_CAPTURE_STATUS = 20;

   private final Position _mapPosition;
   private final TerrainType _baseType;
   private Faction _owningFaction;
   private int _captureStatus;

   public static Base create(Position mapPosition, TerrainType baseType, Faction owningFaction) {
      if (!baseType.isBuilding()) {
         throw new LogicException("Tried to create Base with non-base terrain type!");
      }
      return new Base(mapPosition, baseType, owningFaction);
   }

   private Base(Position mapPosition, TerrainType baseType, Faction owningFaction) {
      _mapPosition = mapPosition;
      _baseType = baseType;
      _captureStatus = STARTING_CAPTURE_STATUS;
      _owningFaction = owningFaction;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Base base = (Base) o;

      return _baseType == base._baseType &&
            _mapPosition.equals(base._mapPosition);

   }

   @Override
   public int hashCode() {
      int result = _mapPosition.hashCode();
      result = 31 * result + _baseType.hashCode();
      return result;
   }
}
