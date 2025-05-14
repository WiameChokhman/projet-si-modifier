import javax.crypto.*;
import javax.crypto.spec.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.security.spec.KeySpec;

public class SecureFileEncryptor {

    public static void showGUI() {
        JFrame frame = new JFrame("Chiffrement de fichier AES");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Centrer la fenêtre

        // Définir l’icône de la fenêtre
        frame.setIconImage(new ImageIcon("icons/aes_encrypt.png").getImage());

        // Couleurs et polices
        Color background = new Color(33, 33, 33);
        Color textColor = Color.WHITE;
        Color buttonColor = new Color(60, 179, 113);
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
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
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
                JOptionPane.showMessageDialog(frame, "Veuillez entrer un mot de passe ");
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    encryptFile(selectedFile, password);
                    JOptionPane.showMessageDialog(frame, "Fichier chiffré avec succès.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur : " + ex.getMessage());
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void encryptFile(File inputFile, String password) throws Exception {
        byte[] fileData = Files.readAllBytes(inputFile.toPath());

        byte[] salt = generateSalt();
        SecretKeySpec key = getKeyFromPassword(password, salt);

        byte[] iv = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] encrypted = cipher.doFinal(fileData);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(salt);
        outputStream.write(iv);
        outputStream.write(encrypted);

        String userHome = System.getProperty("user.home");
        Path outPath = Paths.get(userHome, "Downloads", inputFile.getName() + ".aes");
        Files.write(outPath, outputStream.toByteArray());
    }

    private static byte[] generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    private static SecretKeySpec getKeyFromPassword(String password, byte[] salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }
}


