import javax.swing.table.AbstractTableModel;
import java.text.DecimalFormat;
import java.util.*;

public class HistoryTableModel extends AbstractTableModel {
    private final String[] cols = {"ID", "Waktu", "Dari", "Nilai", "Ke", "Hasil"};
    private final DecimalFormat df = new DecimalFormat("0.########");
    private List<HistoryRecord> records = new ArrayList<>();

    public void setRecords(List<HistoryRecord> records) {
        this.records = (records == null) ? new ArrayList<>() : records;
        fireTableDataChanged();
    }

    public HistoryRecord getRecordAt(int row) {
        if (row < 0 || row >= records.size()) return null;
        return records.get(row);
    }

    @Override public int getRowCount() { return records.size(); }
    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int col) { return cols[col]; }

    @Override public Object getValueAt(int row, int col) {
        HistoryRecord r = records.get(row);
        return switch (col) {
            case 0 -> r.id;
            case 1 -> r.datetime;
            case 2 -> r.fromUnit.toString();
            case 3 -> df.format(r.fromValue);
            case 4 -> r.toUnit.toString();
            case 5 -> df.format(r.toValue);
            default -> "";
        };
    }

    @Override public Class<?> getColumnClass(int col) {
        return (col == 0) ? Long.class : String.class;
    }
}
