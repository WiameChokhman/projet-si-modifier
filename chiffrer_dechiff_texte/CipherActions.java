package chiffrer_dechiff_texte;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class CipherActions implements ActionListener {
    private CipherUI cipherUI;
    public CipherActions(CipherUI cipherUI) {
        this.cipherUI = cipherUI;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String text = cipherUI.getInputArea().getText().trim();
        String algo = (String) cipherUI.getAlgoBox().getSelectedItem();
        String key = cipherUI.getKeyField().getText();

        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(cipherUI.frame, "Veuillez entrer du texte à traiter.");
            return;
        }

        if ("-- Choisir un algorithme --".equals(algo)) {
            JOptionPane.showMessageDialog(cipherUI.frame, "Veuillez sélectionner un algorithme.");
            return;
        }

        if (!CipherUtils.validateKey(algo, key)) {
            JOptionPane.showMessageDialog(cipherUI.frame, "Veuillez remplir correctement la clé pour l'algo " + algo);
            return;
        }

        boolean isEncrypt = (e.getSource().toString().contains("Chiffrer"));
        String resultText = isEncrypt
                ? CipherUtils.encrypt(text, algo, key)
                : CipherUtils.decrypt(text, algo, key);

        cipherUI.getResultArea().setText("Résultat du " + (isEncrypt ? "chiffrement" : "déchiffrement") + " :\n\n" + resultText);
    }
}



