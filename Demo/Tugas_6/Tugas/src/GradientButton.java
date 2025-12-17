import javax.swing.*;
import java.awt.*;

public class GradientButton extends JButton {
    public GradientButton(String text) {
        super(text);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setForeground(Color.WHITE);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();

        g2.setColor(new Color(0, 0, 0, 28));
        g2.fillRoundRect(6, 8, w - 12, h - 12, 30, 30);

        GradientPaint gp = new GradientPaint(0, 0, UIColors.BTN_A, w, h, UIColors.BTN_B);
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, w - 12, h - 12, 30, 30);

        g2.setColor(getForeground());
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        String s = getText();
        int tx = (w - 12 - fm.stringWidth(s)) / 2;
        int ty = (h - 12 - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(s, tx, ty);

        g2.dispose();
    }
}
