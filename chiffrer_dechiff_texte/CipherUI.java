package chiffrer_dechiff_texte;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;

public class CipherUI {
    JFrame frame;
    private JTextArea inputArea;
    private JTextArea resultArea;
    private JTextField keyField;
    private JComboBox<String> algoBox;
    private JLabel keyLabel;

    public void setupUI(ActionListener action) {
        frame = new JFrame("Encrypt/Decrypt");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        Color bgColor = new Color(33, 33, 33);
        Color bgColor2 = new Color(148, 147, 147);
        Color fgColor = Color.WHITE;
        Color buttonColor = new Color(60, 179, 113);
        Font font = new Font("Segoe UI", Font.PLAIN, 14);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(bgColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        // Composants UI
        JLabel inputLabel = new JLabel("Texte à traiter:");
        inputLabel.setForeground(fgColor);
        inputArea = new JTextArea(4, 30);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputArea.setFont(font);
        JScrollPane inputScroll = new JScrollPane(inputArea);

        JLabel algoLabel = new JLabel("Algorithme:");
        algoLabel.setForeground(fgColor);
        algoBox = new JComboBox<>(new String[]{"-- Choisir un algorithme --", "Caesar", "ROT13", "Vigenère"});
        algoBox.setFont(font);

        keyLabel = new JLabel("Clé:");
        keyLabel.setForeground(fgColor);
        keyField = new JTextField(20);
        keyField.setFont(font);
        keyField.setVisible(false);  // Masquer le champ de la clé par défaut
        keyLabel.setVisible(false);

        UIManager.put("OptionPane.background", bgColor2);
        UIManager.put("Panel.background", bgColor2);
        UIManager.put("Button.background", buttonColor);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", font);
        UIManager.put("Label.font", font);
        UIManager.put("Label.foreground", Color.WHITE);

        JButton encryptButton = new JButton("Chiffrer");
        JButton decryptButton = new JButton("Déchiffrer");
        JButton clearButton = new JButton("Effacer");
        JButton copyButton = new JButton("Copier");

        Dimension buttonSize = new Dimension(130, 35);
        JButton[] buttons = {encryptButton, decryptButton, clearButton, copyButton};
        for (JButton b : buttons) {
            b.setPreferredSize(buttonSize);
            b.setFont(font);
            b.setForeground(fgColor);
        }
        encryptButton.setBackground(buttonColor);
        decryptButton.setBackground(buttonColor);
        clearButton.setBackground(Color.RED);
        copyButton.setBackground(new Color(70, 130, 180));

        resultArea = new JTextArea(6, 30);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        resultArea.setBackground(Color.DARK_GRAY);
        resultArea.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Placement
        gbc.gridy = 0; panel.add(inputLabel, gbc);
        gbc.gridx = 1; panel.add(inputScroll, gbc);
        gbc.gridx = 0; gbc.gridy++; panel.add(algoLabel, gbc);
        gbc.gridx = 1; panel.add(algoBox, gbc);
        gbc.gridx = 0; gbc.gridy++; panel.add(keyLabel, gbc);
        gbc.gridx = 1; panel.add(keyField, gbc);
        gbc.gridx = 0; gbc.gridy++; panel.add(encryptButton, gbc);
        gbc.gridx = 1; panel.add(decryptButton, gbc);
        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        panel.add(scrollPane, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(bgColor);
        buttonPanel.add(copyButton);
        buttonPanel.add(clearButton);
        gbc.gridy++; panel.add(buttonPanel, gbc);

        frame.add(panel);
        frame.setVisible(true);

        // Configurer les actions
        encryptButton.addActionListener(action);
        decryptButton.addActionListener(action);
        clearButton.addActionListener(e -> {
            inputArea.setText("");
            keyField.setText("");
            resultArea.setText("");
            algoBox.setSelectedIndex(0);
        });
        copyButton.addActionListener(e -> {
            String fullText = resultArea.getText();
            String toCopy = fullText.replaceFirst("Résultat du (chiffrement|déchiffrement) *:\\n?", "").trim();
            if (!toCopy.isEmpty()) {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(toCopy), null);
                JOptionPane.showMessageDialog(null, "Résultat copié dans le presse-papiers.");
            } else {
                JOptionPane.showMessageDialog(null, "Aucun texte à copier.");
            }
        });

        // Ajouter un ActionListener pour gérer la visibilité du champ de clé
        algoBox.addActionListener(e -> {
            String selectedAlgo = (String) algoBox.getSelectedItem();
            boolean needsKey = !"ROT13".equals(selectedAlgo) && !"-- Choisir un algorithme --".equals(selectedAlgo);
            keyField.setVisible(needsKey);
            keyLabel.setVisible(needsKey);
        });
    }

    public JTextArea getInputArea() {
        return inputArea;
    }

    public JTextArea getResultArea() {
        return resultArea;
    }

    public JTextField getKeyField() {
        return keyField;
    }

    public JComboBox<String> getAlgoBox() {
        return algoBox;
    }
}




