import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.*;

public class CSVUserStore {
    private final Path csvPath;

    public CSVUserStore(String filename) {
        this.csvPath = Paths.get(filename);
    }

    public void ensureDefaultUser() {
        try {
            if (!Files.exists(csvPath)) {
                Files.writeString(csvPath, "username,password_hash\n", StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                register("admin", "admin");
            }
        } catch (IOException ignored) {}
    }

    public synchronized boolean validate(String username, String password) {
        Map<String, String> users = readUsers();
        String stored = users.get(username);
        return stored != null && stored.equals(hash(password));
    }

    public synchronized boolean register(String username, String password) {
        username = username.trim();
        if (username.isEmpty()) return false;

        Map<String, String> users = readUsers();
        if (users.containsKey(username)) return false;

        users.put(username, hash(password));
        return writeUsers(users);
    }

    public synchronized boolean resetPassword(String username, String newPassword) {
        Map<String, String> users = readUsers();
        if (!users.containsKey(username)) return false;

        users.put(username, hash(newPassword));
        return writeUsers(users);
    }

    private Map<String, String> readUsers() {
        Map<String, String> map = new LinkedHashMap<>();
        try {
            if (!Files.exists(csvPath)) return map;
            var lines = Files.readAllLines(csvPath, StandardCharsets.UTF_8);

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (line.isEmpty()) continue;
                if (i == 0 && line.toLowerCase().contains("username")) continue;

                String[] parts = splitCsvLine(line);
                if (parts.length < 2) continue;

                String u = parts[0].trim();
                String h = parts[1].trim();
                if (!u.isEmpty() && !h.isEmpty()) map.put(u, h);
            }
        } catch (IOException ignored) {}
        return map;
    }

    private boolean writeUsers(Map<String, String> users) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("username,password_hash\n");
            for (var e : users.entrySet()) {
                sb.append(escapeCsv(e.getKey())).append(",")
                        .append(escapeCsv(e.getValue())).append("\n");
            }
            Files.writeString(csvPath, sb.toString(), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    private String hash(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] out = md.digest(s.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : out) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            return Integer.toHexString(Objects.hashCode(s));
        }
    }

    private static String escapeCsv(String s) {
        if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }

    private static String[] splitCsvLine(String line) {
        java.util.List<String> parts = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    cur.append('"'); i++;
                } else inQuotes = !inQuotes;
            } else if (ch == ',' && !inQuotes) {
                parts.add(cur.toString());
                cur.setLength(0);
            } else cur.append(ch);
        }
        parts.add(cur.toString());
        return parts.toArray(new String[0]);
    }
}
