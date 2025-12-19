import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class HistoryFrame extends JFrame {
    private final HistoryStore historyStore;
    private final ConverterFrame backTo;

    private final HistoryTableModel model = new HistoryTableModel();
    private final JTable table = new JTable(model);

    public HistoryFrame(HistoryStore historyStore, ConverterFrame backTo) {
        this.historyStore = historyStore;
        this.backTo = backTo;

        setTitle("Riwayat Konversi");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1040, 600);
        setLocationRelativeTo(null);

        ConverterBackgroundPanel root = new ConverterBackgroundPanel();
        root.setLayout(new BorderLayout());
        setContentPane(root);

        // ===== Top bar =====
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.setBorder(new EmptyBorder(20, 30, 0, 30));

        JLabel title = new JLabel("Riwayat Konversi");
        title.setForeground(Color.WHITE);
        title.setFont(UIFactory.fontBold(28f));

        // ✅ ganti link "Kembali" jadi button seperti RIWAYAT
        GradientButton backBtn = new GradientButton("KEMBALI");
        backBtn.setFont(UIFactory.fontBold(13f));
        backBtn.setPreferredSize(new Dimension(140, 40));
        backBtn.setMinimumSize(new Dimension(140, 40));
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        backBtn.addActionListener(e -> {
            if (backTo != null) backTo.setVisible(true);
            dispose();
        });

        // biar tombol tidak "melebar" ikut layout
        JPanel backWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        backWrap.setOpaque(false);
        backWrap.add(backBtn);

        top.add(title, BorderLayout.WEST);
        top.add(backWrap, BorderLayout.EAST);
        root.add(top, BorderLayout.NORTH);

        // ===== Center card =====
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        center.setBorder(new EmptyBorder(30, 30, 30, 30));
        root.add(center, BorderLayout.CENTER);

        RoundedCardPanel card = new RoundedCardPanel(24, true);
        card.setPreferredSize(new Dimension(920, 420));
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(18, 18, 18, 18));

        // JTable styling (header, row height, grid, zebra)
        table.setRowHeight(34);
        table.setShowGrid(true);
        table.setGridColor(new Color(210, 230, 240));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTableHeader header = table.getTableHeader();
        header.setFont(UIFactory.fontBold(14f));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 38));
        header.setDefaultRenderer(new HeaderRenderer(table));

        table.setDefaultRenderer(Object.class, new ZebraRenderer());
        table.setDefaultRenderer(Number.class, new ZebraRenderer());
        table.setDefaultRenderer(Long.class, new ZebraRenderer());

        // column width (biar rapi)
        TableColumnModel cm = table.getColumnModel();
        cm.getColumn(0).setPreferredWidth(60);   // ID
        cm.getColumn(1).setPreferredWidth(180);  // Waktu
        cm.getColumn(2).setPreferredWidth(180);  // Dari
        cm.getColumn(3).setPreferredWidth(130);  // Nilai
        cm.getColumn(4).setPreferredWidth(180);  // Ke
        cm.getColumn(5).setPreferredWidth(130);  // Hasil

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder());
        card.add(sp, BorderLayout.CENTER);

        // Button bar (CRUD)
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnBar.setOpaque(false);

        JButton refresh = new JButton("Refresh");
        JButton edit = new JButton("Edit");
        JButton delete = new JButton("Hapus");
        JButton deleteAll = new JButton("Hapus Semua");

        styleSmallButton(refresh);
        styleSmallButton(edit);
        styleSmallButton(delete);
        styleSmallButton(deleteAll);

        btnBar.add(refresh);
        btnBar.add(edit);
        btnBar.add(delete);
        btnBar.add(deleteAll);

        card.add(btnBar, BorderLayout.SOUTH);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0;
        center.add(card, c);

        // actions
        refresh.addActionListener(e -> reload());

        delete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Pilih 1 baris dulu.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int modelRow = table.convertRowIndexToModel(row);
            HistoryRecord r = model.getRecordAt(modelRow);

            int ok = JOptionPane.showConfirmDialog(this,
                    "Yakin hapus data ID " + r.id + " ?",
                    "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (ok == JOptionPane.YES_OPTION) {
                historyStore.deleteById(r.id);
                reload();
            }
        });

        deleteAll.addActionListener(e -> {
            int ok = JOptionPane.showConfirmDialog(this,
                    "Yakin hapus SEMUA riwayat?",
                    "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (ok == JOptionPane.YES_OPTION) {
                historyStore.deleteAll();
                reload();
            }
        });

        edit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Pilih 1 baris dulu.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int modelRow = table.convertRowIndexToModel(row);
            HistoryRecord r = model.getRecordAt(modelRow);
            if (r == null) return;

            showEditDialog(r);
        });

        reload();
    }

    private void reload() {
        List<HistoryRecord> all = historyStore.readAll();
        model.setRecords(all);
    }

    private void showEditDialog(HistoryRecord r) {
        JComboBox<TempUnit> fromU = new JComboBox<>(TempUnit.values());
        JComboBox<TempUnit> toU = new JComboBox<>(TempUnit.values());
        JTextField fromV = new JTextField(String.valueOf(r.fromValue));

        fromU.setSelectedItem(r.fromUnit);
        toU.setSelectedItem(r.toUnit);

        JPanel panel = new JPanel(new GridLayout(0, 1, 6, 6));
        panel.add(new JLabel("Dari (Unit)"));
        panel.add(fromU);
        panel.add(new JLabel("Nilai"));
        panel.add(fromV);
        panel.add(new JLabel("Ke (Unit)"));
        panel.add(toU);

        int ok = JOptionPane.showConfirmDialog(this, panel, "Edit Riwayat (ID " + r.id + ")", JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;

        String s = fromV.getText().trim().replace(",", ".");
        double val;
        try {
            val = Double.parseDouble(s);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Nilai harus angka.", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        TempUnit newFrom = (TempUnit) fromU.getSelectedItem();
        TempUnit newTo = (TempUnit) toU.getSelectedItem();
        if (newFrom == null || newTo == null) return;

        double result = TemperatureMath.convert(val, newFrom, newTo);

        HistoryRecord updated = new HistoryRecord(
                r.id,
                r.datetime,
                newFrom,
                val,
                newTo,
                result
        );

        historyStore.update(updated);
        reload();
    }

    private void styleSmallButton(JButton b) {
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    static class HeaderRenderer extends DefaultTableCellRenderer {
        public HeaderRenderer(JTable table) {
            setHorizontalAlignment(CENTER);
            setOpaque(true);
        }
        @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                                 boolean hasFocus, int row, int col) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
            setBackground(new Color(235, 245, 250));
            setForeground(new Color(30, 60, 80));
            setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(210, 230, 240)));
            return this;
        }
    }

    static class ZebraRenderer extends DefaultTableCellRenderer {
        @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                                 boolean hasFocus, int row, int col) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
            setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(230, 240, 245)));

            if (!isSelected) {
                Color even = Color.WHITE;
                Color odd = new Color(248, 252, 255);
                setBackground((row % 2 == 0) ? even : odd);
                setForeground(new Color(30, 30, 30));
            } else {
                setBackground(new Color(210, 235, 255));
                setForeground(new Color(20, 20, 20));
            }

            setHorizontalAlignment(SwingConstants.CENTER);
            return this;
        }
    }
}
