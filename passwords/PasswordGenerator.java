package passwords;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class PasswordGenerator {

    public static void showGUI() {
        JFrame frame = new JFrame("Générateur de mot de passe");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Centrer la fenêtre
        frame.setIconImage(new ImageIcon("icons/reset-password.png").getImage());

        // Couleurs et polices
        Color bgColor = new Color(33, 33, 33);
        Color textColor = Color.WHITE;
        Color buttonColor = new Color(76, 175, 80);
        Font font = new Font("Segoe UI", Font.PLAIN, 15);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(bgColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Champ de mot de passe
        JTextField passwordField = new JTextField(20);
        passwordField.setEditable(false);
        passwordField.setFont(font);

        JLabel lengthLabel = new JLabel("Longueur du mot de passe :");
        lengthLabel.setFont(font);
        lengthLabel.setForeground(textColor);

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(8, 8, 32, 1);
        JSpinner lengthSpinner = new JSpinner(spinnerModel);
        lengthSpinner.setFont(font);

        JButton generateButton = new JButton("Générer");
        generateButton.setBackground(buttonColor);
        generateButton.setForeground(Color.WHITE);
        generateButton.setFont(font);
        generateButton.setFocusPainted(false);

        JButton saveButton = new JButton("Sauvegarder");
        saveButton.setBackground(new Color(70, 130, 180));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(font);
        saveButton.setFocusPainted(false);

        // Placement des composants
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(passwordField, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        panel.add(lengthLabel, gbc);

        gbc.gridx = 1;
        panel.add(lengthSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(generateButton, gbc);

        gbc.gridx = 1;
        panel.add(saveButton, gbc);

        Color bgColor2 = new Color(148, 147, 147);
        UIManager.put("OptionPane.background", bgColor2);
        UIManager.put("Panel.background", bgColor2);
        UIManager.put("Button.background", buttonColor);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", font);
        UIManager.put("Label.font", font);
        UIManager.put("Label.foreground", Color.WHITE);
        // Actions
        generateButton.addActionListener((ActionEvent e) -> {
            int length = (int) lengthSpinner.getValue();
            String password = generatePassword(length);
            passwordField.setText(password);
        });

        saveButton.addActionListener((ActionEvent e) -> {
            String password = passwordField.getText();
            if (!password.isEmpty()) {
                SavePasswordDialog.showSaveDialog(password);
            } else {
                JOptionPane.showMessageDialog(frame, "Générez un mot de passe d'abord !");
            }
        });

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    private static String generatePassword(int length) {
        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String special = "!@#$%^&*()-_+=<>?";

        String allChars = upperCase + lowerCase + numbers + special;
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        return password.toString();
    }
}



