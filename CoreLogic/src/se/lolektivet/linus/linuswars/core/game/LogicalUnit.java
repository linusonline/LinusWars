package se.lolektivet.linus.linuswars.core.game;

import se.lolektivet.linus.linuswars.core.LogicException;
import se.lolektivet.linus.linuswars.core.enums.MovementType;
import se.lolektivet.linus.linuswars.core.enums.UnitType;

/**
 * Created by Linus on 2014-09-18.
 */
public class LogicalUnit {

   private static final int MAX_HP = 100;

   private final UnitType _type;
   private final MovementType _movement;
   private final int _cost;
   private final int _maxFuel;
   private final int _maxAmmo;
   private final int _baseVision;
   private final int _baseMinAttackRange;
   private final int _baseMaxAttackRange;
   private final int _baseMovementRange;
   private final boolean _isTransport;
   private final boolean _isRanged;
   private int _fuel;
   private int _ammo;
   private int _hp;

   private boolean _unitIsDestroyed = false;
   private boolean _subIsSubmerged = false;

   static LogicalUnit createTransportUnit(UnitType type, MovementType movement, int cost, int maxFuel, int baseVision, int baseMovementRange) {
      return new LogicalUnit(type, movement, cost, maxFuel, baseVision, baseMovementRange, 0, 0, 0, true, false);
   }

   static LogicalUnit createCombatUnit(UnitType type, MovementType movement, int cost, int maxFuel, int baseVision, int baseMovementRange, int maxAmmo) {
      return new LogicalUnit(type, movement, cost, maxFuel, baseVision, baseMovementRange, maxAmmo, 0, 0, false, false);
   }

   static LogicalUnit createRangedUnit(UnitType type, MovementType movement, int cost, int maxFuel, int baseVision, int baseMovementRange, int maxAmmo, int baseMinAttackRange, int baseMaxAttackRange) {
      return new LogicalUnit(type, movement, cost, maxFuel, baseVision, baseMovementRange, maxAmmo, baseMinAttackRange, baseMaxAttackRange, false, true);
   }

   private LogicalUnit(UnitType type, MovementType movement, int cost, int maxFuel, int baseVision, int baseMovementRange, int maxAmmo, int baseMinAttackRange, int baseMaxAttackRange, boolean isTransport, boolean isRanged) {
      _type = type;
      _movement = movement;
      _cost = cost;
      _maxFuel = maxFuel;
      _maxAmmo = maxAmmo;
      _baseVision = baseVision;
      _baseMinAttackRange = baseMinAttackRange;
      _baseMaxAttackRange = baseMaxAttackRange;
      _baseMovementRange = baseMovementRange;
      _isTransport = isTransport;
      _isRanged = isRanged;

      _fuel = _maxFuel;
      _ammo = _maxAmmo;
      _hp = MAX_HP;
   }

   public int getCost() {
      return _cost;
   }

   public UnitType getType() {
      return _type;
   }

   MovementType getMovementType() {
      return _movement;
   }

   public int getMaxFuel() {
      return _maxFuel;
   }

   public int getMaxAmmo() {
      return _maxAmmo;
   }

   boolean isLand() {
      return !isSea() && !isAir();
   }

   boolean isSea() {
      return _movement == MovementType.SEA || _movement == MovementType.SEA_TRANSPORT;
   }

   boolean isAir() {
      return _movement == MovementType.AIR;
   }

   boolean isTransport() {
      return _isTransport;
   }

   boolean isRanged() {
      return _isRanged;
   }

   boolean isMelee() {
      return !_isRanged && !_isTransport;
   }

   public boolean canCapture() {
      return _movement == MovementType.FOOT ||
            _movement == MovementType.MECH;
   }

   public int getBaseMovementRange() {
      return _baseMovementRange;
   }

   public int getBaseMinAttackRange() {
      return _baseMinAttackRange;
   }

   public int getBaseMaxAttackRange() {
      return _baseMaxAttackRange;
   }



   int getHp1To100() {
      return _hp;
   }

   public int getHp1To10() {
      // TODO: Find out about actual math here! Always round up?

      // Round down, except when below 10
//      return _hp <= 0 ? 0 : Math.max(1, _hp / 10);

      // Always round up
      return _hp % 10 > 0 ? (_hp + 1) / 10 : _hp / 10;
   }

   // Core method
   public void setHp1To100(int hp) {
      throwIfUnitDestroyed();
      _hp = hp;
   }

   boolean isDamaged() {
      return getHp1To10() < 10;
   }

   public int getFuel() {
      return _fuel;
   }

   // Core method
   public void subtractFuel(int fuelCost) {
      throwIfUnitDestroyed();
      _fuel = Math.max(0, _fuel - fuelCost);
   }

   // Core method
   public void addFuel(int extraFuel) {
      throwIfUnitDestroyed();
      _fuel = Math.min(extraFuel + _fuel, _maxFuel);
   }

   // Core method
   public void resupply() {
      throwIfUnitDestroyed();
      _fuel = _maxFuel;
      _ammo = _maxAmmo;
   }

   @Override
   public String toString() {
      return _type + ":\n" +
            "Fuel: " + _fuel + "/" + _maxFuel;
   }

   public void healHpPercent(int hpPercent) {
      throwIfUnitDestroyed();
      _hp = Math.min(hpPercent + _hp, MAX_HP);
   }

   public void setUnitDestroyed() {
      throwIfUnitDestroyed();
      _unitIsDestroyed = true;
   }

   public boolean isUnitDestroyed() {
      return _unitIsDestroyed;
   }

   private void throwIfUnitDestroyed() {
      if (_unitIsDestroyed) {
         throw new LogicException("An operation was called on an already destroyed unit!");
      }
   }

   public boolean subIsSubmerged() {
      throwIfNotSub();
      return _subIsSubmerged;
   }

   public void setSubSubmerged(boolean submerged) {
      throwIfNotSub();
      throwIfUnitDestroyed();
      _subIsSubmerged = submerged;
   }

   private void throwIfNotSub() {
      if (_type != UnitType.SUB) {
         throw new LogicException("Operation only supported for sub!");
      }
   }
}
