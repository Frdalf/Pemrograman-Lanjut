public enum TempUnit {
    CELSIUS("Celcius (°C)"),
    FAHRENHEIT("Fahrenheit (°F)"),
    KELVIN("Kelvin (K)");

    private final String label;
    TempUnit(String label) { this.label = label; }
    @Override public String toString() { return label; }
}
