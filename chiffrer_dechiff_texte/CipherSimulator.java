package chiffrer_dechiff_texte;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CipherSimulator {

    public static void showGUI() {
        CipherUI cipherUI = new CipherUI();  // Créer l'interface utilisateur
        CipherActions cipherActions = new CipherActions(cipherUI);  // Créer les actions liées aux boutons

        cipherUI.setupUI(cipherActions);
    }
}













