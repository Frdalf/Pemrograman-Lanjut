import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegisterDialog extends JDialog {
    public RegisterDialog(JFrame parent, CSVUserStore store) {
        super(parent, "Buat Akun", true);

        setSize(460, 430);
        setLocationRelativeTo(parent);

        GradientBackgroundPanel root = new GradientBackgroundPanel();
        root.setLayout(new GridBagLayout());
        setContentPane(root);

        RoundedCardPanel card = new RoundedCardPanel(22, true);
        card.setPreferredSize(new Dimension(390, 350));
        card.setLayout(new GridBagLayout());
        card.setBorder(new EmptyBorder(18, 22, 18, 22));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = UIFactory.title("Buat Akun");
        c.gridy = 0; c.insets = new Insets(6, 6, 14, 6);
        card.add(title, c);

        HintTextField user = new HintTextField("Username");
        HintPasswordField pass = new HintPasswordField("Password");
        HintPasswordField pass2 = new HintPasswordField("Konfirmasi password");

        JPanel userWrap  = UIFactory.roundedField(user);
        JPanel passWrap  = UIFactory.roundedPasswordField(pass);
        JPanel pass2Wrap = UIFactory.roundedPasswordField(pass2);

        Dimension fieldSize = new Dimension(390, 44);
        userWrap.setPreferredSize(fieldSize);
        userWrap.setMinimumSize(fieldSize);
        userWrap.setMaximumSize(fieldSize);

        passWrap.setPreferredSize(fieldSize);
        passWrap.setMinimumSize(fieldSize);
        passWrap.setMaximumSize(fieldSize);

        pass2Wrap.setPreferredSize(fieldSize);
        pass2Wrap.setMinimumSize(fieldSize);
        pass2Wrap.setMaximumSize(fieldSize);

        c.gridy = 1; c.insets = new Insets(0, 6, 12, 6);
        card.add(userWrap, c);

        c.gridy = 2; c.insets = new Insets(0, 6, 12, 6);
        card.add(passWrap, c);

        c.gridy = 3; c.insets = new Insets(0, 6, 14, 6);
        card.add(pass2Wrap, c);

        GradientButton create = new GradientButton("DAFTAR");
        create.setPreferredSize(new Dimension(320, 46));
        create.setMinimumSize(new Dimension(320, 46));
        create.setFont(UIFactory.fontBold(15f));
        c.gridy = 4; c.insets = new Insets(0, 6, 0, 6);
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
