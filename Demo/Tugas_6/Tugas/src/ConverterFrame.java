import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class ConverterFrame extends JFrame {
    private final CSVUserStore store;
    private final String username;

    private final HistoryStore historyStore = new HistoryStore("history.csv");

    private final JComboBox<TempUnit> fromUnit = new JComboBox<>(TempUnit.values());
    private final JComboBox<TempUnit> toUnit = new JComboBox<>(TempUnit.values());
    private final JTextField fromValue = new HintTextField("Masukkan nilai");
    private final JTextField toValue = new JTextField();

    private final DecimalFormat df = new DecimalFormat("0.########");

    private boolean isSwapping = false;

    public ConverterFrame(CSVUserStore store, String username) {
        this.store = store;
        this.username = username;

        setTitle("Konversi Suhu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1040, 600);
        setLocationRelativeTo(null);

        ConverterBackgroundPanel root = new ConverterBackgroundPanel();
        root.setLayout(new BorderLayout());
        setContentPane(root);

        // ===== Top bar (judul saja) =====
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.setBorder(new EmptyBorder(20, 30, 0, 30));

        JLabel title = new JLabel("Konversi Suhu");
        title.setForeground(Color.WHITE);
        title.setFont(UIFactory.fontBold(28f));

        top.add(title, BorderLayout.WEST);
        root.add(top, BorderLayout.NORTH);

        // ===== Center =====
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        center.setBorder(new EmptyBorder(30, 30, 30, 30));
        root.add(center, BorderLayout.CENTER);

        // ===== Card konversi =====
        RoundedCardPanel card = new RoundedCardPanel(24, true);
        card.setPreferredSize(new Dimension(820, 230));
        card.setLayout(null);
        card.setBorder(new EmptyBorder(18, 18, 18, 18));

        JPanel left = UIFactory.unitBlock("Dari", fromUnit, fromValue, true);
        JPanel right = UIFactory.unitBlock("Ke", toUnit, toValue, false);

        left.setBounds(28, 46, 350, 150);
        right.setBounds(442, 46, 350, 150);

        CircleButton swap = new CircleButton("⇄");
        swap.setBounds(392, 96, 64, 64);

        card.add(left);
        card.add(right);
        card.add(swap);

        // ===== Tombol bawah (SEJAJAR CARD 820px) =====
        // total card 820, gap 14, riwayat 200 => konversi 606
        GradientButton convertBtn = new GradientButton("KONVERSI");
        convertBtn.setPreferredSize(new Dimension(606, 56));
        convertBtn.setMinimumSize(new Dimension(606, 56));
        convertBtn.setFont(UIFactory.fontBold(18f));

        GradientButton historyBtn = new GradientButton("RIWAYAT");
        historyBtn.setPreferredSize(new Dimension(200, 56));
        historyBtn.setMinimumSize(new Dimension(200, 56));
        historyBtn.setFont(UIFactory.fontBold(16f));

        JPanel bottomRow = new JPanel(new GridBagLayout());
        bottomRow.setOpaque(false);
        bottomRow.setPreferredSize(new Dimension(820, 56));

        GridBagConstraints bc = new GridBagConstraints();
        bc.gridy = 0;
        bc.anchor = GridBagConstraints.NORTHWEST;
        bc.insets = new Insets(0, 0, 0, 0);

        // KONVERSI (fixed 606, tidak melebar)
        bc.gridx = 0;
        bc.weightx = 0;
        bc.fill = GridBagConstraints.NONE;
        bottomRow.add(convertBtn, bc);

        // RIWAYAT (fixed 200)
        bc.gridx = 1;
        bc.insets = new Insets(0, 14, 0, 0);
        bottomRow.add(historyBtn, bc);

        // ===== Pasang card + bottomRow ke center =====
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 18, 0);
        center.add(card, c);

        c.gridy = 1;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 0, 0, 0);
        center.add(bottomRow, c);

        // ===== Style input/output =====
        fromValue.setHorizontalAlignment(SwingConstants.CENTER);
        fromValue.setFont(UIFactory.fontPlain(24f));

        toValue.setHorizontalAlignment(SwingConstants.CENTER);
        toValue.setFont(UIFactory.fontPlain(24f));
        toValue.setEditable(false);
        toValue.setBorder(null);
        toValue.setOpaque(false);

        // default
        fromUnit.setSelectedItem(TempUnit.CELSIUS);
        toUnit.setSelectedItem(TempUnit.FAHRENHEIT);
        fromValue.setText("25");
        convert(false);

        // ===== Actions =====
        swap.addActionListener(e -> {
            isSwapping = true;

            TempUnit oldFrom = (TempUnit) fromUnit.getSelectedItem();
            TempUnit oldTo   = (TempUnit) toUnit.getSelectedItem();

            String leftText  = fromValue.getText().trim();
            String rightText = toValue.getText().trim();

            // tukar unit
            fromUnit.setSelectedItem(oldTo);
            toUnit.setSelectedItem(oldFrom);

            // pindahkan nilai: pakai hasil kanan kalau ada
            if (!rightText.isEmpty()) {
                fromValue.setText(rightText);
            } else {
                fromValue.setText(leftText);
            }

            isSwapping = false;
            convert(false);
        });

        // KONVERSI -> hitung + simpan riwayat (Create)
        convertBtn.addActionListener(e -> {
            Double result = convert(true);
            if (result == null) return;

            String s = fromValue.getText().trim().replace(",", ".");
            double val = Double.parseDouble(s);

            TempUnit from = (TempUnit) fromUnit.getSelectedItem();
            TempUnit to = (TempUnit) toUnit.getSelectedItem();
            if (from == null || to == null) return;

            historyStore.add(from, val, to, result);
            JOptionPane.showMessageDialog(this, "Riwayat tersimpan.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        // RIWAYAT -> buka halaman tabel
        historyBtn.addActionListener(e -> {
            HistoryFrame hf = new HistoryFrame(historyStore, this);
            hf.setVisible(true);
            this.setVisible(false);
        });

        // auto convert saat input/units berubah (skip saat swap)
        DocumentListener dl = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { if (!isSwapping) convert(false); }
            @Override public void removeUpdate(DocumentEvent e) { if (!isSwapping) convert(false); }
            @Override public void changedUpdate(DocumentEvent e) { if (!isSwapping) convert(false); }
        };
        fromValue.getDocument().addDocumentListener(dl);

        ItemListener il = e -> {
            if (e.getStateChange() == ItemEvent.SELECTED && !isSwapping) convert(false);
        };
        fromUnit.addItemListener(il);
        toUnit.addItemListener(il);
    }

    private Double convert(boolean showDialog) {
        String s = fromValue.getText().trim().replace(",", ".");
        if (s.isEmpty()) { toValue.setText(""); return null; }

        double val;
        try {
            val = Double.parseDouble(s);
        } catch (NumberFormatException ex) {
            toValue.setText("");
            if (showDialog) {
                JOptionPane.showMessageDialog(this,
                        "Input harus angka (contoh: 25 atau 25.5).",
                        "Input salah", JOptionPane.WARNING_MESSAGE);
            }
            return null;
        }

        TempUnit from = (TempUnit) fromUnit.getSelectedItem();
        TempUnit to = (TempUnit) toUnit.getSelectedItem();
        if (from == null || to == null) return null;

        double result = TemperatureMath.convert(val, from, to);
        toValue.setText(df.format(result));
        return result;
    }
}
