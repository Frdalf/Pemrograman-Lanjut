import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        // 1. Membuat Frame Utama (Langkah 2)
        JFrame frame = new JFrame("Password Validation");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        // 2. Menambahkan Label (Langkah 3)
        JLabel label = new JLabel("Enter PIN (6 digits only):");
        frame.add(label);

        // 3. Menambahkan Password Field (Langkah 4)
        JPasswordField passwordField = new JPasswordField(10); // columns: 10
        frame.add(passwordField);

        // 4. Menambahkan Tombol Validasi (Langkah 5)
        JButton button = new JButton("Validate");
        frame.add(button);

        // 5. Menambahkan Logika Validasi (Langkah 6)
        button.addActionListener(e -> {
            // Mengambil password dari field dan mengubahnya menjadi String
            String password = new String(passwordField.getPassword());

            // Cek 1: Apakah panjang karakter tidak sama dengan 6?
            if (password.length() != 6) {
                JOptionPane.showMessageDialog(
                        frame,
                        "PIN must be exactly 6 characters long!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return; // Menghentikan proses jika salah
            }

            // Cek 2: Apakah isinya bukan angka (menggunakan Regex \\d+)?
            if (!password.matches("\\d+")) {
                JOptionPane.showMessageDialog(
                        frame,
                        "PIN must contain digits only (no letters or symbols)!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return; // Menghentikan proses jika salah
            }

            // Jika lolos semua cek
            JOptionPane.showMessageDialog(
                    frame,
                    "Correct PIN!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        // 6. Menampilkan Frame (Langkah 7)
        frame.setVisible(true);
    }
}