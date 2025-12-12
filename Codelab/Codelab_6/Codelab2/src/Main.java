import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        // 1. Membuat Frame Utama (Langkah 2)
        JFrame frame = new JFrame("Library Book List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // 2. Menyiapkan Data Awal (Langkah 3)
        String[][] data = {
                {"1", "Naruto", "Masashi Kishimoto", "1999"},
                {"2", "One Piece", "Eiichiro Oda", "1997"},
                {"3", "Attack on Titan", "Hajime Isayama", "2009"},
                {"4", "Demon Slayer", "Koyoharu Gotouge", "2016"},
                {"5", "Jujutsu Kaisen", "Gege Akutami", "2018"}
        };
        String[] columns = {"Book ID", "Title", "Author", "Year"};

        // 3. Membuat Tabel dengan DefaultTableModel (Langkah 4)
        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);

        // 4. Menambahkan Scroll Pane agar tabel bisa di-scroll (Langkah 5)
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // 5. Membuat Panel untuk Input Data (Langkah 6)
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Padding

        // 6. Membuat Komponen Input (TextField) (Langkah 7)
        JTextField idField = new JTextField(20);
        JTextField titleField = new JTextField(20);
        JTextField authorField = new JTextField(20);
        JTextField yearField = new JTextField(20);

        // 7. Menyusun Komponen ke dalam Panel (Langkah 8)
        panel.add(new JLabel("Book ID:"));
        panel.add(idField);
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Author:"));
        panel.add(authorField);
        panel.add(new JLabel("Year:"));
        panel.add(yearField);

        // 8. Menambahkan Tombol "Add Book" (Langkah 9)
        JButton addButton = new JButton("Add Book");
        addButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
        panel.add(addButton);

        // Menambahkan panel input ke bagian atas frame (North)
        frame.add(panel, BorderLayout.NORTH);

        // 9. Logika Tombol (Action Listener) (Langkah 10)
        addButton.addActionListener(e -> {
            String id = idField.getText();
            String title = titleField.getText();
            String author = authorField.getText();
            String year = yearField.getText();

            // Validasi: Cek jika ada kolom yang kosong
            if (id.isEmpty() || title.isEmpty() || author.isEmpty() || year.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "All fields must be filled!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Menambahkan baris baru ke tabel
            model.addRow(new Object[]{id, title, author, year});

            // Mengosongkan field setelah input berhasil
            idField.setText("");
            titleField.setText("");
            authorField.setText("");
            yearField.setText("");
        });

        // 10. Menampilkan Frame (Langkah 11)
        frame.setVisible(true);
    }
}