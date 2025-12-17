import javax.swing.*;
import java.awt.*;

public class GradientBackgroundPanel extends JPanel {
    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, UIColors.GRAD_A, w, h, UIColors.GRAD_B);
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);

        g2.dispose();
    }
}
