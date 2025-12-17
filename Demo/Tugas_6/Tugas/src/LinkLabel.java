import javax.swing.*;
import java.awt.*;

public class LinkLabel extends JLabel {
    public LinkLabel(String text) {
        super("<html><u>" + text + "</u></html>");
        setForeground(UIColors.LINK);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
