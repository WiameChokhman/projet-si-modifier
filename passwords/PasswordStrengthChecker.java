package passwords;// ... imports inchangés
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.regex.Pattern;
public class PasswordStrengthChecker {
    private static PasswordFeedback currentFeedback; // Pour suivre la dernière évaluation
    public static void showGUI() {
        JFrame frame = new JFrame("Vérifier mot de passe");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon("icons/security-lock.png").getImage());
        // Styles
        Color bgColor = new Color(33, 33, 33);
        Color textColor = Color.WHITE;
        Color buttonColor = new Color(76, 175, 80);
        Font font = new Font("Segoe UI", Font.PLAIN, 16);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(bgColor);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("Entrez votre mot de passe :");
        label.setForeground(textColor);
        label.setFont(font);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField passwordField = new JTextField(20);
        passwordField.setFont(font);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JTextArea resultArea = new JTextArea(6, 30);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Analyse"));

        JButton saveButton = new JButton("Sauvegarder");
        saveButton.setFont(font);
        saveButton.setBackground(buttonColor);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        Color bgColor2 = new Color(148, 147, 147);
        UIManager.put("OptionPane.background", bgColor2);
        UIManager.put("Panel.background", bgColor2);
        UIManager.put("Button.background", buttonColor);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", font);
        UIManager.put("Label.font", font);
        UIManager.put("Label.foreground", Color.WHITE);

        // Action du bouton sauvegarder avec vérification de force
        saveButton.addActionListener(e -> {
            String password = passwordField.getText();
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Veuillez entrer un mot de passe.");
                return;
            }
            PasswordFeedback feedback = checkStrength(password);
            String message = feedback.message.toLowerCase();
            if (message.contains("très fort") || message.contains("fort")) {
                SavePasswordDialog.showSaveDialog(password);
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Veuillez renforcer votre mot de passe avant de le sauvegarder.",
                        "Mot de passe trop faible",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        // Mise à jour dynamique de la force
        passwordField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updateStrength(); }
            public void removeUpdate(DocumentEvent e) { updateStrength(); }
            public void changedUpdate(DocumentEvent e) { updateStrength(); }
            private void updateStrength() {
                String password = passwordField.getText();
                currentFeedback = checkStrength(password);
                resultArea.setText(currentFeedback.message);
                resultArea.setForeground(currentFeedback.color);
                passwordField.setBorder(BorderFactory.createLineBorder(currentFeedback.color, 4));
            }
        });
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(saveButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(scrollPane);

        frame.setContentPane(panel);
        frame.setVisible(true);
    }
    static class PasswordFeedback {
        String message;
        Color color;
        PasswordFeedback(String message, Color color) {
            this.message = message;
            this.color = color;
        }
    }
    public static PasswordFeedback checkStrength(String password) {
        StringBuilder feedback = new StringBuilder();
        int length = password.length();
        boolean hasUpper = Pattern.compile("[A-Z]").matcher(password).find();
        boolean hasLower = Pattern.compile("[a-z]").matcher(password).find();
        boolean hasDigit = Pattern.compile("[0-9]").matcher(password).find();
        boolean hasSpecial = Pattern.compile("[^a-zA-Z0-9]").matcher(password).find();
        boolean hasRepeat = Pattern.compile("(.)\\1{2,}").matcher(password).find();

        int score = 0;
        if (length >= 8) score++;
        else feedback.append("Trop court (min 8 caractères)\n");
        if (hasUpper) score++; else feedback.append("Ajoutez des MAJUSCULES\n");
        if (hasLower) score++; else feedback.append("Ajoutez des minuscules\n");
        if (hasDigit) score++; else feedback.append("Ajoutez des chiffres\n");
        if (hasSpecial) score++; else feedback.append("Ajoutez un caractère spécial (!, @, #...)\n");
        if (!hasRepeat) score++; else feedback.append("Évitez les répétitions (aaa, 111...)\n");

        String strength;
        Color color;
        switch (score) {
            case 6:
                strength = "Très Fort";
                color = new Color(0, 128, 0);
                break;
            case 5:
                strength = "Fort";
                color = new Color(42, 190, 42);
                break;
            case 4:
                strength = "Modéré";
                color = new Color(248, 174, 56);
                break;
            case 3:
                strength = "Faible";
                color = new Color(255, 69, 0);
                break;
            default:
                strength = "Très faible";
                color = Color.RED;
        }

        feedback.insert(0, "Force du mot de passe : " + strength + "\n\n");
        return new PasswordFeedback(feedback.toString(), color);
    }
}





