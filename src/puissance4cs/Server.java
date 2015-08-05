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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Staik
 */
public class Server extends Jeu {

    public static final Joueur joueur = new Joueur("server", Jeton.Couleur.ROUGE);
    private int port;

    @Override
    public void start() {
        try {
            ServerSocket socket = new ServerSocket(port);
            System.out.println("Waiting for a player...");
            Socket clientSocket = socket.accept();
            System.out.println("A player is connected");
            final ObjectOutputStream outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
            final ObjectInputStream inFromClient = new ObjectInputStream(clientSocket.getInputStream());
            canevas.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int mouseX = e.getX();
                    int colonneSelectionne = mouseX / Canevas.TAILLE_CASE;

                    // Obtient la ligne et la colonne cliquée
                    if (etat == EtatDuJeu.PARTIE_EN_COURS) {
                        if (currentPlayer == joueur) {
                            // gestionnaire du clic de souris

                            if (colonneSelectionne >= 0 && colonneSelectionne < Canevas.COLONNES) {
                                // Cherche une cellule vide à partir de la rangée du bas
                                for (int ligne = Canevas.LIGNES - 1; ligne >= 0; ligne--) {
                                    if (canevas.grille[ligne][colonneSelectionne].getCouleur() == Jeton.Couleur.VIDE) {
                                        canevas.grille[ligne][colonneSelectionne].setCouleur(joueur.getCouleur()); // jouer un coup
                                        break;
                                    }
                                }
                            }
                        }

                        try {

                            outToClient.writeObject("grille");
                            outToClient.writeObject(canevas.grille);
                            
                            currentPlayer = Client.joueur;
                            miseAjour(new Jeton(currentPlayer.getCouleur())); // mise à jour de la grille du jeu
                        } catch (IOException ex) {
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        canevas.initGrille();
                        etat = EtatDuJeu.PARTIE_EN_COURS;
                    }
                    repaint();
                }
            });
            /**
             * Initialise le contenu du plateau et l'état du jeu
             */
            while (true) {
                String cmd = (String) inFromClient.readObject();
                switch (cmd) {
                    case "Hello":
                        String name = (String) inFromClient.readObject();
                        Client.joueur.setName(name);
                        System.out.println("New player is " + name);
                        break;
                    case "grille":
                        canevas.grille = (Jeton[][]) inFromClient.readObject();
                        repaint();
                        currentPlayer = Server.joueur;
                        break;
                }
                miseAjour(new Jeton(currentPlayer.getCouleur()));

            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Server(int port) {
        super();
        this.port = port;
        currentPlayer = joueur;
        pack();  // emballe tout les composants du JFrame
        setTitle("Puissance Quatre 2 Joueurs (Server)");
        setVisible(true);  // montrer ce JFrame
    }
}
