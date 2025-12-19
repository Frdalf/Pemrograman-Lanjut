import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistoryStore {
    private final Path csvPath;

    public HistoryStore(String filename) {
        this.csvPath = Paths.get(filename);
        ensureFile();
    }

    private void ensureFile() {
        try {
            if (!Files.exists(csvPath)) {
                Files.writeString(csvPath,
                        "id,datetime,from_unit,from_value,to_unit,to_value\n",
                        StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            }
        } catch (IOException ignored) {}
    }

    public synchronized HistoryRecord add(TempUnit fromUnit, double fromValue, TempUnit toUnit, double toValue) {
        List<HistoryRecord> all = readAll();

        long nextId = 1;
        for (HistoryRecord r : all) nextId = Math.max(nextId, r.id + 1);

        String dt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        HistoryRecord record = new HistoryRecord(nextId, dt, fromUnit, fromValue, toUnit, toValue);

        all.add(record);
        writeAll(all);
        return record;
    }

    public synchronized List<HistoryRecord> readAll() {
        ensureFile();
        List<HistoryRecord> out = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(csvPath, StandardCharsets.UTF_8);
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (line.isEmpty()) continue;
                if (i == 0 && line.toLowerCase().contains("datetime")) continue;

                String[] p = splitCsvLine(line);
                if (p.length < 6) continue;

                long id = Long.parseLong(p[0].trim());
                String datetime = p[1].trim();
                TempUnit fromU = TempUnit.valueOf(p[2].trim());
                double fromV = Double.parseDouble(p[3].trim());
                TempUnit toU = TempUnit.valueOf(p[4].trim());
                double toV = Double.parseDouble(p[5].trim());

                out.add(new HistoryRecord(id, datetime, fromU, fromV, toU, toV));
            }
        } catch (Exception ignored) {}
        return out;
    }

    public synchronized boolean update(HistoryRecord updated) {
        List<HistoryRecord> all = readAll();
        boolean found = false;
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).id == updated.id) {
                all.set(i, updated);
                found = true;
                break;
            }
        }
        if (!found) return false;
        writeAll(all);
        return true;
    }

    public synchronized boolean deleteById(long id) {
        List<HistoryRecord> all = readAll();
        boolean removed = all.removeIf(r -> r.id == id);
        if (!removed) return false;
        writeAll(all);
        return true;
    }

    public synchronized void deleteAll() {
        writeAll(new ArrayList<>());
    }

    private void writeAll(List<HistoryRecord> all) {
        StringBuilder sb = new StringBuilder();
        sb.append("id,datetime,from_unit,from_value,to_unit,to_value\n");
        for (HistoryRecord r : all) {
            sb.append(r.id).append(",")
                    .append(escapeCsv(r.datetime)).append(",")
                    .append(r.fromUnit.name()).append(",")
                    .append(r.fromValue).append(",")
                    .append(r.toUnit.name()).append(",")
                    .append(r.toValue).append("\n");
        }
        try {
            Files.writeString(csvPath, sb.toString(), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException ignored) {}
    }

    private static String escapeCsv(String s) {
        if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }

    private static String[] splitCsvLine(String line) {
        List<String> parts = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    cur.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (ch == ',' && !inQuotes) {
                parts.add(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(ch);
            }
        }
        parts.add(cur.toString());
        return parts.toArray(new String[0]);
    }
}
