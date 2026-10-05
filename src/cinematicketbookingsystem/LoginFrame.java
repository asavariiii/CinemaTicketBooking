package cinematicketbookingsystem;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {

        setTitle("CinéBook - Login");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(20, 20, 25));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(50, 70, 40, 70)
        );

        JLabel logo = new JLabel("🎬 CINÉBOOK");
        logo.setFont(new Font("SansSerif", Font.BOLD, 30));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Welcome Back");
        title.setFont(new Font("SansSerif", Font.BOLD, 25));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "Login to continue booking your movie"
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(Color.LIGHT_GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(
                new Font("SansSerif", Font.BOLD, 14)
        );
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        usernameField = new JTextField();
        usernameField.setMaximumSize(
                new Dimension(350, 40)
        );
        usernameField.setFont(
                new Font("SansSerif", Font.PLAIN, 15)
        );

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(
                new Font("SansSerif", Font.BOLD, 14)
        );
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        passwordField = new JPasswordField();
        passwordField.setMaximumSize(
                new Dimension(350, 40)
        );
        passwordField.setFont(
                new Font("SansSerif", Font.PLAIN, 15)
        );

        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(
                new Font("SansSerif", Font.BOLD, 15)
        );
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(
                new Color(120, 60, 180)
        );
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setMaximumSize(
                new Dimension(350, 45)
        );
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton registerButton =
                new JButton("Create New Account");

        registerButton.setFont(
                new Font("SansSerif", Font.PLAIN, 13)
        );
        registerButton.setForeground(Color.LIGHT_GRAY);
        registerButton.setBackground(
                new Color(20, 20, 25)
        );
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        registerButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        loginButton.addActionListener(e -> login());

        registerButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration screen will be added next!",
                    "CinéBook",
                    JOptionPane.INFORMATION_MESSAGE
            );

        });

        mainPanel.add(logo);
        mainPanel.add(Box.createVerticalStrut(25));

        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(8));
        mainPanel.add(subtitle);

        mainPanel.add(Box.createVerticalStrut(35));

        mainPanel.add(usernameLabel);
        mainPanel.add(Box.createVerticalStrut(8));
        mainPanel.add(usernameField);

        mainPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(passwordLabel);
        mainPanel.add(Box.createVerticalStrut(8));
        mainPanel.add(passwordField);

        mainPanel.add(Box.createVerticalStrut(30));

        mainPanel.add(loginButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(registerButton);

        add(mainPanel);
    }

    private void login() {

        String username =
                usernameField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter both username and password.",
                    "Login Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Temporary login
        if (username.equals("admin")
                && password.equals("123")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login successful!",
                    "Welcome to CinéBook",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}