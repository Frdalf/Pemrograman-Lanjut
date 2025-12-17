import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private final CSVUserStore store;

    public LoginFrame(CSVUserStore store) {
        this.store = store;

        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 560);
        setLocationRelativeTo(null);

        GradientBackgroundPanel root = new GradientBackgroundPanel();
        root.setLayout(new GridBagLayout());
        setContentPane(root);

        RoundedCardPanel card = new RoundedCardPanel(24, true);
        card.setPreferredSize(new Dimension(420, 390));
        card.setLayout(new GridBagLayout());
        card.setBorder(new EmptyBorder(22, 28, 22, 28));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = UIFactory.title("Login");
        c.gridy = 0; c.insets = new Insets(6, 6, 18, 6);
        card.add(title, c);

        JLabel userLbl = UIFactory.label("Username");
        c.gridy = 1; c.insets = new Insets(0, 6, 6, 6);
        card.add(userLbl, c);

        HintTextField username = new HintTextField("Masukkan username anda");
        JPanel userWrap = UIFactory.roundedField(username);
        c.gridy = 2; c.insets = new Insets(0, 6, 14, 6);
        card.add(userWrap, c);

        JLabel passLbl = UIFactory.label("Password");
        c.gridy = 3; c.insets = new Insets(0, 6, 6, 6);
        card.add(passLbl, c);

        HintPasswordField password = new HintPasswordField("Masukkan password anda");
        JPanel passWrap = UIFactory.roundedPasswordField(password);
        c.gridy = 4; c.insets = new Insets(0, 6, 18, 6);
        card.add(passWrap, c);

        GradientButton loginBtn = new GradientButton("LOGIN");
        loginBtn.setPreferredSize(new Dimension(320, 48));
        loginBtn.setFont(UIFactory.fontBold(16f));
        c.gridy = 5; c.insets = new Insets(0, 6, 10, 6);
        card.add(loginBtn, c);

        LinkLabel forgot = new LinkLabel("Lupa Password?");
        forgot.setHorizontalAlignment(SwingConstants.CENTER);
        c.gridy = 6; c.insets = new Insets(4, 6, 6, 6);
        card.add(forgot, c);

        LinkLabel register = new LinkLabel("Buat Akun");
        register.setHorizontalAlignment(SwingConstants.CENTER);
        c.gridy = 7; c.insets = new Insets(0, 6, 2, 6);
        card.add(register, c);

        // Actions
        ActionListener doLogin = e -> {
            String u = username.getText().trim();
            String p = new String(password.getPassword());

            if (u.isEmpty() || p.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username & password wajib diisi.", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (store.validate(u, p)) {
                new ConverterFrame(store, u).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Login gagal. Username atau password salah.", "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        };

        loginBtn.addActionListener(doLogin);
        username.addActionListener(doLogin);
        password.addActionListener(doLogin);

        register.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                new RegisterDialog(LoginFrame.this, store).setVisible(true);
            }
        });

        forgot.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                new ResetPasswordDialog(LoginFrame.this, store).setVisible(true);
            }
        });

        root.add(card);
    }
}
