package emailandurl;

import javax.swing.*;
import java.awt.*;

public class ValidationUI {
    public static void showGUI() {
        JFrame frame = new JFrame("Validation Email / URL");
        frame.setSize(550, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Color fgColor = new Color(230, 230, 230);
        Color buttonColor = new Color(60, 63, 65);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(30, 30, 30));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel titleLabel = new JLabel("Saisir une URL ou un e-mail :");
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(fgColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField inputField = new JTextField();
        inputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        inputField.setBackground(buttonColor);
        inputField.setForeground(fgColor);
        inputField.setCaretColor(fgColor);

        JComboBox<String> typeBox = new JComboBox<>(new String[]{"-- Choisir un type --", "Email", "URL"});
        typeBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        typeBox.setBackground(buttonColor);
        typeBox.setForeground(fgColor);

        JButton validateButton = new JButton("Valider");
        validateButton.setBackground(new Color(22, 122, 10));
        validateButton.setForeground(fgColor);
        validateButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton clearButton = new JButton("Effacer");
        clearButton.setBackground(new Color(22, 118, 206));
        clearButton.setForeground(fgColor);
        clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel resultLabel = new JLabel(" ");
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        resultLabel.setForeground(fgColor);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Color bgColor2 = new Color(148, 147, 147);
        Font font = new Font("Segoe UI", Font.PLAIN, 15);
        UIManager.put("OptionPane.background", bgColor2);
        UIManager.put("Panel.background", bgColor2);
        UIManager.put("Button.background", buttonColor);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", font);
        UIManager.put("Label.font", font);
        UIManager.put("Label.foreground", Color.WHITE);

        validateButton.addActionListener(e -> {
            String input = inputField.getText().trim();
            String type = (String) typeBox.getSelectedItem();

            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Veuillez remplir le champ", "Champs incomplets", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if ("-- Choisir un type --".equals(type)) {
                JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un type (Email ou URL)", "Champs incomplets", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String[] result = "Email".equals(type)
                    ? EmailValidator.validate(input)
                    : URLValidator.validate(input);

            boolean isValid = Boolean.parseBoolean(result[0]);
            String reason = result[1];

            resultLabel.setText((isValid ? "Valide : " : "Invalide : ") + reason);
            resultLabel.setForeground(isValid ? new Color(102, 255, 102) : new Color(255, 102, 102));
        });

        clearButton.addActionListener(e -> {
            inputField.setText("");
            typeBox.setSelectedIndex(0);
            resultLabel.setText(" ");
            resultLabel.setForeground(fgColor);
        });

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(inputField);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(typeBox);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(validateButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(clearButton);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(resultLabel);

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }
}

