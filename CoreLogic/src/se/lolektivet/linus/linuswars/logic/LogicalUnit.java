package se.lolektivet.linus.linuswars.logic;

/**
 * Created by Linus on 2014-09-18.
 */
public class LogicalUnit {
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
      _hp = 100;
   }

   int getHp1To100() {
      return _hp;
   }

   public int getHp1To10() {
      // TODO: Find out about actual math here!
      return _hp / 10;
   }

   void setHp1To100(int hp) {
      _hp = hp;
   }

   MovementType getMovementType() {
      return _movement;
   }

   public UnitType getType()
   {
      return _type;
   }

   boolean isLand() {
      return !isSea() && !isAir();
   }

   boolean isSea() {
      return _movement.equals(MovementType.SEA);
   }

   boolean isAir() {
      return _movement.equals(MovementType.AIR);
   }

   boolean isTransport() {
      return _isTransport;
   }

   boolean isRanged() {
      return _isRanged;
   }

   boolean isCombat() {
      return !_isRanged && !_isTransport;
   }

   boolean canCapture() {
      return _movement.equals(MovementType.FOOT);
   }

   public int getBaseMovementRange() {
      return _baseMovementRange;
   }

   public int getFuel() {
      return _fuel;
   }

   public int getBaseMinAttackRange() {
      return _baseMinAttackRange;
   }

   public int getBaseMaxAttackRange() {
      return _baseMaxAttackRange;
   }

   public void subtractFuel(int fuelCost) {
      _fuel -= fuelCost;
      if (_fuel < 0) {
         throw new LogicException("Fuel was set to less than zero!");
      }
   }

   public void resupply() {
      _fuel = _maxFuel;
      _ammo = _maxAmmo;
   }

   @Override
   public String toString() {
      return _type + ":\n" +
            "Fuel: " + _fuel + "/" + _maxFuel;
   }
}
