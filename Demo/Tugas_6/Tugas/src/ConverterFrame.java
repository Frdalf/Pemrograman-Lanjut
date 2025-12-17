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

    private final JComboBox<TempUnit> fromUnit = new JComboBox<>(TempUnit.values());
    private final JComboBox<TempUnit> toUnit = new JComboBox<>(TempUnit.values());
    private final JTextField fromValue = new HintTextField("Masukkan nilai");
    private final JTextField toValue = new JTextField();

    private final DecimalFormat df = new DecimalFormat("0.########");

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

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.setBorder(new EmptyBorder(20, 30, 0, 30));

        JLabel title = new JLabel("Konversi Suhu");
        title.setForeground(Color.WHITE);
        title.setFont(UIFactory.fontBold(28f));

        LinkLabel logout = new LinkLabel("Logout");
        logout.setForeground(new Color(235, 250, 255));
        logout.setHorizontalAlignment(SwingConstants.RIGHT);
        logout.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                new LoginFrame(store).setVisible(true);
                dispose();
            }
        });

        top.add(title, BorderLayout.WEST);
        top.add(logout, BorderLayout.EAST);
        root.add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        center.setBorder(new EmptyBorder(30, 30, 30, 30));
        root.add(center, BorderLayout.CENTER);

        RoundedCardPanel card = new RoundedCardPanel(24, true);
        card.setPreferredSize(new Dimension(820, 230));
        card.setLayout(null); // biar swap button pas di tengah “1:1”
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

        GradientButton convertBtn = new GradientButton("KONVERSI");
        convertBtn.setPreferredSize(new Dimension(820, 56));
        convertBtn.setFont(UIFactory.fontBold(18f));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0;
        c.insets = new Insets(0, 0, 18, 0);
        center.add(card, c);

        c.gridy = 1; c.insets = new Insets(0, 0, 0, 0);
        center.add(convertBtn, c);

        // style input/output
        fromValue.setHorizontalAlignment(SwingConstants.CENTER);
        fromValue.setFont(UIFactory.fontPlain(24f));
        toValue.setHorizontalAlignment(SwingConstants.CENTER);
        toValue.setFont(UIFactory.fontPlain(24f));
        toValue.setEditable(false);
        toValue.setBorder(null);
        toValue.setOpaque(false);

        fromUnit.setSelectedItem(TempUnit.CELSIUS);
        toUnit.setSelectedItem(TempUnit.FAHRENHEIT);
        fromValue.setText("25");
        convert(false);

        convertBtn.addActionListener(e -> convert(true));

        swap.addActionListener(e -> {
            TempUnit a = (TempUnit) fromUnit.getSelectedItem();
            TempUnit b = (TempUnit) toUnit.getSelectedItem();
            // swap the displayed values first to avoid triggering conversions
            String leftVal = fromValue.getText();
            String rightVal = toValue.getText();
            fromValue.setText(rightVal);
            toValue.setText(leftVal);

            // then swap the selected units
            fromUnit.setSelectedItem(b);
            toUnit.setSelectedItem(a);

            // perform a single conversion after swap
            convert(false);
        });

        DocumentListener dl = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { convert(false); }
            @Override public void removeUpdate(DocumentEvent e) { convert(false); }
            @Override public void changedUpdate(DocumentEvent e) { convert(false); }
        };
        fromValue.getDocument().addDocumentListener(dl);

        ItemListener il = e -> { if (e.getStateChange() == ItemEvent.SELECTED) convert(false); };
        fromUnit.addItemListener(il);
        toUnit.addItemListener(il);
    }

    private void convert(boolean showDialog) {
        String s = fromValue.getText().trim().replace(",", ".");
        if (s.isEmpty()) { toValue.setText(""); return; }

        double val;
        try { val = Double.parseDouble(s); }
        catch (NumberFormatException ex) {
            toValue.setText("");
            if (showDialog) JOptionPane.showMessageDialog(this, "Input harus angka (contoh: 25 atau 25.5).",
                    "Input salah", JOptionPane.WARNING_MESSAGE);
            return;
        }

        TempUnit from = (TempUnit) fromUnit.getSelectedItem();
        TempUnit to = (TempUnit) toUnit.getSelectedItem();
        if (from == null || to == null) return;

        double result = TemperatureMath.convert(val, from, to);
        toValue.setText(df.format(result));
    }
}
