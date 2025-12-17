import javax.swing.*;

public class AppMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}

            CSVUserStore store = new CSVUserStore("users.csv");
            store.ensureDefaultUser();

            new LoginFrame(store).setVisible(true);
        });
    }
}
