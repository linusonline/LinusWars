package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.LogicException;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;

/**
 * Created by Linus on 2015-11-27.
 */
public class Building {
   private static final int STARTING_CAPTURE_STATUS = 20;

   private final Position _mapPosition;
   private final TerrainType _buildingType;
   private Faction _owningFaction;
   private Faction _capturingFaction;
   private int _captureStatus;

   public static Building create(Position mapPosition, TerrainType buildingType, Faction owningFaction) {
      if (!buildingType.isBuilding()) {
         throw new LogicException("Tried to create Building with non-building terrain type!");
      }
      return new Building(mapPosition, buildingType, owningFaction);
   }

   private Building(Position mapPosition, TerrainType buildingType, Faction owningFaction) {
      _mapPosition = mapPosition;
      _buildingType = buildingType;
      _captureStatus = STARTING_CAPTURE_STATUS;
      _owningFaction = owningFaction;
      _capturingFaction = Faction.NEUTRAL;
   }

   public Faction getFaction() {
      return _owningFaction;
   }

   void setFaction(Faction faction) {
      if (isCapturing() && _capturingFaction == faction) {
         resetCapture();
      }
      _owningFaction = faction;
   }

   public TerrainType getBuildingType() {
      return _buildingType;
   }

   public Position getPosition() {
      return new Position(_mapPosition);
   }

   void doCapture(int captureValue, Faction faction) {
      if (isCapturing() && _capturingFaction != faction) {
         throw new LogicException("Building at " + _mapPosition + " is already being captured by " +
               _capturingFaction + ", faction " + faction + " cannot start capturing.");
      }
      if (_owningFaction == faction) {
         throw new LogicException("Building at " + _mapPosition + " is already owned by " + faction + ", cannot capture.");
      }
      internalDoCapture(captureValue, faction);
   }

   private void internalDoCapture(int captureValue, Faction faction) {
      _captureStatus -= captureValue;
      _capturingFaction = faction;
      if (_captureStatus < 1) {
         _captureStatus = STARTING_CAPTURE_STATUS;
         _owningFaction = faction;
         _capturingFaction = Faction.NEUTRAL;
      }
   }

   void resetCapture() {
      _captureStatus = STARTING_CAPTURE_STATUS;
      _capturingFaction = Faction.NEUTRAL;
   }

   boolean isCapturing() {
      return _captureStatus < STARTING_CAPTURE_STATUS;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Building building = (Building) o;

      return _buildingType == building._buildingType &&
            _mapPosition.equals(building._mapPosition);

   }

   @Override
   public int hashCode() {
      int result = _mapPosition.hashCode();
      result = 31 * result + _buildingType.hashCode();
      return result;
   }
}
