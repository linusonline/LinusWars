package se.lolektivet.linus.linuswars.core.game;

import se.lolektivet.linus.linuswars.core.enums.UnitType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Linus on 2014-09-29.
 */
class TransportLogic {
   private final Map<UnitType, List<UnitType>> _transportCapabilities;
   private final Map<UnitType, Integer> _transportLimits;

   TransportLogic() {
      _transportCapabilities = new HashMap<>(4);
      _transportLimits = new HashMap<>(4);
      initializeTransportCapabilities();
      initializeTransportLimits();
   }

   private void initializeTransportCapabilities() {
      List<UnitType> apcTransportCapabilities = new ArrayList<UnitType>(2);
      apcTransportCapabilities.add(UnitType.INFANTRY);
      apcTransportCapabilities.add(UnitType.MECH);

      List<UnitType> helicopterTransportCapabilities = new ArrayList<UnitType>(2);
      helicopterTransportCapabilities.add(UnitType.INFANTRY);
      helicopterTransportCapabilities.add(UnitType.MECH);

      List<UnitType> landerTransportCapabilities = new ArrayList<UnitType>(2);
      landerTransportCapabilities.add(UnitType.INFANTRY);
      landerTransportCapabilities.add(UnitType.MECH);
      landerTransportCapabilities.add(UnitType.RECON);
      landerTransportCapabilities.add(UnitType.TANK);
      landerTransportCapabilities.add(UnitType.MD_TANK);
      landerTransportCapabilities.add(UnitType.APC);
      landerTransportCapabilities.add(UnitType.ARTILLERY);
      landerTransportCapabilities.add(UnitType.ROCKETS);
      landerTransportCapabilities.add(UnitType.ANTI_AIR);
      landerTransportCapabilities.add(UnitType.MISSILES);

      List<UnitType> cruiserTransportCapabilities = new ArrayList<UnitType>(2);
      cruiserTransportCapabilities.add(UnitType.B_COPTER);
      cruiserTransportCapabilities.add(UnitType.T_COPTER);

      _transportCapabilities.put(UnitType.APC, apcTransportCapabilities);
      _transportCapabilities.put(UnitType.T_COPTER, helicopterTransportCapabilities);
      _transportCapabilities.put(UnitType.LANDER, landerTransportCapabilities);
      _transportCapabilities.put(UnitType.CRUISER, cruiserTransportCapabilities);
   }

   private void initializeTransportLimits() {
      _transportLimits.put(UnitType.APC, 1);
      _transportLimits.put(UnitType.T_COPTER, 1);
      _transportLimits.put(UnitType.LANDER, 2);
      _transportLimits.put(UnitType.CRUISER, 2);
   }

   private boolean isTransportType(UnitType unitType) {
      return _transportCapabilities.containsKey(unitType);
   }

   boolean canTransport(UnitType transporter, UnitType passenger) {
      return isTransportType(transporter) && _transportCapabilities.get(transporter).contains(passenger);
   }

   int getTransportLimit(UnitType transporter) {
      return _transportLimits.get(transporter);
   }
}
