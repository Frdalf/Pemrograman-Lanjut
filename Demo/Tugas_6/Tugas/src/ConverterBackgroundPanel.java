import javax.swing.*;
import java.awt.*;

public class ConverterBackgroundPanel extends JPanel {
    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();

        GradientPaint gp = new GradientPaint(0, 0, UIColors.GRAD_A, w, 0, UIColors.GRAD_B);
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, (int)(h * 0.55));

        g2.setColor(new Color(245, 248, 250));
        g2.fillRect(0, (int)(h * 0.45), w, (int)(h * 0.55));

        g2.dispose();
    }
}
