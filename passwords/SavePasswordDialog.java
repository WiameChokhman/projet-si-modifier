package passwords;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.List;

public class SavePasswordDialog {

    public static void showSaveDialog(String password) {
        JFrame frame = new JFrame("Sauvegarde du mot de passe");
        frame.setSize(450, 250);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon("icons/save.png").getImage());

        // Couleurs
        Color bgColor = new Color(33, 33, 33);
        Color textColor = Color.WHITE;
        Color buttonColor = new Color(76, 175, 80);
        Font font = new Font("Segoe UI", Font.PLAIN, 14);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(bgColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelLabel = new JLabel("Intitulé :");
        labelLabel.setForeground(textColor);
        labelLabel.setFont(font);

        JTextField labelField = new JTextField(20);
        labelField.setFont(font);

        JButton saveButton = new JButton("Sauvegarder");
        saveButton.setBackground(buttonColor);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(font);
        saveButton.setFocusPainted(false);

        // Ajout des composants
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(labelLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(labelField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(saveButton, gbc);

        saveButton.addActionListener(e -> {
            String label = labelField.getText().trim();
            try {
                if (label.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Veuillez saisir un intitulé.");
                    return;
                }

                if (labelExists(label)) {
                    JOptionPane.showMessageDialog(frame, "Cet intitulé existe déjà, veuillez en choisir un autre.");
                    return;
                }

                savePassword(label, password);
                JOptionPane.showMessageDialog(frame, "Mot de passe sauvegardé avec succès !");
                frame.dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Erreur : " + ex.getMessage());
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private static boolean labelExists(String label) throws IOException {
        Path filePath = Paths.get(System.getProperty("user.home"), "Downloads", "passwords.csv");

        if (!Files.exists(filePath)) return false;

        List<String> lines = Files.readAllLines(filePath);
        for (String line : lines) {
            String[] parts = line.split(",", 2);
            if (parts.length == 2 && parts[0].equalsIgnoreCase(label)) {
                return true;
            }
        }
        return false;
    }

    private static void savePassword(String label, String password) throws Exception {
        Path filePath = Paths.get(System.getProperty("user.home"), "Downloads", "passwords.csv");

        Map<String, String> entries = new LinkedHashMap<>();

        if (Files.exists(filePath)) {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    entries.put(parts[0], parts[1]);
                }
            }
        }

        if (entries.containsKey(label)) {
            throw new Exception("Cet intitulé existe déjà !");
        }

        entries.put(label, password);

        List<String> newLines = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            newLines.add(entry.getKey() + "," + entry.getValue());
        }

        Files.write(filePath, newLines);
    }
}


