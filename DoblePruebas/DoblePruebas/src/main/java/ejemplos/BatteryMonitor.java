package ejemplos;

public class BatteryMonitor {
    public void warnWhenBatteryPowerLow() {
        if ( DeviceApi.getBatteryPercentage() < 10 ) {
            System.out.println("Peligro - bateria baja");
        }
    }
}
