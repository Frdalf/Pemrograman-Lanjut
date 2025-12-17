import javax.swing.*;
import java.awt.*;

public class HintPasswordField extends JPasswordField {
    private final String hint;

    public HintPasswordField(String hint) {
        this.hint = hint;
        setBorder(null);
        setOpaque(false);
        setFont(UIFactory.fontPlain(14f));
        setEchoChar('•');
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getPassword().length == 0 && !isFocusOwner()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(150, 150, 150));
            g2.setFont(getFont());
            Insets in = getInsets();
            g2.drawString(hint, in.left + 2, getHeight() / 2 + g2.getFontMetrics().getAscent() / 2 - 2);
            g2.dispose();
        }
    }
}
