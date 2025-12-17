import javax.swing.*;
import java.awt.*;

public class RoundedCardPanel extends JPanel {
    private final int radius;
    private final boolean shadow;

    public RoundedCardPanel(int radius, boolean shadow) {
        this.radius = radius;
        this.shadow = shadow;
        setOpaque(false);
    }

    @Override protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();

        if (shadow) {
            g2.setColor(new Color(0, 0, 0, 32));
            g2.fillRoundRect(10, 12, w - 20, h - 20, radius, radius);
        }

        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, w - 20, h - 20, radius, radius);

        g2.dispose();
        super.paintComponent(g);
    }

    @Override public Insets getInsets() {
        return new Insets(0, 0, 20, 20);
    }
}
