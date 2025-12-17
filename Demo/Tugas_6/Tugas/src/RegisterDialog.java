import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegisterDialog extends JDialog {
    public RegisterDialog(JFrame parent, CSVUserStore store) {
        super(parent, "Buat Akun", true);
        setSize(460, 360);
        setLocationRelativeTo(parent);

        GradientBackgroundPanel root = new GradientBackgroundPanel();
        root.setLayout(new GridBagLayout());
        setContentPane(root);

        RoundedCardPanel card = new RoundedCardPanel(22, true);
        card.setPreferredSize(new Dimension(390, 290));
        card.setLayout(new GridBagLayout());
        card.setBorder(new EmptyBorder(18, 22, 18, 22));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.weightx = 1; c.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = UIFactory.title("Buat Akun");
        c.gridy = 0; c.insets = new Insets(6, 6, 14, 6);
        card.add(title, c);

        HintTextField user = new HintTextField("Username");
        HintPasswordField pass = new HintPasswordField("Password");
        HintPasswordField pass2 = new HintPasswordField("Konfirmasi password");

        c.gridy = 1; c.insets = new Insets(0, 6, 12, 6);
        card.add(UIFactory.roundedField(user), c);

        c.gridy = 2;
        card.add(UIFactory.roundedPasswordField(pass), c);

        c.gridy = 3;
        card.add(UIFactory.roundedPasswordField(pass2), c);

        GradientButton create = new GradientButton("DAFTAR");
        create.setPreferredSize(new Dimension(320, 46));
        create.setFont(UIFactory.fontBold(15f));
        c.gridy = 4; c.insets = new Insets(12, 6, 0, 6);
        card.add(create, c);

        create.addActionListener(e -> {
            String u = user.getText().trim();
            String p1 = new String(pass.getPassword());
            String p2 = new String(pass2.getPassword());

            if (u.isEmpty() || p1.isEmpty() || p2.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field wajib diisi.", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!p1.equals(p2)) {
                JOptionPane.showMessageDialog(this, "Konfirmasi password tidak sama.", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (store.register(u, p1)) {
                JOptionPane.showMessageDialog(this, "Akun berhasil dibuat. Silakan login.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username sudah dipakai.", "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        });

        root.add(card);
    }
}
