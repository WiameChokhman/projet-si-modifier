package passwords;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.List;
public class PasswordSaver {
    private static final String STORAGE_PATH = System.getProperty("user.home") + "/Downloads/passwords.csv";
    private static final String MASTER_PASSWORD = "admin123";
    public static void showProtectedViewer() {
        Color bgColor = new Color(96, 94, 94);
        Color buttonColor = new Color(60, 179, 113);
        Font font = new Font("Segoe UI", Font.BOLD, 14);

        JPanel passwordPanel = new JPanel(new BorderLayout(10, 10));
        passwordPanel.setBackground(bgColor);
        passwordPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel label = new JLabel("Entrez le mot de passe maître :");
        label.setFont(font);
        label.setForeground(Color.WHITE);
        passwordPanel.add(label, BorderLayout.NORTH);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(font);
        passwordPanel.add(passwordField, BorderLayout.CENTER);

        UIManager.put("OptionPane.background", bgColor);
        UIManager.put("Panel.background", bgColor);
        UIManager.put("Button.background", buttonColor);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", font);
        UIManager.put("Label.font", font);
        UIManager.put("Label.foreground", Color.WHITE);

        int option = JOptionPane.showConfirmDialog(null, passwordPanel, "Accès protégé", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            String inputPassword = new String(passwordField.getPassword());
            if (!inputPassword.equals(MASTER_PASSWORD)) {
                JOptionPane.showMessageDialog(null, "Mot de passe incorrect !", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            List<String[]> passwords = readPasswordsFromFile();
            if (passwords.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Aucun mot de passe enregistré.", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String[] columnNames = {"Intitulé", "Mot de Passe"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            for (String[] password : passwords) {
                model.addRow(password);
            }

            JTable table = new JTable(model);
            table.setFont(font);
            table.setBackground(bgColor);
            table.setForeground(Color.WHITE);
            table.setGridColor(Color.GRAY);
            table.setRowHeight(24);
            table.getTableHeader().setFont(font);
            table.getTableHeader().setBackground(buttonColor);
            table.getTableHeader().setForeground(Color.WHITE);
            ((DefaultTableCellRenderer) table.getDefaultRenderer(Object.class)).setHorizontalAlignment(SwingConstants.CENTER);

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(370, 200));
            scrollPane.getViewport().setBackground(bgColor);

            JPanel panel = new JPanel(new BorderLayout(10, 10));
            panel.setBackground(bgColor);
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            JLabel tableLabel = new JLabel("Voici les mots de passe enregistrés :");
            tableLabel.setFont(font);
            tableLabel.setForeground(Color.WHITE);
            panel.add(tableLabel, BorderLayout.NORTH);
            panel.add(scrollPane, BorderLayout.CENTER);

            String[] options = {"Télécharger", "Fermer"};
            int result = JOptionPane.showOptionDialog(null, panel, "Mots de passe sauvegardés",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);

            if (result == JOptionPane.YES_OPTION) {
                downloadFile();
            }
        }
    }
    private static List<String[]> readPasswordsFromFile() {
        List<String[]> passwords = new ArrayList<>();
        try {
            Path path = Path.of(STORAGE_PATH);
            if (Files.exists(path)) {
                List<String> lines = Files.readAllLines(path);
                for (String line : lines) {
                    String[] parts = line.split(",", 2);
                    if (parts.length == 2) {
                        passwords.add(parts);
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la lecture du fichier !", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        return passwords;
    }
    private static void downloadFile() {
        try {
            Path source = Path.of(STORAGE_PATH);
            Path target = Paths.get(System.getProperty("user.home"), "Downloads", "passwords_copy.csv");
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
            JOptionPane.showMessageDialog(null, "Fichier téléchargé :\n" + target, "Téléchargement réussi", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors du téléchargement du fichier.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}





