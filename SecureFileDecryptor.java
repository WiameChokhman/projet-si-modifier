import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class SecureFileDecryptor {

    public static void showGUI() {
        JFrame frame = new JFrame("Déchiffrement de fichier AES");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Centrer la fenêtre

        // Définir l’icône de la fenêtre
        frame.setIconImage(new ImageIcon("icons/aes_decrypt.png").getImage());

        // Couleurs et styles
        Color background = new Color(33, 33, 33);
        Color textColor = Color.WHITE;
        Color buttonColor = new Color(220, 20, 60);
        Font font = new Font("Segoe UI", Font.PLAIN, 16);

        // Création des composants
        JLabel passwordLabel = new JLabel("Mot de passe :");
        passwordLabel.setForeground(textColor);
        passwordLabel.setFont(font);

        JTextField passwordField = new JTextField(20);
        passwordField.setFont(font);

        JButton selectButton = new JButton("Selectionner un fichier");
        selectButton.setFont(font);
        selectButton.setBackground(buttonColor);
        selectButton.setForeground(Color.WHITE);
        selectButton.setFocusPainted(false);
        selectButton.setPreferredSize(new Dimension(220, 35));

        Color bgColor2 = new Color(148, 147, 147);
        UIManager.put("OptionPane.background", bgColor2);
        UIManager.put("Panel.background", bgColor2);
        UIManager.put("Button.background", buttonColor);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", font);
        UIManager.put("Label.font", font);
        UIManager.put("Label.foreground", Color.WHITE);

        // Panneau principal
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(background);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(selectButton, gbc);

        // Action bouton
        selectButton.addActionListener(e -> {
            String password = passwordField.getText();
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Veuillez entrer un mot de passe.");
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    decryptFile(selectedFile, password);
                    JOptionPane.showMessageDialog(frame, "Fichier déchiffré avec succès.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur : " + ex.getMessage());
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void decryptFile(File inputFile, String password) throws Exception {
        byte[] fileContent = Files.readAllBytes(inputFile.toPath());

        byte[] salt = Arrays.copyOfRange(fileContent, 0, 16);
        byte[] iv = Arrays.copyOfRange(fileContent, 16, 32);
        byte[] encryptedData = Arrays.copyOfRange(fileContent, 32, fileContent.length);

        SecretKeySpec key = getKeyFromPassword(password, salt);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] decrypted = cipher.doFinal(encryptedData);

        String originalName = inputFile.getName().replace(".aes", ".decrypted");
        String userHome = System.getProperty("user.home");
        Path outPath = Paths.get(userHome, "Downloads", originalName);
        Files.write(outPath, decrypted);
    }

    private static SecretKeySpec getKeyFromPassword(String password, byte[] salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }
}



