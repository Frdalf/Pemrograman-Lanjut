import javax.swing.*;
import java.awt.*;

public class RoundedOutlinePanel extends JPanel {
    private final int radius;

    public RoundedOutlinePanel(int radius) {
        this.radius = radius;
        setOpaque(false);
    }

    @Override protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();

        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, w - 1, h - 1, radius, radius);

        g2.setColor(UIColors.OUTLINE);
        g2.setStroke(new BasicStroke(2f));
        g2.drawRoundRect(0, 0, w - 1, h - 1, radius, radius);

        g2.dispose();
        super.paintComponent(g);
    }
}
