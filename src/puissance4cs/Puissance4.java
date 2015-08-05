/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package puissance4cs;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.*;

/**
 *
 * @author Staik
 */
public class Puissance4 extends JFrame {

    private static Jeu jeu;


    public static void main(String[] args){
        new Puissance4();
    }
    public static void launch(String type, String host, int port) {
        // le Code de l'interface graphique s'exécute dans le thread de distribution des événements pour la sécurité des threads
        if (type.equals("server")) {
            jeu = new Server(port);
        } else {
            jeu = new Client(host, port);
        }
        SwingWorker sw = new SwingWorker() {

            @Override
            protected Object doInBackground() throws Exception {
                jeu.start();
                return null;
            }

        };
        sw.execute();

    }
    
    public Puissance4(){
        final Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(new OptionPanel(), BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
