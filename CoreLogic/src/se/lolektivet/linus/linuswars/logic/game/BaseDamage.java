package se.lolektivet.linus.linuswars.logic.game;

/**
 * Created by Linus on 2014-09-19.
 */
class BaseDamage {
   private final int _specificDamage;
   private final boolean _canAttack;

   static BaseDamage cannotAttack() {
      return new BaseDamage();
   }

   static BaseDamage create(int damage) {
      return new BaseDamage(damage);
   }

   private BaseDamage(int specificDamage) {
      _canAttack = true;
      _specificDamage = specificDamage;
   }

   private BaseDamage() {
      _specificDamage = 0;
      _canAttack = false;
   }

   boolean canAttack() {
      return _canAttack;
   }

   int getDamage() {
      return _specificDamage;
   }
}
