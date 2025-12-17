public class TemperatureMath {
    public static double convert(double value, TempUnit from, TempUnit to) {
        double k = toKelvin(value, from);
        return fromKelvin(k, to);
    }

    private static double toKelvin(double v, TempUnit u) {
        return switch (u) {
            case CELSIUS -> v + 273.15;
            case FAHRENHEIT -> (v - 32) * 5.0/9.0 + 273.15;
            case KELVIN -> v;
        };
    }

    private static double fromKelvin(double k, TempUnit u) {
        return switch (u) {
            case CELSIUS -> k - 273.15;
            case FAHRENHEIT -> (k - 273.15) * 9.0/5.0 + 32;
            case KELVIN -> k;
        };
    }
}
