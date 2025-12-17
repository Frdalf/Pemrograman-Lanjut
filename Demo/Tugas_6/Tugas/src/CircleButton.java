import javax.swing.*;
import java.awt.*;

public class CircleButton extends JButton {
    private final String text;

    public CircleButton(String text) {
        this.text = text;
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();

        g2.setColor(new Color(0, 0, 0, 28));
        g2.fillOval(6, 8, w - 12, h - 12);

        GradientPaint gp = new GradientPaint(0, 0, UIColors.BTN_A, w, h, UIColors.BTN_B);
        g2.setPaint(gp);
        g2.fillOval(0, 0, w - 12, h - 12);

        g2.setColor(Color.WHITE);
        g2.setFont(getFont().deriveFont(Font.BOLD, 18f));
        FontMetrics fm = g2.getFontMetrics();
        int tx = (w - 12 - fm.stringWidth(text)) / 2;
        int ty = (h - 12 - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(text, tx, ty);

        g2.dispose();
    }
}
