public class HistoryRecord {
    public long id;
    public String datetime;
    public TempUnit fromUnit;
    public double fromValue;
    public TempUnit toUnit;
    public double toValue;

    public HistoryRecord(long id, String datetime, TempUnit fromUnit, double fromValue, TempUnit toUnit, double toValue) {
        this.id = id;
        this.datetime = datetime;
        this.fromUnit = fromUnit;
        this.fromValue = fromValue;
        this.toUnit = toUnit;
        this.toValue = toValue;
    }
}
