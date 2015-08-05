/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package puissance4cs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Staik
 */
public class Client extends Jeu {

    public static Joueur joueur = new Joueur("client", Jeton.Couleur.JAUNE);
    private String host;
    private int port;

    @Override
    public void start() {
        try {
            Socket serverSocket = new Socket(host, port);
            System.out.println("Connected to game server");
            final ObjectOutputStream outToServer = new ObjectOutputStream(serverSocket.getOutputStream());
            final ObjectInputStream inFromServer = new ObjectInputStream(serverSocket.getInputStream());
            prepare(outToServer, inFromServer);

            canevas.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        if(etat == EtatDuJeu.PARTIE_EN_COURS){
                        if (currentPlayer == joueur) {
                            // gestionnaire du clic de souris
                            int mouseX = e.getX();
                            // Obtient la ligne et la colonne cliquée
                            int colonneSelectionne = mouseX / Canevas.TAILLE_CASE;
                            if (colonneSelectionne >= 0 && colonneSelectionne < Canevas.COLONNES) {
                                // Cherche une cellule vide à partir de la rangée du bas
                                for (int ligne = Canevas.LIGNES - 1; ligne >= 0; ligne--) {
                                    if (canevas.grille[ligne][colonneSelectionne].getCouleur() == Jeton.Couleur.VIDE) {
                                        canevas.grille[ligne][colonneSelectionne].setCouleur(joueur.getCouleur()); // jouer un coup
                                        break;
                                    }
                                }
                            }
                            outToServer.writeObject("grille");
                            outToServer.writeObject(canevas.grille);
                            
                            currentPlayer = Server.joueur;
                            miseAjour(new Jeton(currentPlayer.getCouleur())); // mise à jour de la grille du jeu
                        }
                        }else{
                            etat = EtatDuJeu.PARTIE_EN_COURS;
                        }
                        repaint();
                    } catch (IOException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            while (true) {
                String cmd = (String) inFromServer.readObject();
                switch (cmd) {
                    case "grille":
                        currentPlayer = joueur;
                        canevas.grille = (Jeton[][]) inFromServer.readObject();
                        repaint();
                        break;
                }
                miseAjour(new Jeton(currentPlayer.getCouleur())); // mise à jour de la grille du jeu
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Client(String host, int port) {
        super();
        this.host = host;
        this.port = port;
        pack();  // emballe tout les composants du JFrame
        setTitle("Puissance Quatre 2 Joueurs (Client)");
        setVisible(true);  // montrer ce JFrame
    }

    private void prepare(ObjectOutputStream outToServer, ObjectInputStream inFromServer) {
        try {
            outToServer.writeObject("Hello");
            outToServer.writeObject(joueur.getName());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
