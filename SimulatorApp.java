import chiffrer_dechiff_texte.CipherSimulator;
import passwords.PasswordGenerator;
import passwords.PasswordSaver;
import passwords.PasswordStrengthChecker;

import javax.swing.*;
import java.awt.*;

public class SimulatorApp {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Cyber Security Simulator");
        frame.setIconImage(new ImageIcon("icons/cyber-security.png").getImage());
        frame.setSize(600, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Couleurs et polices
        Color bgColor = new Color(33, 33, 33);
        Color buttonColor = new Color(60, 179, 113);
        Font buttonFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font titleFont = new Font("Segoe UI", Font.BOLD, 13);

        // Créer les boutons
        JButton passwordButton = createIconButton("Vérifier mot de passe", "icons/security-lock.png", buttonColor, buttonFont);
        JButton generatorButton = createIconButton("Générer mots de passe", "icons/reset-password.png", buttonColor, buttonFont);
        JButton viewPasswordsButton = createIconButton("Voir mots de passe", "icons/list.png", buttonColor, buttonFont);
        JButton aesEncryptButton = createIconButton("Chiffrement AES (fichier)", "icons/data-encryption.png", buttonColor, buttonFont);
        JButton aesDecryptButton = createIconButton("Déchiffre AES (fichier)", "icons/decryption.png", buttonColor, buttonFont);
        JButton cipherButton = createIconButton("Chiffrer / Déchiffrer (texte)", "icons/cryptographic.png", buttonColor, buttonFont);
        JButton validatorButton = createIconButton("Valider Email / URL", "icons/valid.png", buttonColor, buttonFont);

        // Actions
        passwordButton.addActionListener(e -> PasswordStrengthChecker.showGUI());
        generatorButton.addActionListener(e -> PasswordGenerator.showGUI());
        viewPasswordsButton.addActionListener(e -> PasswordSaver.showProtectedViewer());
        aesEncryptButton.addActionListener(e -> SecureFileEncryptor.showGUI());
        aesDecryptButton.addActionListener(e -> SecureFileDecryptor.showGUI());
        cipherButton.addActionListener(e -> chiffrer_dechiff_texte.CipherSimulator.showGUI());
        validatorButton.addActionListener(e -> emailandurl.ValidationUI.showGUI());


        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(bgColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Sections
        mainPanel.add(createSectionTitle("Gestion des mots de passe ", titleFont));
        mainPanel.add(createButtonRow(passwordButton));
        mainPanel.add(createButtonRow(generatorButton));
        mainPanel.add(createButtonRow(viewPasswordsButton));

        mainPanel.add(createSectionTitle("Chiffrement ", titleFont));
        mainPanel.add(createButtonRow(aesEncryptButton));
        mainPanel.add(createButtonRow(aesDecryptButton));
        mainPanel.add(createButtonRow(cipherButton));

        mainPanel.add(createSectionTitle("Validation", titleFont));
        mainPanel.add(createButtonRow(validatorButton));

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // Création des boutons avec petite taille
    private static JButton createIconButton(String text, String iconPath, Color bgColor, Font font) {
        ImageIcon icon = new ImageIcon(iconPath);
        Image img = icon.getImage().getScaledInstance(25, 25,Image.SCALE_SMOOTH); // plus petit
        icon = new ImageIcon(img);

        JButton button = new JButton(text, icon);
        button.setFont(font);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(8);
        button.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 33));//aille réduite
        return button;
    }

    // Titre aligné à gauche
    private static JLabel createSectionTitle(String title, Font font) {
        JLabel label = new JLabel(title);
        label.setFont(font);
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(8, 0, 4, 0));
        return label;
    }

    // Une ligne de bouton alignée à gauche
    private static JPanel createButtonRow(JButton button) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setBackground(new Color(33, 33, 33));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.add(button);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        return row;
    }
}




