import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UIFactory {
    public static Font fontBold(float size) {
        return new Font("SansSerif", Font.BOLD, Math.round(size));
    }
    public static Font fontPlain(float size) {
        return new Font("SansSerif", Font.PLAIN, Math.round(size));
    }

    public static JLabel title(String text) {
        JLabel l = new JLabel(text, SwingConstants.CENTER);
        l.setFont(fontBold(30f));
        return l;
    }

    public static JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(fontPlain(14f));
        return l;
    }

    public static JPanel roundedField(JComponent field) {
        RoundedOutlinePanel outline = new RoundedOutlinePanel(18);
        outline.setLayout(new BorderLayout());
        outline.setBorder(new EmptyBorder(10, 14, 10, 14));
        outline.add(field, BorderLayout.CENTER);

        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.add(outline, BorderLayout.CENTER);
        return p;
    }

    public static JPanel roundedPasswordField(HintPasswordField pass) {
        RoundedOutlinePanel outline = new RoundedOutlinePanel(18);
        outline.setLayout(new BorderLayout());
        outline.setBorder(new EmptyBorder(10, 14, 10, 10));

        JButton eye = new JButton("👁");
        eye.setFocusPainted(false);
        eye.setBorderPainted(false);
        eye.setContentAreaFilled(false);
        eye.setOpaque(false);
        eye.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        eye.setPreferredSize(new Dimension(44, 40));

        char defaultEcho = pass.getEchoChar();
        eye.addActionListener(e -> {
            pass.setEchoChar(pass.getEchoChar() == 0 ? defaultEcho : (char) 0);
            pass.requestFocusInWindow();
        });

        outline.add(pass, BorderLayout.CENTER);
        outline.add(eye, BorderLayout.EAST);

        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.add(outline, BorderLayout.CENTER);
        return p;
    }

    public static JPanel unitBlock(String label, JComboBox<TempUnit> combo, JTextField field, boolean editableField) {
        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setOpaque(false);

        JLabel lbl = new JLabel(label);
        lbl.setFont(fontPlain(14f));

        RoundedOutlinePanel comboWrap = new RoundedOutlinePanel(16);
        comboWrap.setLayout(new BorderLayout());
        comboWrap.setBorder(new EmptyBorder(8, 12, 8, 12));
        combo.setBorder(null);
        combo.setOpaque(false);
        combo.setFont(fontPlain(16f));
        comboWrap.add(combo, BorderLayout.CENTER);

        RoundedOutlinePanel valueWrap = new RoundedOutlinePanel(16);
        valueWrap.setLayout(new BorderLayout());
        valueWrap.setBorder(new EmptyBorder(14, 12, 14, 12));
        field.setBorder(null);
        field.setOpaque(false);
        field.setEditable(editableField);
        valueWrap.add(field, BorderLayout.CENTER);

        JPanel stack = new JPanel(new BorderLayout(0, 10));
        stack.setOpaque(false);
        stack.add(comboWrap, BorderLayout.NORTH);
        stack.add(valueWrap, BorderLayout.CENTER);

        p.add(lbl, BorderLayout.NORTH);
        p.add(stack, BorderLayout.CENTER);

        return p;
    }
}
